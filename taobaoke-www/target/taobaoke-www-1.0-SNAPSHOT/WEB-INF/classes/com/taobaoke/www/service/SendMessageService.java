/*
 * Copyright (c) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taobaoke.www.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import com.taobaoke.cms.home.ConnectSessionHome;
import com.taobaoke.cms.model.TItem;


@Service()
public class SendMessageService
{
    @Inject
    private BayeuxServer _bayeux;
    @Session
    private ServerSession _session;
    
    public static SendMessageService instance = null;
    
    public static SendMessageService getInstance(){
        return instance;
    }
    
    public static void setInstance(SendMessageService _instance){
        instance = _instance;
    }


    public void sendMessage(long connectId, TItem tItem, String deviceNo) {
        Map<String, Object> data = new HashMap<String, Object>(4);
        data.put("title", tItem.getTitle());
        data.put("pic_url", tItem.getPicUrl());
        data.put("price", tItem.getPrice());
        data.put("volume", tItem.getVolume());
        data.put("cash_ondelivery", tItem.getCashOndelivery());
        data.put("click_url", tItem.getEncodedClickUrl());
        /*
         * TODO 
         * 使用 mSourceStoreHome.getSourceValue(deviceNo, SourceStoreHome.TYPE_COLLECT);<BR>
         * 数据为long类型的jsonArray
         */
        String sessionId = "";
        try {
            sessionId = ConnectSessionHome.getInstance().getSessionId(connectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if( StringUtils.isEmpty(sessionId) ){
            return ;
        }
        // Create the channel name using the stock symbol
        String channelName = "/sendMessage/" + connectId;

        // Initialize the channel, making it persistent and lazy
        ServerMessage.Mutable forward = _bayeux.newMessage();
        forward.setChannel(channelName);
        // forward.setId(message.getId());
        forward.setData(data);

        // test for lazy messages
        // if (text.lastIndexOf("lazy")>0)
        forward.setLazy(true);

        ServerSession session = _bayeux.getSession(sessionId);
        if( session == null ){
            try {
                ConnectSessionHome.getInstance().delete(sessionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ; 
        }
        // session.deliver(from, channel, data, id);
//        WebItemHome.getInstance().insert(connectId, itemId, WebItemHome.TYPE_ITEM);
        session.deliver(_session, forward);

    }
}
