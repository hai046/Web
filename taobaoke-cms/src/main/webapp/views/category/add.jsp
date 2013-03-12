<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CMS</title>
</head>
<body class="addUser" bgcolor="#EEEEEE">
	<%@ include file="./../header.jsp"%>


	<table>
		<tr>
			<td width="30%"></td>
			<td width="30%">
				<form action="./add" method="post" class="form-horizontal" enctype="multipart/form-data">
					<fieldset>
						<c:if test="${not empty errorMsg }">
							<div class="control-group">
								<label class="control-label" for="content">错误:</label>
								<div class="controls">
									${errorMsg }
								</div>
							</div>
						</c:if>
						<div class="control-group">
							<label class="control-label" for="name">parentId:</label>
							<div class="controls">
								<input type="hidden" name="id" value="${tCategory.id}" />
								<input type="hidden" name="backUrl" value="${backUrl}" />
								<input type="text" id="name" name="parentId" value="${tCategory.parentId}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="app_id">app_id:</label>
							<div class="controls">
								<input type="text" id="app_id" name="app_id" value="${tCategory.app_id}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="name">name:</label>
							<div class="controls">
								<input type="text" id="name" name="name" value="${tCategory.name}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="cid">cid:</label>
							<div class="controls">
								<input type="text" id="cid" name="cid" value="${tCategory.cid}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="orderNo">orderNo:</label>
							<div class="controls">
								<input type="text" id="orderNo" name="orderNo" value="${tCategory.orderNo}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="iconFile">icon:</label>
							<div class="controls">
								<img src="${tCategory.realIconUrl }"></img>
								<input type="file" id="iconFile" name="iconFile" value="" />
							</div>
						</div>
						<div class="control-group" align="center">
							<input type="submit" name="" value="更新/添加" />
							<a href="${backUrl }">返回</a>
						</div>
					</fieldset>
				</form>
			</td>
			<td width="30%"></td>
		</tr>
	</table>
</body>
</html>




