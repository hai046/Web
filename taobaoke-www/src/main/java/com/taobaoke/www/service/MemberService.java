package com.taobaoke.www.service;

import java.sql.SQLException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import com.taobaoke.cms.home.ConnectSessionHome;

@Service()
public class MemberService {
    private static final Log logger = LogFactory.getLog(MemberService.class);

    @Inject
    BayeuxServer _bayeux;
    @Session
    ServerSession _session;
    
    @Listener("/member/add")
    public void handleMembership(ServerSession client, ServerMessage message) {
        Map<String, Object> data = message.getDataAsMap();

        logger.info("aaaaa....I am here ");
        System.out.println("aaaaa....I am here ");
        long connectId = NumberUtils.toLong( (String) data.get("connect_id"), 0 );
        try {
            
            if( connectId < 1 ){
                return ;
            }
            ConnectSessionHome.getInstance().insert(connectId, client.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        client.addListener(new ServerSession.RemoveListener() {
            public void removed(ServerSession session, boolean timeout) {
                try {
                    ConnectSessionHome.getInstance().delete(session.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}