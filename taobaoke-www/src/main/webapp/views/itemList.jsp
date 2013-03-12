<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="keywords" content="淘色,taose,69,桃色,情趣,情趣用品,淘宝,天猫,天猫特卖,淘宝特卖,成人用品,夫妻,成人,两性,性爱,性价比,成人笑话,同性,美女,成人小说,成人电影" />
    <meta name="description" content="淘色，App Store生活榜第一名，最性感最好玩的情趣APP，为你专业推荐最安全最实惠最优品质的情趣宝贝。情趣用品、情趣游戏，成人笑话、性爱技巧、两性交流应有尽有！淘情趣，性本色！" />
    
    <link href="static/css/base.css" rel="stylesheet" type="text/css" /> 
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/application.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery//MSClass.js"></script>
	
	
	<script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
       
    </script>
    <script type="text/javascript">
        $(document).ready(function(){
            var currentli = $(".piclist").children("li").first();
            var currentTopVal = false;

            function newTop(add){
                var topStr = $(".piclist").css("top");
                if( topStr == "auto" ){
                	topStr = "10px";
                }
                var topNum = topStr.substring(0, topStr.length-2) * 1;
                if( currentTopVal === false ){
                	currentTopVal = topNum;
                }else{
                	topNum = currentTopVal;
                }

                var heightStr = currentli.css("height");
                var heightNum = heightStr.substring(0, heightStr.length-2) * 1;

                var intervalStr = currentli.css("margin-bottom");
                var intervalNum = intervalStr.substring(0, intervalStr.length-2) * 1;

                var newTopStr = "";
                if( add ){
                	currentTopVal = topNum+heightNum+intervalNum;
                    newTopStr = (topNum+heightNum+intervalNum)+"px";
                }else{
                	currentTopVal = topNum-heightNum-intervalNum;
                    newTopStr = (topNum-heightNum-intervalNum)+"px";
                }
                return newTopStr; 
            }

            $("#up").bind("click", function(){
                if( currentli.next() == null || currentli.next().length < 1 ){
                    return ;
                }
                var newTopStr = newTop(false);
                    currentli = currentli.next();

                $(".piclist").animate({"top":newTopStr},300);
            });
            $("#down").bind("click", function(){
                if( currentli.prev() == null || currentli.prev().length < 1 ){
                    return ;
                }
                currentli = currentli.prev();
                var newTopStr = newTop(true);
                $(".piclist").animate({"top":newTopStr},300);
            });

        });
    </script> 
    <title>淘色 - 两性成人情趣用品天堂,最性感的手机情趣应用</title>
</head>

<body>
<%@include file="./inc/header.jsp"%>  
<div class="main">
	<div class="main-l">
		<input type="hidden" value="<%=request.getAttribute("connect_id") %>" id="connectId"/>
    	<input type="hidden" value="${device_no }" id="deviceNo"/>
		<p class="m-view"><span>最近浏览商品</span></p>
		<a href="#nogo"><p class="arrow-bar up" id="up">▲</p></a>
		<div class="piclist-wrap">
			<ul class="piclist">
				<c:forEach var="il" items="${itemList}">
                <li id="${il.id }">
                    <a href="#nogo" class="piclist-l"><img src="${il.picUrl }_40x40.jpg" width=50 height=50/></a>
					
                    <div class="piclist-r">
                        <a href="#nogo" class="title">${il.title }</a>
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
        <a href="#nogo"><p class="arrow-bar" id="down">▼</p></a>
        <p><img src="./colorQR?size=164" /></p>
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
<%@include file="./inc/footer.jsp"%>  

</body>
</html>

