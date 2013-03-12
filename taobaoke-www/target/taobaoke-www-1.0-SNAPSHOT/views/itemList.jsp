
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
    <script type="text/javascript" src="application.js"></script>
    
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
       
    </script>
    <link href="static/css/base.css" rel="stylesheet" type="text/css" /> 
    <title>xx--x</title>
</head>

<body>
<div id="header">
    <div class="wrap">
    <div class="header-l">
        <a href="#"><img  src="static/images/logo.png" /></a>
        <span><img  src="static/images/logotext.png" /></span>  
    </div>
    
    <span class="header-f">商务合作：bd@taose69.com</span>
    </div>
</div>
<div class="main">
    <div class="main-l">
    	<input type="hidden" value="<%=request.getAttribute("connect_id") %>" id="connectId"/>
        <p class="m-view"><a href="#">最近浏览商品</a></p>
        <p class="arrow-bar">▲</p>
        <div class="piclist-wrap">
            
            <ul class="piclist">
            	<c:forEach var="il" items="${itemList}">
                <li>
                    <a href="#nogo" class="piclist-l"><img src="${il.picUrl }_40x40.jpg" width=50 height=50/></a>
                    <div class="piclist-r">
                        <a class="title">${il.title }</a>
                        <p class="sale">¥${il.price }</p>
                        <p class="num">销量：${il.volume }</p>
                    </div>
                    <input type="hidden" value="${il.title }" name="title"/>
                    <input type="hidden" value="${il.picUrl }" name="picUrl"/>
                    <input type="hidden" value="${il.price }" name="price"/>
                    <input type="hidden" value="${il.volume }" name="volume"/>
                    <input type="hidden" value="${il.cashOndelivery }" name="cashOndelivery"/>
                    <input type="hidden" value="${il.encodedClickUrl }" name="clickUrl"/>
                </li>
                </c:forEach>
            </ul>
           
        </div>
         <p class="arrow-bar">▼</p>
        <p><img src="./colorQR?size=164"/></p>
        <p class="m-code">PC购二维码</p>
        <p class="m-code-text">(用手机扫描此二维码购物)</p>
    </div>
    <div class="main-r">
    	<c:set var="lastEle" value="${itemList[0]}" />
        <h2 id="dis_title">${lastEle.title}</h2>
        <div class="main-pic">
            <a href="${lastEle.clickUrl }" id="dis_pic_click_url" target="_blank"><img src="${lastEle.picUrl}_310x310.jpg"  id="dis_pic_url"/></a>
            <h3>
                <span id="dis_price">¥${lastEle.price}</span>
                <em id="dis_volume">销量:${lastEle.volume}</em>
                <i  id="dis_cashOndelivery" style="display:${lastEle.cashOndelivery>0 ? '' : 'none'}">货到付款</i>
            </h3>
        </div>
        
        <p><img src="static/images/ling.png" width="326" height="12"/></p>
        <p><a id="dis_click_url" class="btn" href="./buy?url=${lastEle.encodedClickUrl}" target="_blank"></a></p>
    </div>
</div>
<div id="footer">
    <p>Copyright © 2012-2013 taose69.com All Rights Reserved </p>
    <p>北京博然乐享科技有限公司 版权所有</p>
</div>

</body>
</html>
