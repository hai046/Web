package com.taobaoke.cms.home;

import java.sql.SQLException;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.ConnectSessionDAO;

@Component
public class ConnectSessionHome {

	@Autowired
	private ConnectSessionDAO connectSessionDAO;
	
    private static ApplicationContext context;

    private static ConnectSessionHome instance;
	
	public static ConnectSessionHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (ConnectSessionHome) BeanFactoryUtils.beanOfType(context, ConnectSessionHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }
	
	public boolean insert(long connectId, String sessionId) throws SQLException {
		try {
			return connectSessionDAO.insert(connectId, sessionId) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException("插入数据失败");
		}

	}

	public String getSessionId(long connectId) throws SQLException {
		try {
			return connectSessionDAO.getSessionId(connectId);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public boolean delete(String sessionId) throws SQLException {
        try {
            return connectSessionDAO.del(sessionId) > 0 ? true : false;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        
    }

}
