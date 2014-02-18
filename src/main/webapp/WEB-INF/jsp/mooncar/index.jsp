<%@page pageEncoding="UTF-8"%>
<style>
.namesTable td:nth-child(3) {
	text-align: right !important;
}

.namesTable td:nth-child(1) {
	text-align: center !important;
}

.daysTable td:nth-child(2),#statistics td:nth-child(3) {
	text-align: right !important;
}

#functions .panel-body {
	text-align: center;
}

.form-group {
	padding-left: 0px !important;
}

.tab-content {
	padding: 5px;
}

.nav-tabs>li {
	float: none;
	display: inline-block;
	*display: inline; /* ie7 fix */
	zoom: 1; /* hasLayout ie7 trigger */
	white-space: nowrap;
}

.nav-tabs {
	text-align: center !important;
}

.jqplot-title {
	font-family: "Microsoft Yahei", "MS PGothic", "Helvetica Neue", Helvetica, Arial, sans-serif;
}
</style>
<div class="col-md-12">
	<div class="page-header">
		<h1><span class="clxyicon-rocket"></span> 嫦三月车辅</h1>
	</div>
	<span class="help-block">
		${app.description}
		<strong class='text-danger'>以下信息纯属自娱自乐，仅供参考，一切内容以官方为准！</strong>
	</span>

	<ul class="nav nav-tabs">
		<c:if test="${user.firstName=='yi.cui.clxy@gmail.com'}">
		<li>
			<a href="#admin" data-toggle="tab">
				<span class="glyphicon glyphicon-wrench"></span> 管理
			</a>
		</li>
		</c:if>
		<li>
			<a href="#search" data-toggle="tab">
				<span class="glyphicon glyphicon-search"></span> 查询
			</a>
		</li>
		<li class="active">
			<a href="#statistics" data-toggle="tab">
				<span class="glyphicon glyphicon-stats"></span> 统计
			</a>
		</li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane fade" id="search">
			<div class="container">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="form-inline">
							<div class="form-group col-md-10">
								<label class="sr-only" for="carName"><spring:message code="common.name" /></label>
								<input type="text" class="form-control" id="carName"
									placeholder="<spring:message code='common.name.placeholder'/>">
							</div>
							<span class="btn btn-primary" id="buttonSearch">
								<span class="glyphicon glyphicon-search"></span>
								<spring:message code="common.search" />
							</span>
						</div>
					</div>
					<span id="namesTable"> <%@include file="namesTable.jsp"%></span>
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="admin">
			<%@include file="panelAdmin.jsp"%>
		</div>
		<div class="tab-pane fade in active" id="statistics">
			<%@include file="panelStatistics.jsp"%>
		</div>
	</div>
</div>

<script>
	var downloadFrame = false;
	var baseUrl = "<c:url value='/mooncar/'/>";
</script>
<st:res type="js" files="index" isLocal="true" />
<%@include file="/WEB-INF/jsp/common/adsense.jsp"%>
