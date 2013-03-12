require(['dojox/cometd', 'dojo/dom', 'dojo/dom-construct', 'dojo/domReady!'],
function(cometd, dom, doc)
{
    cometd.configure({
        url: location.protocol + '//' + location.host + config.contextPath + '/cometd',
        logLevel: 'info'
    });

    cometd.addListener('/meta/handshake', function(message)
    {
        if (message.successful)
        {
        	cometd.publish('/test/add', {
                user: 'haha'
            });
            dom.byId('status').innerHTML += '<div>CometD handshake successful</div>';
            cometd.subscribe('/test/haha', function(message)
//            cometd.subscribe('/stock/*', function(message)
            {
                var data = message.data;
                var url = data.url;
                

                // Find the div for the given stock symbol
                var id = 'stocks';
                var symbolDiv = dom.byId(id);
                
                symbolDiv.innerHTML = '<span>url: ' + url + '</span>';
            });
        }
        else
        {
            dom.byId('status').innerHTML += '<div>CometD handshake failed</div>';
        }
    });

    cometd.handshake();
});