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
<!--     <script type="text/javascript" src="application.js"></script> -->
    </style>
    
    <%--
    The reason to use a JSP is that it is very easy to obtain server-side configuration
    information (such as the contextPath) and pass it to the JavaScript environment on the client.
    --%>
    <link href="static/css/base.css" rel="stylesheet" type="text/css" /> 
	<title>跳转啦。。。</title>
	<script type="text/javascript">
      <!--
      function load_url() {
          goTo("${url}")
      }
      var cnt = 0;
      setInterval(function() {
          if (cnt < 5) {
              cnt += 1;
          }else if( cnt == 5 ){
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
<div class="o-main">
    <div class="banner-wrap">
        <div class="btn-p"><a href=""><img src="static/images/btn-p.png" /></a></div>
        <div class="btn-n"><a href=""><img src="static/images/btn-n.png" /></a></div>
        <div class="banner">
            <a><img src="static/images/test2.png" /></a>
        </div>
    </div>
    <div class="info">
        <a href="${url }">手动跳转，请按这里</a>
    </div>
</div>
<div id="footer">
    <p>Copyright © 2012-2013 taose69.com All Rights Reserved </p>
    <p>北京博然乐享科技有限公司 版权所有</p>
</div>

</body>
</html>
