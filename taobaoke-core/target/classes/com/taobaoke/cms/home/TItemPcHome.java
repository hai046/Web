package com.taobaoke.cms.home;

import java.sql.SQLException;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemPcDAO;

@Component
public class TItemPcHome {

	@Autowired
	private TItemPcDAO tItemPcDAO;
	
    private static ApplicationContext context;

    private static TItemPcHome instance;
	
	public static TItemPcHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (TItemPcHome) BeanFactoryUtils.beanOfType(context, TItemPcHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }
	
	public boolean insert(long numIid, String clickUrl) throws SQLException {
		try {
			return tItemPcDAO.insert(numIid, clickUrl) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException("插入数据失败");
		}

	}

	public String getClickUrl(long numIid) throws SQLException {
		try {
			return tItemPcDAO.getClickUrl(numIid);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public boolean delete(long sessionId) throws SQLException {
        try {
            return tItemPcDAO.del(sessionId) > 0 ? true : false;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        
    }

}
