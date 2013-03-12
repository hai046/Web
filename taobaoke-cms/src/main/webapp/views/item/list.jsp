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
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=id&orderOption=${empty id ? false : !orderOption }">id<span class="${id }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=num_iid&orderOption=${empty num_iid ? false : !orderOption }">numIid<span class="${num_iid }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=title&orderOption=${empty title ? false : !orderOption }">商品名<span class="${title }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=volume&orderOption=${empty volume ? false : !orderOption }">销售量<span class="${volume }"></span></a></th>
							<th style="table-layout: inherit; width: 100px">图片</th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=price&orderOption=${empty price ? false : !orderOption }">价格<span class="${price }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=commission_rate&orderOption=${empty commission_rate ? false : !orderOption }">佣金比例<span class="${commission_rate }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=commission&orderOption=${empty commission ? false : !orderOption }">佣金<span class="${commission }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=nick&orderOption=${empty nick ? false : !orderOption }">店铺名称<span class="${nick }"></span></a></th>
							<th style="table-layout: inherit; width: 100px"><a href="./list?tcId=${tcId}&backUrl=${backUrl}&orderItem=seller_credit_score&orderOption=${empty seller_credit_score ? false : !orderOption }">店铺信用<span class="${seller_credit_score }"></span></a></th>
							<th style="table-layout: inherit; width: 100px">商品url</th>
						</tr>
						
						<c:forEach var="il" items="${itemList}">
						<tr>
							<td>${il.id }</td>
							<td>${il.numIid }</td>
							<td>${il.title}</td>
							<td>${il.volume}</td>
							<td><img src="${il.picUrl}_120x120.jpg"></img></td>
							<td>${il.price}</td>
							<td>${il.commissionRate}</td>
							<td>${il.commission}</td>
							<td>${il.nick}</td>
							<td>${il.sellerCreditScore}</td>
							<td><a target="_blank" href="${il.clickUrl }">商品url</a></td>
						</tr>
						</c:forEach>
						

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><a href="${backUrl }">返回</a></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<c:set var="extraParam" value="tcId=${tcId}&backUrl=${backUrl}&orderItem=${orderItem}&orderOption=${orderOption }" />
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

