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
				<form action="./articleUpdate" method="post" class="form-horizontal">
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
							<label class="control-label" for="articleName">帖子标题:</label>
							<div class="controls">
								<input type="hidden" name="id" value="${article.id}" />
								<input type="hidden" name="callback" value="${callback}" />
								<input type="text" id="articleName" name="articleName" value="${article.articleName}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="app_id">app_id:</label>
							<div class="controls">
								<input type="text" id="app_id" name="app_id" value="${article.app_id}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="articleBody">帖子内容:</label>
							<div class="controls">
								<textarea rows="" cols="" name="articleBody">${article.articleBody}</textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="iconFile">创建时间:</label>
							<div class="controls">
								<img src="${topic.realTopicPic }"></img>
								<input type="text" id="iconFile" name="createTime" value="" />
							</div>
						</div>
						<div class="control-group" align="center">
							<input type="submit" name="" value="更新/添加" />
							<a href="${callback }">返回</a>
							<a href="./articleUpdate?callback=${callback}">新加</a>
						</div>
					</fieldset>
				</form>
			</td>
			<td width="30%"></td>
		</tr>
	</table>
</body>
</html>




