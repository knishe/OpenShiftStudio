$(document).ready(function() {
	// 检索按钮。
	$("#buttonSearch").click(search);
	initPagination();
	drawChart();
});

function getUrl(target) {
	return baseUrl + $(target).attr("data-url");
}

function search() {
	var url = baseUrl + "search?name=" + $("#carName").val();
	$("#namesTable").load(url, initPagination);
	console.log($("#namesTable").html());
}

function download(url) {
	if (downloadFrame) {
		downloadFrame.attr('src', url);
	} else {
		downloadFrame = $('<iframe>', {
			src : url
		}).hide().appendTo('body');
	}
}

function initPagination() {

	$(".pagination a").click(function() {
		var page = $(this).attr("data-page");
		var url = baseUrl + "page" + "?page=" + page;
		$("#namesTable").load(url, initPagination);
	});
}

function drawChart() {

	$.jsDate.regional['cn'] = {
		dayNamesShort : [ '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天' ]
	};
	$('html').attr("lang", "cn");
	$.jsDate.regional.getLocale();

	var tables = $("#days .table");
	var datas = [];
	// $(tables.get().reverse()).each(function(ti, tv) {
	$.each(tables, function(ti, tv) {
		var data = [];
		$.each($("tbody tr", tv), function(ri, rv) {
			var tr = $(rv);
			data.push(new Array($("th", tr).text(), parseInt($("td", tr).text(), 10)));
		});
		datas.push(data);
	})

	var dayChart = $.jqplot('dayChart', datas, {
		title : '可收集件数 — 日推移堆积图',
		stackSeries : true,
		axes : {
			xaxis : {
				renderer : $.jqplot.DateAxisRenderer,
				tickOptions : {
					formatString : '%m-%d %a'
				},
				// max : '2013-10-25',
				min : '2013-09-25'
			},
			yaxis : {
				min : 0,
				max : 75000
			}
		},
		grid : {
			borderColor : 'transparent'
		},
		legend : {
			show : true,
			placement : 'insideGrid',
			location : 'n',
			border : '0px'
		},
		seriesDefaults : {
			fill : false,
			showMarker : true,
			pointLabels : {
				show : true,
				edgeTolerance : 5
			},
			markerOptions : {
				show : true,
				style : 'filledCircle'
			}
		},
		series : [ {
			label : 'XinHua'
		}, {
			label : 'QQ'
		} ],
		highlighter : {
			// sizeAdjust : 7.5,
			bringSeriesToFront : true,
			show : true
		}
	});
}