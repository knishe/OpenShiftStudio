<%@page pageEncoding="UTF-8"%>
<style>
	td:last-child{
		text-align: center !important;
	}
</style>
<div class="col-md-12">
	<div class="page-header">
		<h1>
			<spring:message code="common.apps" />
		</h1>
	</div>

	<div class="row">
		<div class="col-md-12">
			<button class="btn btn-sm btn-primary" disabled="disabled">
				<span class="glyphicon glyphicon-plus"></span> Add
			</button>

			<div class="btn-group pull-right" data-toggle="buttons" id="viewButtons">
				<label class="btn btn-sm btn-info">
					<span class="glyphicon glyphicon-list"></span>
					<input type="radio" name="view" value="listView"> List
				</label>
				<label class="btn btn-sm btn-info active">
					<span class="glyphicon glyphicon-th-large"></span>
					<input type="radio" name="view" value="iconView"> Icon
				</label>
			</div>
		</div>
	</div>
	<p></p>
	<div class="row" id="iconView">
		<div class="col-md-12">
			<c:forEach items="${app}" var="ap" varStatus="status">
				<div class="col-md-4 col-lg-3" id="app-${ap.code}"
					 data-thumbnail="<c:url value='${ap.thumbnail}'/>">
					Loading from <c:url value='${ap.thumbnail}'/> ...
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="row" id="listView">
		<div class="col-md-1"></div>
		<div class="col-md-10">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th class="col-md-1"><spring:message code="common.code" /></th>
						<th class="col-md-2"><spring:message code="common.name" /></th>
						<th class="col-md-8"><spring:message code="common.description" /></th>
						<th class="col-md-1">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr class="app-template hidden">
						<td>{code}</td>
						<td>{name}</td>
						<td>{description}</td>
						<td>
							<button class="btn btn-sm btn-danger" disabled="disabled">
								<span class="glyphicon glyphicon-remove"></span>
								<spring:message code="common.delete" />
							</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<st:res type="js" files="template"/>
<script>
	$(document).ready(function() {
		var tabs = $("#listView").add($("#iconView"));
		var current = false;
		$("#viewButtons input[name='view']").change(function() {
			setTimeout(function() {
				var v = $("#viewButtons .active input").val();
				if (v === current) {
					return;
				}
				current = v;
				tabs.hide();
				$("#" + current).fadeIn();
			}, 10);
		}).trigger("change");

		$("#iconView div[data-thumbnail]").each(function(i, v) {
			var $v = $(v);
			$v.load($v.attr("data-thumbnail"), function() {

				var code = $v.attr("id").substr(4);
				var name = $(".panel-heading", $v).html();
				if (!name) {
					return;
				}

				var tr = $("<tr>").html(csm.template($(".app-template"), {
					code: code + 3,
					name: name,
					description: $(".panel-body", $v).html()
				}));
				$("#listView tbody").append(tr);
			});
		});
	});
</script>
<%@include file="/WEB-INF/jsp/common/adsense.jsp"%>
