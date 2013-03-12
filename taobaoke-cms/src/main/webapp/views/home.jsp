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

	<%@ include file="./header.jsp"%>
	<div id="divFrame" runat="server">


        <div>${currDate}</div>
		<table class="adminList" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="3%">&nbsp;</td>
				<td width="94%"></td>
				<td width="3%">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<form action="/" method="post">
						<input type="text"  name="date" class="datepicker"/>
						
					</form>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th style="table-layout: inherit; width: 100px">类别</th>
							<th style="table-layout: inherit; width: 100px">项目</th>
							<th style="table-layout: inherit; width: 100px">总数</th>
							<th style="table-layout: inherit; width: 100px">每天数</th>
						</tr>
						
					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<%-- <td align="center"><%@ include file="./pages.jsp"%></td> --%>
			</tr>
		</table>
	</div>
</body>
</html>



