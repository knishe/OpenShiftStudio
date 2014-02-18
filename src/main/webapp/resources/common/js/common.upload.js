csm.fileupload = function(selector, options) {

	var $target = $(selector);
	$.each($(selector), function(i, v) {

		$target = $(v);
		var $parent = $target.closest(options.parent || ".file-parent");
		var $submitButton = $(options.button || ".file-button-submit", $parent);
		var $progressbar = $(options.progressBar || ".progress-bar", $parent);
		var $infoPanel = $(options.infoPanel || ".file-info-panel", $parent);
		var maxFileCount = 10;

		var setting = $.extend(options, {
			dataType : 'json',
			// singleFileUploads : false,
			// limitConcurrentUploads : 10,
			add : function(e, data) {
				$infoPanel.empty();
				var files = [];
				$.each(data.files, function(index, file) {
					var p = $('<p/>').text(file.name + "：" + csm.formatFileSize(file.size));
					p.addClass("text-info").appendTo($infoPanel);
					files.push(file);
					if (files.length >= maxFileCount) {
						return false;
					}
				});
				data.files = files;
				console.log(data.files.length);
				$progressbar.css('width', '0%');
				if ($submitButton.length) {
					$submitButton.removeAttr("disabled");
					$submitButton.off("click").click(function() {
						$submitButton.attr("disabled", true);
						console.log(data.files.length);
						data.submit();
					});
				} else {
					data.submit();
				}
			},
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$progressbar.css('width', progress + '%');
			},
			done : function(e, data) {
				$infoPanel.empty();
				if (data.result.files) {
					$.each(data.result.files, function(key, value) {
						var p = $('<p/>').text("文件：" + key + " 上传处理失败！" + (value || ""));
						p.addClass("text-danger").appendTo($infoPanel);
					});
				}
				if (data.result.success) {
					$('<p/>').text(data.result.success).addClass("text-success").appendTo(
							$infoPanel);
				}
				data.files = false;
				if (options.doneCallback) {
					options.doneCallback();
				}
			}
		});
		$target.fileupload(setting);
	});
};

csm.formatFileSize = function(bytes) {
	if (typeof bytes !== 'number') {
		return '';
	}
	if (bytes >= 1000000000) {
		return (bytes / 1000000000).toFixed(2) + ' GB';
	}
	if (bytes >= 1000000) {
		return (bytes / 1000000).toFixed(2) + ' MB';
	}
	return (bytes / 1000).toFixed(2) + ' KB';
};