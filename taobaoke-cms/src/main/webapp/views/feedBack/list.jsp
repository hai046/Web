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
	<div id="divFrame" runat="server">

		<table class="adminList" width="100%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>

					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th style="table-layout: inherit; width: 40px">id</th>
							<th style="table-layout: inherit; width: 120px">mobile_model</th>
							<th style="table-layout: inherit; width: 140px">content</th>
							<th style="table-layout: inherit; width: 100px">email</th>
							<th style="table-layout: inherit; width: 100px">status</th>
							<th style="table-layout: inherit; width: 100px">create_time</th>
							<th style="table-layout: inherit; width: 100px">update_time</th>
						</tr>
						
						<c:forEach var="c" items="${list}" varStatus="status">
						<tr>
							<td>${c.id}   <a href="./delete?id=${c.id}">删除</a></td>
							<td>${c.mobile_model}</td>
							<td>${c.content}</td>
							<td>${c.email}</td>
							<td>
							<c:choose>
								<c:when test="${c.status==1}">
									标记为<a href="./update?id=${c.id}">处理</a>
								</c:when>
								<c:when test="${c.status==2}">
										已经处理过
								</c:when>
							</c:choose>
							
							</td>
							<td>${c.create_time}</td>
							<td>${c.update_time}</td>
						</tr>
						</c:forEach>
						

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<%@ include file="./../pages.jsp"%>
				</td>
			</tr>
		</table>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
	
</script>

