package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemRateDAO;
import com.taobaoke.cms.model.TItemRate;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
public class TItemRateHome {

	@Autowired
	private TItemRateDAO tItemRateDAO;

	private static ApplicationContext context;

	private static TItemRateHome instance;

	public static int TYPE_MOBILE = 1;
	public static int TYPE_WEB = 2;
	public static int ALL = -1;

	public static int STATUS_OK = 0;
	public static int STATUS_ERR = 1;

	public static TItemRateHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (TItemRateHome) BeanFactoryUtils.beanOfType(context,
				TItemRateHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	public int insert(TItemRate tItemRate) throws SQLException {
		try {
		    if( tItemRate == null ){
		        return -1;
		    }
		    RedisPoolFactory.delete(listCacheKey(tItemRate.getNumIid()));
			return tItemRateDAO.insert(tItemRate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public TItemRate getById(int id) throws SQLException {
		try {
			return tItemRateDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int getCountByNumIid(long numIid) throws SQLException {
	    Integer count = 0;
		try {
		    count = RedisPoolFactory.get(countCacheKey(numIid), Integer.class);
		    if( count != null && count > 0 ){
		        return count == null ? 0 : count;
		    }
			count = tItemRateDAO.getCountByNumIid(numIid);
			if( count == null || count < 1 ){
			    return count == null ? 0 : count;
			}
			RedisPoolFactory.set(countCacheKey(numIid), count, 60*60*24*7);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return count == null ? 0 : count;
	}

	public List<TItemRate> getListByNumiid(long numIid, int offset, int limit)
			throws SQLException {
	    List<TItemRate> result = null;
		try {
		    result = RedisPoolFactory.getList(listCacheKey(numIid), TItemRate.class, offset, offset + limit - 1, false);
            if( result != null && result.size() == limit ){
                return result;
            }
            result = tItemRateDAO.getLisByNumiid(numIid, offset, limit);
            if( result == null || result.size() < 1 ){
                return result;
            }
            for( TItemRate tir : result ){
                RedisPoolFactory.add2List(listCacheKey(numIid), tir, tir.getCommentDate().getTime(), offset);
            }
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	public int del(int id) throws SQLException {
		try {
		    TItemRate tir = this.getById(id);
		    if( tir == null ){
		        return -1;
		    }
		    RedisPoolFactory.delete(listCacheKey(tir.getNumIid()));
			return tItemRateDAO.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}
	
	private String listCacheKey(long numIid){
	    return "t_item_rate_" + numIid;
	}
	
	private String countCacheKey(long numIid){
        return "t_item_rate_count_" + numIid;
    }
	
	

	public static void main(String[] args) {
		RoseAppContext context = new RoseAppContext();
		TItemRateHome home = context.getBean(TItemRateHome.class);
		try {
			System.out.println("sss+++" + home.getById(1));
			System.out.println("sss+++");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sfsdljfkjslkdf");

	}
}
