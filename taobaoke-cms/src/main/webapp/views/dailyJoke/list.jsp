<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="date" class="java.util.Date"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CMS</title>
<script type="text/javascript">
	
</script>
</head>
<body class="addUser" bgcolor="#EEEEEE">
	<%@ include file="./../header.jsp"%>
	<div id="divFrame" align="center" runat="server">

		<table class="adminList" width="900px" border="0" cellspacing="0"
			cellpadding="0">

			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">每日段子</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">

					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th align="center" style="table-layout: inherit; width: 200px">date</th>
							<th align="center" style="table-layout: inherit; width: 700px">content</th>
						</tr>

						<c:forEach var="c" items="${list}" varStatus="status">

							<tr
								<c:choose>
								<c:when test="${c.date<=date}">
										style="background:#AAAAAA"
								</c:when>
							</c:choose>>
								<td align="center"><fmt:formatDate value="${c.date}" type="date"></fmt:formatDate></td>
								<td align="center">
									<form action="/joke/update" method="post">
										<input type="hidden" name="id" value="${c.id}" />
										<textarea rows="3" style="width: 500px; 
										<c:choose>
								<c:when test="${c.date<=date}">
										background:#AAAAAA
								</c:when>
							</c:choose>
										
										
										"
											cols="160" name="content">${c.content}</textarea>
										<input type="submit" value="修改" />
									</form>
								</td>
							</tr>
						</c:forEach>


					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><%@ include file="./../pages.jsp"%>
				</td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td><br></br>
					<hr /> <br></br> </td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">添加新段子</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<form action="/joke/add" method="post">
						<input type="radio" name="flag" value="before" />向前添加 <input
							type="radio" name="flag" checked="checked" value="after" />向后添加<br></br>
						<textarea rows="4" name="content" style="width: 500px" cols="200"></textarea>
						<br /> <input type="submit" value="提交" />
					</form>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><hr></hr><br></br><br></br>
					<form action="/joke/deleteBefore" method="post">
						删除<select name="offset" style="width: 120px">
							<option value="60">60天前</option>
							<option value="90">90天前</option>
							<option value="120">120天前</option>
							<option value="150">150天前</option>
							<option value="180">180天前</option>
						</select>的段子 <input type="submit" value="提交" />
					</form></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><br></br> <br></br> <br></br></td>
				<td>&nbsp;</td>
			</tr>

		</table>
		<!--这里是自定义页面结束-->
	</div>




	</table>
</body>
</html>

<script type="text/javascript">
	
</script>

