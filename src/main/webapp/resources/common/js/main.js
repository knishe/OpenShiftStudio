"use strict";
/**
 * namespace。
 */
var csm = {};

/**
 * =================== static methods
 */

/**
 * 是否处理中。
 * @param obj
 * @returns {Boolean}
 */
csm.isSubmiting = function(obj) {
	var key = "submiting";
	if (obj.attr(key)) {
		return true;
	}
	obj.attr(key, true);
	return false;
};

/**
 * 终了处理。
 * @param obj
 */
csm.endSubmiting = function(obj) {
	obj.removeAttr("submiting");
};

/**
 * 共通Ajax。ダブルサブミッション防止が各自でやる。
 * @param settings
 */
csm.ajax = function(settings) {
	var options = $.extend(settings, {
		type : "POST",
		url : csm.getAbsUrl(settings.url)
	});
	$.ajax(options);
};

/**
 * フォームAjax化する。
 * @param form
 * @param settings
 * @returns {csm.ajaxForm}
 */
csm.ajaxForm = function(form, settings) {

	form = $(form);
	settings = settings || {};
	var url = settings.url || form.attr2('action');

	// デフォルト設定。
	var options = $.extend({
		beforeSubmit : function(arr, $form, options) {
			// ダブルサブミッション防止。
			if (csm.isSubmiting($form)) {
				return false;
			}
		},
		complete : function() {
			csm.endSubmiting(form);
		}
	}, settings, {
		// 路径处理需要吗？
		url : url
	});

	form.ajaxForm(options);
};

csm.errorFields = $([]);

/**
 * ===------------------------------ エラーとメッセージ。
 */
csm.showMessage = function(text, isError) {

	// クリア。
	csm.errorFields.tooltip('destroy').parents(".control-group").removeClass("error");
	csm.errorFields = $([]);
	var box = $("#messageBox").removeClass("alert-error alert-success").addClass("hide");

	// データ収集。
	var map = {};
	$.each($("<div>").html(text).find("li"), function(index, value) {

		var li = $(value);
		var msg = li.text();
		var name = li.attr("data-field");
		// if (!name) {// メッセージを直接に表示する。
		box.text(msg);
		box.removeClass("hide").addClass("alert-" + (isError ? "error" : "success"));
		box.parent().popover({
			title : "エラーが発生しました。",
			content : box.html(),
			html : true,
			placement : "bottom"
		}).popover('show');
		// return;
		// }

		var n = map[name];
		if (!n) {
			n = [];
			map[name] = n;
		}
		n.push(msg);
	}),

	// エラー表示する。
	$.each(map, function(key, value) {
		var field = $(key);
		csm.errorFields = csm.errorFields.add(field);
		field.parents(".control-group").addClass("error");
		field.tooltip({
			html : true,
			placement : 'right',
			trigger : 'focus',
			cssClass : 'error',
			title : value.join("<br>")
		}).tooltip('show');
	});
};

/**
 * 遷移する。
 * @param to
 */
csm.go = function(to) {
	document.location.href = csm.getAbsUrl(to);
};

/**
 * 絶対パスを取得する。
 * @param url
 * @returns
 */
csm.getAbsUrl = function(url) {

	if (url.substr(0, 4) == "http") {
		return url;
	}

	var root = CONSTANTS.rootUrl;
	if (url.substr(0, root.length) == root) {
		return url;
	}

	if (url.charAt(0) == "/") {
		url = url.substr(1);
	}

	return root + url;
};

/**
 * Confirm dialog.Do not use bootstrap orign .modal() directly!<br>
 * @see http://confirmmodal.codeplex.com/
 */
csm.dialog = function(settings) {

	var options = $.extend({
		selector : '#modalDiv',// 对话框模板选择器
		header : 'Please confirm',// 标题内容
		headerSelector : '.modal-header h3',// 标题选择器
		body : 'Body contents',// 主体内容
		bodySelector : '.modal-body p',// 主体选择器
		handleSelector : '.modal-header',// 拖动条选择器
		// 简化的OK按钮动作。
		callback : false
	}, settings);

	var modal = $(options.selector);
	$(options.headerSelector).html(options.header);
	$(options.bodySelector).html(options.body);
	modal.draggable({
		handle : options.handleSelector
	});
	var buttons = options.buttons || [ {
		selector : '#confirmYesBtn',
		click : function() {
			if (options.callback) {
				options.callback();
			}
			modal.modal('hide');
		}
	} ];

	$.each(buttons, function(i, b) {
		$(b.selector, modal).off('click').click(b.click);
	});

	modal.modal('show');
	return modal;
};

/**
 * 共通エラー処理。
 */
$(document).ajaxError(function(event, xhr, settings, error) {

	// 共通エラー処理。settingsのエラー処理を上書きしている。
	console.log(xhr);
	var ct = xhr.getResponseHeader('content-type');
	if (!ct) {
		// csm.go(CONSTANTS.systemError);
		return;
	}

	// HTMLなら表示する。
	if (ct.indexOf("html") > -1) {
		csm.showMessage(xhr.responseText, true);
		return;
	}
	// JSONなら例外とする。
	if (ct.indexOf("json") > -1) {
		var ex = $.parseJSON(xhr.responseText);
		if (ex && ex.view) {
			csm.go(ex.view);
			return;
		}
	}
	// その他の不明例外がすべてシステム例外へ。
	// csm.go(CONSTANTS.systemError);
});

/**
 * 初始化。
 */
$(document).ready(function() {

	// 所有form变成ajax。
	$("form").each(function(index, form) {
		csm.ajaxForm($(form));
	});

	// 显示信息。
	csm.showMessage($("#messagesDataList").html(), true);

	var colors = [ "#A30001", "#FF9900", "#47248F", "#002080" ];
	var target = $(".navbar-inverse,.jumbotron").css("transition", "background-color 1.5s linear");
	setInterval(function() {
		var color = colors.shift();
		target.css("background-color", color);
		colors.push(color);
	}, 10000);
});
