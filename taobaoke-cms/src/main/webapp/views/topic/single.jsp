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
				<form action="./topicAdd" method="post" class="form-horizontal" enctype="multipart/form-data">
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
							<label class="control-label" for="topicName">专题名:</label>
							<div class="controls">
								<input type="hidden" name="id" value="${topic.id}" />
								<input type="hidden" name="backUrl" value="${backUrl}" />
								<input type="text" id="topicName" name="topicName" value="${topic.topicName}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="topicName">应用id:</label>
							<div class="controls">
								<input type="text" id="app_id" name="app_id" value="${topic.app_id}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="content">专题文字:</label>
							<div class="controls">
								<input type="text" id="content" name="content" value="${topic.content}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="status">状态:</label>
							<div class="controls">
								<input type="text" id="status" name="status" value="${topic.status}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="orderNo">排列号:</label>
							<div class="controls">
								<input type="text" id="orderNo" name="orderNo" value="${topic.orderNo}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="orderNo">类型:</label>
							<div class="controls">
								<input type="text" id="orderNo" name="type" value="${topic.type}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="orderNo">商品号:</label>
							<div class="controls">
								<input type="text" id="orderNo" name="numIid" value="${topic.numIid}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="orderNo">url:</label>
							<div class="controls">
								<input type="text" id="orderNo" name="url" value="${topic.url}" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="iconFile">专题图片:</label>
							<div class="controls">
								<img src="${topic.realTopicPic }"></img>
								<input type="file" id="iconFile" name="iconFile" value="" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="iconFilePad">专题图片(Pad):</label>
							<div class="controls">
								<img src="${topic.realTopicPadPic }"></img>
								<input type="file" id="iconFilePad" name="iconFilePad" value="" />
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




