<%@page pageEncoding="UTF-8"%>
<div class="text-center"><%@include file="pagination.jsp" %></div>
<table class="table table-striped table-bordered namesTable">
	<thead>
		<tr>
			<th class="col-md-1">No.</th>
			<th class="col-md-2"><spring:message code="common.name" /></th>
			<th class="col-md-1"><spring:message code="common.count" /></th>
			<th class="col-md-8">&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty allNames}">
			<tr><td colspan="4">没有数据。</td></tr>
		</c:if>
		<c:set var="offset" value="${pagination.offset}"/>
		<c:forEach items="${allNames}" begin="${offset}" end="${offset+pagination.limit-1}" var="name" varStatus="status">
			<tr>
				<td>${status.count+offset}</td>
				<td>${name.name}</td>
				<td>
					<fmt:formatNumber value="${name.count}" type="number" pattern="#,###" />
				</td>
				<td>
					<c:forEach items="${name.origins}" var="entry">
						<c:set var="car" value="${entry.value}"/>
						<c:if test="${car.site=='qq'}">
							<c:set var="url">http://act3.news.qq.com/10240/work/show-id-${car.originId}.html</c:set>
						</c:if>
						<c:if test="${car.site=='xinhua'}">
							<c:set var="xhid"><fmt:formatNumber value="${car.originId/20 + 0.5}" type="number" pattern="#" /></c:set>
							<c:set var="url">http://forum.home.news.cn/detail/126673699/${xhid}.html#author_${car.originId}</c:set>
						</c:if>
						<a href="${url}" target="_blank">${car.name}</a>
						by&nbsp;${car.createBy}@${car.site}&nbsp;
						-&nbsp;<fmt:formatDate value="${car.createAt}" pattern="M月d日"/>，
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="text-center"><%@include file="pagination.jsp" %></div>
