<%@page pageEncoding="UTF-8"%>
<st:res type="css" files="jquery.jqplot.min"/>
<st:res type="js" files="jquery.jqplot.min,jqplot.dateAxisRenderer.min,jqplot.highlighter.min"/>
<div class="row">
	<div class="col-md-8 col-md-offset-2">
		<div class="alert alert-warning text-center">
			更新时间：
			<strong><fmt:formatDate value="${statistics.updateAt}" pattern="yyyy年MM月dd日 HH:mm:ss" /></strong>
		</div>
	</div>
</div>
<div class="container">
	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">件数</div>
			<div class="panel-body">注：新华网理论投稿数为论坛发帖数。</div>
			<table class="table table-striped daysTable">
				<thead>
					<tr>
						<th>分类</th>
							<c:forEach var="entry" items="${statistics.map}">
							<th>${entry.key}</th>
							</c:forEach>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>理论投稿数</th>
							<c:forEach var="entry" items="${statistics.map}">
							<td><fmt:formatNumber value="${entry.value.max}" pattern="#,###" /></td>
						</c:forEach>
					</tr>
					<tr>
						<th>可收集件数</th>
							<c:forEach var="entry" items="${statistics.map}">
							<td><fmt:formatNumber value="${entry.value.count}" pattern="#,###" /></td>
						</c:forEach>
					</tr>
					<tr>
						<th>整理后最终件数</th>
							<c:forEach var="entry" items="${statistics.map}">
							<td><fmt:formatNumber value="${entry.value.countUnduplicated}" pattern="#,###" /></td>
						</c:forEach>
					</tr>
					<tr class="danger text-danger">
						<th>最终的最终件数</th>
						<td colspan="2"><fmt:formatNumber value="${statistics.count}" pattern="#,###" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-danger">
			<div class="panel-heading">TOP 10</div>
			<table class="table table-striped namesTable">
				<thead>
					<tr>
						<th class="col-md-2">No.</th>
						<th class="col-md-5"><spring:message code="common.name" /></th>
						<th class="col-md-5"><spring:message code="common.count" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="car" items="${topCount}" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${car.name}</td>
							<td>
								<fmt:formatNumber value="${car.count}" type="number" pattern="#,###" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="container">
		<div class="col-md-12" id="dayChart" style="height:500px;">
		</div>
</div>
<div class="container hidden" id="days">
	<c:forEach var="entry" items="${statistics.map}">
		<div class="col-md-6">
		<div class="panel panel-info">
			<div class="panel-heading">可收集件数 — 日推移堆积图</div>
			<table class="table table-striped daysTable">
				<thead>
					<tr>
						<th>日期</th>
						<th>${entry.key}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="day" items="${entry.value.days}">
						<tr>
							<th><fmt:formatDate value="${day.key}" pattern="yyyy-MM-dd"  /></th>
							<td><fmt:formatNumber value="${day.value}" type="number" pattern="####" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</div>
	</c:forEach>
</div>
