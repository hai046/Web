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

		<table class="adminList" width="100%" border="0" cellspacing="0"
			cellpadding="0">

			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center" width="700px">

					<table width="800px" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th style="table-layout: inherit; width: 200px">key</th>
							<th style="table-layout: inherit; width: 500px">value&&name</th>

						</tr>
						<c:set var="ordey_type" value="-1"></c:set>
						<c:forEach var="c" items="${list}" varStatus="status">
							<c:choose>

								<c:when test="${c.type!=ordey_type}">
									<tr class="TableInfoTH">
										<th colspan="2" align="center"><font color="red">type=${c.type}</font></th>

									</tr>
									<c:set var="ordey_type" value="${c.type}"></c:set>
								</c:when>
							</c:choose>

							<tr>
								<td>${c.key}<br></br> <a
									href="./delete?id=${c.id}&${pageCount}">删除</a></td>
								<td>
									<form action="./update" method="post">
										<input name="id" value="${c.id}" type="hidden" /> value:<input
											value="${c.value}" name="value"
											style="table-layout: inherit; width: 400px" /><br></br>

										name:<input name="name"
											style="table-layout: inherit; width: 400px" value="${c.name}" />
										<input type="submit" value="修改" />
									</form>
								</td>

							</tr>
						</c:forEach>

					</table> <br></br> <br></br> <br></br> 添加参数
					<form action="/system/setKeyValue" method="post">
						key(必填)<input name="key" /><br></br> value(必填)<input name="value" /><br></br>
						name(选填)<input name="name" /><br></br> type(选填)<input name="type" /><br>
						</br> <input type="submit" />
					</form> <br></br> 提示：
					<div style="border: 1px solid black;">
						<div align="left" style="border: 1px solid black;">
							1.key为loadingPic最为开机图片的的key,例如：图片为480x800，请把key写成
							loadingPic_480x800<br></br>
						</div>
						<div align="left" style="border: 1px solid black;">
							2.android版本更新相关key：<br></br>(1).androidVerUpdateDesc
							代表本次android更新的描述，#号代表分行<br></br> (2).androidVersionName
							代表android版本名字例如1.1<br></br> (3).androidVersionCode 代表android的版本号，<font
								color="Red">必须正整形 最小为1</font>例如：<br />5
							此为判断是否版本升级的关机，如果答应当前版本，则跳出升级页面<br></br> (4).androidApkDownUrl
							代表下载apk文件的url地址<br></br> 
							<font color="red">注意：因为后来衍生了新的引用  故：androidVerUpdateDesc代表android应用 淘色 的描述，androidVerUpdateDesc1代表代表android应用 宠物 的描述，以此类推。 其他的属性也是一样</font>
							<br></br>
						</div>
						<div align="left" style="border: 1px solid black;">
							2.iPhone版本更新相关key：<br></br>(1).iPhoneVerUpdateDesc
							代表本次iPhone更新的描述，#号代表分行<br></br> (2).iPhoneVersionName
							代表iPhone版本名字例如1.2<br></br> (3).iPhoneVersionCode 代表iPhone的版本号，<br></br>
							(4).version_iPhoneDownUrl 代表下载的url地址<br></br>
						</div>
						<div align="left" style="border: 1px solid black;">
							2.iPad版本更新相关key：<br></br>(1).iPadVerUpdateDesc
							代表本次iPad更新的描述，#号代表分行<br></br> (2).iPadVersionName 代表iPad版本名字例如1.3<br></br>
							(3).iPadVersionCode 代表iPad的版本号， 此为判断是否版本升级的关机，如果答应当前版本，则跳出升级页面<br></br>
							(4).iPadDownUrl 代表下载url地址<br></br>
						</div>



					</div>
				</td>
				<td>&nbsp;</td>
			</tr>




			<tr>
				<td>&nbsp;</td>
				<td align="center"><%@ include file="./../pages.jsp"%>
				</td>
			</tr>
		</table>
		
		
		<form action="/system/deleteCache" method="get">
			删除缓存key=<input name="key" value="" type="text"/>
		</form>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
	
</script>

