<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="static/css/base.css" rel="stylesheet" type="text/css" /> 
    <link href="static/css/global.css" rel="stylesheet" type="text/css"/>
    
    <meta name="keywords" content="淘色,taose,69,桃色,情趣,情趣用品,淘宝,天猫,天猫特卖,淘宝特卖,成人用品,夫妻,成人,两性,性爱,性价比,成人笑话,同性,美女,成人小说,成人电影" />
    <meta name="description" content="淘色，App Store生活榜第一名，最性感最好玩的情趣APP，为你专业推荐最安全最实惠最优品质的情趣宝贝。情趣用品、情趣游戏，成人笑话、性爱技巧、两性交流应有尽有！淘情趣，性本色！" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
    <%-- <script type="text/javascript" src="jquery/jquery.easing.1.3.js"></script> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/slides.min.jquery.js"></script>
    <script>
    var config = {
    		contextPath: '${pageContext.request.contextPath}'
    };
        $(function(){
            $('#slides').slides({
                preload: true,
                preloadImage: 'img/loading.gif',
                play: 5000,
                pause: 2500,
                hoverPause: true
            });
        });
    </script>
    <title>淘色 - 两性成人情趣用品天堂,最性感的手机情趣应用</title>
</head>

<body>
<%@include file="./inc/header.jsp"%>  

<div class="o-main">
        <div id="container">
        <div id="example">
            <div id="slides">
                <div class="slides_container">
                    <a href="#nogo" title=""><img src="static/img/1.png" width="850" height="272"/></a>
                    <a href="#nogo" title=""><img src="static/img/2.png" width="850" height="272"/></a>
                    <a href="#nogo" title=""><img src="static/img/3.png" width="850" height="272"/></a>
                </div>
                <a href="#nogo" class="prev s-prev"> </a>
                <a href="#nogo" class="next s-next"> </a>
            </div> 
        </div>
 
    </div>
    <div class="info">
        <div class="info-l">
        	<input type="hidden" value="<%=request.getAttribute("connect_id") %>" id="connectId"/>
            <span class="code"><img src="./colorQR?size=164" /></span>
            <div class="help">
                <p class="title">PC购使用帮助</p>
                <p class="text">
                	1.打开淘色官网，点击手机端PC购按钮<br>
					2.扫描官网PC购二维码，显示宝贝详情<br>
					3.购买您心仪的宝贝
				</p>
            </div>
        </div>
        <div class="info-r">
            <h3>免费下载淘色：</h3>
            <p>
                <a class="s-btn s-btn-1" href="http://shouji.360safe.com/360sj/dev/20121228/com.buy_1_104024.apk" target="_blank"></a>
                <a class="s-btn s-btn-2" href="https://itunes.apple.com/cn/app/tao-se-liang-xing-cheng-ren/id592356996?mt=8" target="_blank"></a>
                <a class="s-btn s-btn-3" href="https://itunes.apple.com/cn/app/tao-sehd-cheng-ren-liang-xing/id596649011?mt=8"  target="_blank"></a>
            </p>
            <h3>关注淘色：</h3>
            <p>
                <span class="s-btn s-btn-4"></span>
                <a href="http://weibo.com/u/3185418505"  target="_blank"><span class="s-btn s-btn-5"></span></a>
            </p>
        </div>
    </div>
</div>
<%@include file="./inc/footer.jsp"%>  

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