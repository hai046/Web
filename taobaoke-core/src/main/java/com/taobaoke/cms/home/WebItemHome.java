package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.WebItemDAO;

@Component
public class WebItemHome {

	/**
	 * 收藏类型
	 */
	public static int TYPE_ITEM = 1;
	
	@Autowired
	private WebItemDAO webItemDAO;


	private static ApplicationContext context;

    private static WebItemHome instance;

    public static WebItemHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (WebItemHome) BeanFactoryUtils.beanOfType(context, WebItemHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }
	
	
	public int insert(String deviceNo, long itemId, int type)
			throws SQLException {
		try {
			return webItemDAO.insert(deviceNo, itemId, type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public List<Long> getItemList(String deviceNo)
			throws SQLException {
		try {
			return webItemDAO.getItemIdList(deviceNo, TYPE_ITEM, 0, Integer.MAX_VALUE);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	public int get(String deviceNo, long itemId, int type) throws SQLException {
	    Integer result = 0;
        try {
            result = webItemDAO.get(deviceNo, itemId, type);
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return result == null ? 0 : result;
    }
	
	public int getCount(String deviceNo)
            throws SQLException {
	    Integer count = 0;
        try {
            count = webItemDAO.getCount(deviceNo, TYPE_ITEM);
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return count == null ? 0 : count;
    }


	public int deleteId(int id) throws SQLException {
		try {
			return webItemDAO.deleteId(id);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	public int delete(String deviceNo, long itemId, int type) throws SQLException {
        try {
            return webItemDAO.deleteByItemId(deviceNo, itemId, type);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
	
	public static void main(String []args){
//	    RoseAppContext context = new RoseAppContext();
//	    WebItemHome home = context.getBean(WebItemHome.class);
//	    try {
//            home.deleteId(2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
	}

}
