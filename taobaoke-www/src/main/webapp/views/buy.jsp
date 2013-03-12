<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>温馨提示-淘色</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
    
	<script type="text/javascript">
      <!--
      function load_url() {
          goTo("${url}")
      }
      var cnt = 0;
      setInterval(function() {
          if (cnt < 1) {
              cnt += 1;
          }else if( cnt == 1 ){
        	  cnt += 1;
        	  goTo("${url}");
          }
      }, 1000);

      
      function goTo(url) {
          var a = document.createElement("a");
          if(!a.click) {window.location.href = url;return;}
          if(isFirefox5plus()){window.location.href = url;return;}
          a.setAttribute("href", url);
          
          a.style.display = "none";
          document.body.appendChild(a);
          a.click();
      }
      
      function isFirefox5plus(){
    	  var user_agent = navigator.userAgent;
    	  if(/mozilla/i.test(user_agent) && /firefox\/([5-9]|\d{2,})[^\d]*/i.test(user_agent)){
    		  return true
    	  }
    	  return false
      }
      -->
  </script>
<style>
body,button,input,select,textarea{font:12px/1.125 Arial,Helvetica,sans-serif;_font-family:"SimSun"}
body,h1,h2,h3,h4,h5,h6,dl,dt,dd,ul,ol,li,th,td,p,blockquote,pre,form,fieldset,legend,input,button,textarea,hr{margin:0;padding:0;}
table{border-collapse:collapse;border-spacing:0;}li{list-style:none;}fieldset,img{border:0;}q:before,q:after{content:'';}input,textarea{outline-style:none;}
address,caption,cite,code,dfn,em,strong,th,var{font-style:normal; font-weight:normal;}
.eng{ font-family:Arial, Helvetica, sans-serif}
img{border:0;}
textarea{resize:none;}
h2,h3,h4{ font-size: 12px; font-weight: normal;}
input,button,select,textarea{outline:none}
ol,ul{list-style:none;}
caption,th{text-align:left;}
q:before,q:after{content:''}
a{text-decoration:none;}
a:link{color:#014E97;}
a:hover{text-decoration:underline;}
a:visited{color:#014E97;}
.t-main{ width:632px;position:absolute; top:50%; left:50%; margin:-180px 0 0 -316px;color:#333;background:#f9f9f9;padding:27px 32px 68px;font-family:"微软雅黑", "黑体", "宋体";border:1px solid #ccc; border-radius:12px;}
.t-main .t-logo{text-align:center;}
.t-main .t-tips{ margin-top:20px;text-align:center; color:#333;  font-size:18px;border-bottom:1px solid #ccc; padding-bottom:12px;}
.t-main .t-tips .para1{margin:20px 0 16px 0;}
.t-main .t-tips .para1 em{color:#ff004d;}
.t-main .t-tips .para2{ font-size:14px;}
.t-main .t-tips .para2 a{ padding-left:4px;color:#ff004e}
.t-main .t-text{ width:320px; margin:0 auto;padding-top:16px; border-top:1px solid #fff;text-align:center;}
.t-main .t-text .t-title{ font-size:18px;}
.t-main .t-content{ margin-top:10px; font-size:14px;line-height:22px;}
</style>
</head>
<body>
<div class="t-main">
	<h2 class="t-logo"><img src="static/images/logo.png" /></h2>
    <div class="t-tips">
    	<p class="para1"><em>亲，您将从淘色离开，</em>进入淘宝购物页面</p>
        <p class="para2">如果没有自动跳转，请<a href="${url }">点击这里</a></p>
    </div>
    <div class="t-text">
    	<p class="t-title">温馨提示</p>
        <div class="t-content">淘色仅为您提供淘宝网产品展示服务，您所<br />购买产品的全部信息请以淘宝各商家相应信息为准</div>
    </div>
</div>
</body>
</html>


