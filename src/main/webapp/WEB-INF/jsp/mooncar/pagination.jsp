<%@page pageEncoding="UTF-8" %>
<ul class="pagination">
    <c:if test="${pagination.pageCount>1}">
        <c:set var="pageStart" value="${pagination.pageStart}"/>
        <c:set var="pageEnd" value="${pagination.pageEnd}"/>
        <c:set var="pageCount" value="${pagination.pageCount}"/>
        <li<c:if test='${pageStart==1}'> class="disabled"</c:if>><a href="#" data-page="1">&laquo;</a></li>
        <c:if test="${pageStart!=1}">
            <li class="disable"><span>...</span></li>
        </c:if>
        <c:forEach begin="${pageStart}" end="${pageEnd}" varStatus="status">
            <li<c:if test='${pagination.page==status.index}'> class="active"</c:if>>
                <a href="javascript:void(0)" data-page="${status.index}">${status.index}</a>
            </li>
        </c:forEach>
        <c:if test="${pageEnd!=pageCount}">
            <li class="disable"><span>...</span></li>
        </c:if>
        <li<c:if test='${pageEnd==pageCount}'> class="disabled"</c:if>><a href="#" data-page="${pageCount}">&raquo;</a></li>
    </c:if>
</ul>
