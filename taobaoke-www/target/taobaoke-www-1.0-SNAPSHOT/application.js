(function($)
{
    var cometd = $.cometd;

    /*var itemLists = [];
    $(".piclist").child().foreach(){
    	$(this).children("name=")
    	itemLists.push({
    		title:$(this).children("[name='tile']").val(),
    		picUrl:$(this).children("[name='picUrl']").val(),
    		price:$(this).children("[name='price']").val(),
    		volume:$(this).children("[name='volume']").val(),
    		cashOndelivery:$(this).children("[name='cashOndelivery']").val(),
    		clickUrl:$(this).children("[name='clickUrl']").val(),
    	});
    }*/
    
    
    $(document).ready(function()
    {
    	function itemClick(){
    		$("#dis_title").html( $(this).children("[name='tile']").val() );
    		$("#dis_pic_url").attr("src", $(this).children("[name='picUrl']").val() + "_310x310.jpg");
    		$("#dis_price").html( "¥" + $(this).children("[name='price']").val() );
    		$("#dis_volume").html( "销量:" + $(this).children("[name='volume']").val() );
    		if( $(this).children("[name='cashOndelivery']").val() > 0 ){
    			$("#dis_cashOndelivery").css("display", "");
    		}else{
    			$("#dis_cashOndelivery").css("display", "none");
    		}
    		
    		$("#dis_pic_click_url").attr( "href", $(this).children("[name='clickUrl']").val() );
    		$("#dis_click_url").attr( "href", "./buy?url=" + $(this).children("[name='clickUrl']").val() );
    	}
    	
    	function createItemAndAppend2List(data){
    		var liEle = $("<li></li>");
    		var innerHtml = '<a href="" class="piclist-l"><img src="' + data.pic_url + '_40x40.jpg" width=50 height=50></a>';
    		innerHtml = innerHtml + '<div class="piclist-r">';
    		innerHtml = innerHtml + '<a class="title">' + data.title + '</a>';
    		innerHtml = innerHtml + '<p class="sale">¥' + data.price + '</a>';
    		innerHtml = innerHtml + '<p class="num">销量：' + data.volume + '</a>';
    		innerHtml = innerHtml + '</div>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.title + '" name="title"/>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.pic_url + '" name="picUrl"/>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.price + '" name="price"/>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.volume + '" name="volume"/>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.cash_ondelivery + '" name="cashOndelivery"/>';
    		innerHtml = innerHtml + '<input type="hidden" value="' + data.click_url + '" name="clickUrl"/>';
    		liEle.html(innerHtml);
    		
    		liEle.bind("click", itemClick);
    		$(".piclist").prepend(liEle);
    		liEle.click();
    	}
    	
    	$(".piclist").children().bind("click", itemClick);
    	
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
                    createItemAndAppend2List(data);

                   // $("#url").html( '<span>url: ' + url + '</span>' );
                  /*  var w=window.open();
                    setTimeout(function(){
                        w.location=url;
                    }, 300);*/ 
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