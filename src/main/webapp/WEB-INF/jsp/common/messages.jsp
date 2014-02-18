<%@page pageEncoding="UTF-8"%>
<div class="hide">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 alert text-center" id="messageBox"></div>
	</div>
	<ul id="messagesDataList">
		<c:forEach items="${sf:errorMessages()}" var="msg">
			<li<c:if test="${not empty msg.field}"> data-field="#${msg.objectName } #${msg.field }"</c:if>>
				<spring:message code="${msg.code}" arguments="${msg.arguments}" text="${msg.defaultMessage}" />
			</li>
		</c:forEach>
	</ul>
	<div class="modal" id="modalDiv">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>Please confirm</h3>
		</div>
		<div class="modal-body">
			<p>Body contents</p>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn pull-left" data-dismiss="modal" aria-hidden="true">Close</button>
			<a class="btn btn-primary" id="confirmYesBtn">OK</a>
		</div>
	</div>
</div>