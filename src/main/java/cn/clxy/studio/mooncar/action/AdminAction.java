package cn.clxy.studio.mooncar.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.common.data.MapData;
import cn.clxy.studio.common.data.ZipModel;
import cn.clxy.studio.common.exception.ServiceException;
import cn.clxy.studio.common.util.ZipUtil;
import cn.clxy.studio.mooncar.data.OriginNameData;
import cn.clxy.studio.mooncar.service.MooncarService;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

//TODO 管理员限制
public class AdminAction {

	@Resource
	protected MooncarService mooncarService;

	@RequestMapping("clear*")
	public Map<String, Integer> clear() throws Exception {
		int deleteCount = mooncarService.deleteAll();
		return new MapData<>(deleteCount);
	}

	/**
	 * Upload origin data.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("uploadO*")
	public Map<String, ?> uploadO(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DefaultMultipartHttpServletRequest multi = (DefaultMultipartHttpServletRequest) request;
		CsvMapper mapper = new CsvMapper();
		ObjectReader reader = mapper.
				reader(OriginNameData.class).with(mapper.schemaFor(OriginNameData.class));

		MapData<String, Object> result = new MapData<>();
		List<OriginNameData> datas = new ArrayList<>();

		MultipartFile file = multi.getFile(keyFiles);
		String fileName = file.getOriginalFilename();
		log.warn("Upload file：" + fileName);
		checkFile(file, false);

		if (ZipUtil.isZip(fileName)) {

			Map<String, byte[]> contents = ZipUtil.unzip(file.getInputStream());
			for (Entry<String, byte[]> entry : contents.entrySet()) {
				if (!read(reader, datas, entry.getValue())) {
					result.put(entry.getKey(), "failed");
				}
			}
		} else {
			if (!read(reader, datas, file.getInputStream())) {
				result.put(fileName, "failed");
			}
		}

		// If get error.
		if (!result.isEmpty()) {
			return new MapData<String, Object>("files", result);
		}

		mooncarService.analyze(datas);
		result.put("insertCount", datas.size());
		return result;
	}

	/**
	 * Upload analyze data.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("uploadA*")
	public Map<String, ?> uploadA(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DefaultMultipartHttpServletRequest multi = (DefaultMultipartHttpServletRequest) request;
		MultipartFile file = multi.getFile(keyFiles);
		checkFile(file, true);

		Map<String, byte[]> contents = ZipUtil.unzip(file.getInputStream());
		mooncarService.setAnalyzeData(contents);

		return new MapData<>("OK");
	}

	@RequestMapping("refresh*")
	public String refresh() {

		// if (!GaeUtil.isBackend()) {
		// return "NotByBackendError";
		// }

		mooncarService.refresh();
		return "Updated";
	}

	@RequestMapping("download*")
	public ModelAndView download() throws Exception {

		ZipModel result = new ZipModel();
		result.putAll(mooncarService.getAnalyzeData());
		return result;
	}

	private void checkFile(MultipartFile file, boolean needZip) {

		if (file == null || file.isEmpty()) {
			throw new ServiceException("exception.service.wrongfile");
		}

		if (needZip && !ZipUtil.isZip(file.getOriginalFilename())) {
			throw new ServiceException("exception.service.wrongfile");
		}
	}

	private boolean read(ObjectReader reader, List<OriginNameData> datas, Object data) {

		try {

			MappingIterator<OriginNameData> iterator = null;
			if (data instanceof byte[]) {
				iterator = reader.readValues((byte[]) data);
			} else if (data instanceof InputStream) {
				iterator = reader.readValues((InputStream) data);
			} else {
				// No way!
				throw new RuntimeException("Wrong type : " + data.getClass());
			}

			while (iterator.hasNextValue()) {
				datas.add(iterator.next());
			}
		} catch (Exception e) {
			log.warn(e);
			return false;
		}

		return true;
	}

	private static final String keyFiles = "uploadFiles";
	private static final Log log = LogFactory.getLog(AdminAction.class);
}
