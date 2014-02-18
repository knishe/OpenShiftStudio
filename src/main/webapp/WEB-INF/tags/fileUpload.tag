<%@ tag language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ tag body-content="tagdependent" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ tag description="File upload component.See common.upload.js too."%>
<%-- attribute --%>
<%@ attribute name="addButton" required="true"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="submitButton" required="false"%>
<%@ attribute name="progressBar" required="false"%>
<div class="file-parent">
	<div class="row">
		<div class="col-md-3">
			<span class="btn btn-success file-button-add">
				<span class="glyphicon glyphicon-plus"/> ${addButton}
				<input type="file" name="uploadFiles" multiple data-url="${url}">
			</span>
		</div>
		<div class="col-md-6 file-info-panel">&nbsp;</div>
		<div class="col-md-3">
			<c:if test="${not empty submitButton}">
				<button class="btn btn-warning file-button-submit" disabled="disabled">
					<span class="glyphicon glyphicon-cloud-upload"/> ${submitButton}
				</button>
			</c:if>
		</div>
	</div>
	<c:if test="${(fn:toLowerCase(progressBar) != 'false')}">
		<br>
		<div class="col-md-12">
			<div class="progress progress-striped" aria-valuemin="0" aria-valuemax="100">
				<div class="progress-bar progress-bar-info" style="width: 0%;"></div>
			</div>
		</div>
	</c:if>
</div>
