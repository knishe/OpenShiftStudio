<%@page pageEncoding="UTF-8" contentType="text/javascript" session="false"%>
var CONSTANTS = {
	rootUrl : "<c:url value='/'/>",
	systemError : "<c:url value='/common/systemException'/>",
	region : "<%=request.getLocale() %>",
};
