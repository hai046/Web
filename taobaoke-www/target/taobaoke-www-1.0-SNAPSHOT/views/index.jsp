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
<!--     <script type="text/javascript" src="application.js"></script> -->
    </style>
    
    <%--
    The reason to use a JSP is that it is very easy to obtain server-side configuration
    information (such as the contextPath) and pass it to the JavaScript environment on the client.
    --%>
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
       
    </script>
    <link href="static/css/base.css" rel="stylesheet" type="text/css" /> 
        <title>xxx</title>

</head>

<body>
<div id="header">
    <div class="wrap">
    <div class="header-l">
        <a href="#"><img  src="static/images/logo.png" ></a>
        <span><img  src="static/images/logotext.png" ></span>  
    </div>
    
    <span class="header-f">商务合作：bd@taose69.com</span>
    </div>
</div>
<div class="o-main">
    <div class="banner-wrap">
        <div class="btn-p"><a href=""><img src="static/images/btn-p.png"></a></div>
        <div class="btn-n"><a href=""><img src="static/images/btn-n.png"></a></div>
        <div class="banner">
            <a><img src="static/images/test2.png"></a>
        </div>
    </div>
    <div class="info">
        <div class="info-l">
                <input type="hidden" value="<%=request.getAttribute("connect_id") %>" id="connectId"/>
            <span class="code">
                <img src="./colorQR?size=164"></img>
            </span>
            <div class="help">
                <p class="title">PC购使用帮助</p>
                <p class="text">1.打开淘色官网，点击手机端PC购按钮<br>2.扫描官网PC购二维码，显示宝贝详情<br>3.购买您心仪的宝贝</p>
            </div>
        </div>
        <div class="info-r">
            <h3>免费下载淘色：</h3>
            <p>
                <a class="s-btn s-btn-1"></a>
                <a class="s-btn s-btn-2"></a>
                <a class="s-btn s-btn-3"></a>
            </p>
            <h3>关注淘色：</h3>
            <p>
                <span class="s-btn s-btn-4"></span>
                <span class="s-btn s-btn-5"></span>
            </p>
        </div>
    </div>
</div>
<div id="footer">
    <p>Copyright © 2012-2013 taose69.com All Rights Reserved </p>
    <p>北京博然乐享科技有限公司 版权所有</p>
</div>

</body>
</html>
<script type="text/javascript">
(function($)
{
    var cometd = $.cometd;

    $(document).ready(function()
    {
        var connectId = $("#connectId").val();
        
        function _connectionEstablished()
        {
            //$('#body').append('<div>CometD Connection Established</div>');
        }

        function _connectionBroken()
        {
            //$('#body').append('<div>CometD Connection Broken</div>');
        }

        function _connectionClosed()
        {
            //$('#body').append('<div>CometD Connection Closed</div>');
        }

        // Function that manages the connection status with the Bayeux server
        var _connected = false;
        function _metaConnect(message)
        {
            if (cometd.isDisconnected())
            {
                _connected = false;
                _connectionClosed();
                return;
            }

            var wasConnected = _connected;
            _connected = message.successful === true;
            if (!wasConnected && _connected)
            {
                _connectionEstablished();
            }
            else if (wasConnected && !_connected)
            {
                _connectionBroken();
            }
        }

        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(message)
        {
                //$('#status').html('<div>CometD handshake 1time' + message + '</div>');
            if (message.successful)
            {
                cometd.publish('/member/add', {
                        connect_id: connectId
                });
                //$('#status').html('<div>CometD handshake successful</div>');
                cometd.subscribe('/sendMessage/' + connectId, function(message)
                {
                    var data = message.data;
                    var url = data.url;
                    

                    //$("#url").html( '<span>url: ' + url + '</span>' );
                    //var w=window.open();
                    /*setTimeout(function(){
                        w.location=url;
                    }, 300);*/
                    window.location.href=location.protocol + "//" + location.host + config.contextPath + "/itemList";
                    //alert(location.protocol + "//" + location.host + config.contextPath + "/itemList");
//                    $("#newW").attr('href', url);
//                    alert("you are going to leave this page..");
//                    $("#newW").get(0).click();
//                    $("#newW").click();
                });
            }
            else
            {
                //$('#status').html('<div>CometD handshake failed</div>');
            }
           
        }

        // Disconnect when the page unloads
        $(window).unload(function()
        {
            cometd.disconnect(true);
        });

        var cometURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
        cometd.configure({
            url: cometURL,
//            logLevel: 'debug'
            logLevel: 'info'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
    });
})(jQuery);
</script>