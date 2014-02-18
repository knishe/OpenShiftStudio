<%@page pageEncoding="UTF-8" %>
<c:if test="${user.firstName=='yi.cui.clxy@gmail.com'}">
<st:res type="js" files="jquery.ui.widget,jquery.iframe-transport,jquery.fileupload,common.upload"/>
<div class="row">
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">下载数据</div>
			<div class="panel-body">
				<button class="btn btn-sm btn-primary disabled buttonDownload"
						data-url="downloadOrigin.csv">
					<span class="glyphicon glyphicon-cloud-download"/> 原始数据
				</button>
				<button class="btn btn-sm btn-primary buttonDownload" data-url="download.zip">
					<span class="glyphicon glyphicon-cloud-download"/> 分析结果
				</button>
			</div>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-success">
			<div class="panel-heading">初始化</div>
			<div class="panel-body">
				<div class="col-md-12">
					<button class="btn btn-danger ajaxButton" data-url="clear.json">
						<span class="glyphicon glyphicon-trash"/> 清空数据库
					</button>
				</div>
				<hr>
				<c:set var="uploadOUrl"><c:url value='/mooncar/uploadO.json'/></c:set>
				<st:fileUpload addButton="添加原始数据" url="${uploadOUrl}" submitButton="开始上传"/>
				<hr>
				<c:set var="uploadAUrl"><c:url value='/mooncar/uploadA.json'/></c:set>
				<st:fileUpload addButton="添加分析数据" url="${uploadAUrl}" submitButton="开始上传"/>
			</div>
		</div>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading">更新</div>
			<div class="panel-body">
				<button class="btn btn-sm btn-primary ajaxButton" data-url="refresh.json">
					<span class="glyphicon glyphicon-refresh"/> 更新
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	// 下载按钮。
	$(".buttonDownload").click(function() {
		download(getUrl(this));
	});
	// 清空DB。TODO 可以做成共通。
	$(".ajaxButton").click(function() {
		var url = getUrl(this);
		csm.ajax({
			url : url,
			success : function(data, status, xhr) {
				csm.showMessage("<li>" + data.result + "</li>", false);
			}
		});
	});
	// 上传文件。
	csm.fileupload('.file-parent input[type=file]', {
	// doneCallback : search
	});
});
</script>
</c:if>
