package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TopicDAO;
import com.taobaoke.cms.model.Topic;
import com.taobaoke.cms.redis.RedisPoolFactory;
import com.taobaoke.cms.utils.Apps;

@Component
public class TopicHome {

	@Autowired
	private TopicDAO topicDAO;

	private static ApplicationContext context;

	private static TopicHome instance;

	public static int STATUS_OK = 0;
	public static int STATUS_NO_DISPLAY = 1;
	public static int STATUS_ALL = -1;

	public static int TYPE_TOPIC = 0;
	public static int TYPE_ITEM = 1;
	public static int TYPE_URL = 2;
	public static int TYPE_OTHER = 3;

	public static TopicHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (TopicHome) BeanFactoryUtils.beanOfType(context,
				TopicHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	public List<Topic> getAll(int status, int offset, int limit)
			throws SQLException {
		List<Topic> result = null;
		try {
			// result = RedisPoolFactory.getList(listCacheKey(), Topic.class,
			// offset, offset + limit - 1, true);
			// int count = getCount(status);
			// if (result != null
			// && (result.size() == limit || result.size() == count)) {
			// return result;
			// }
			result = topicDAO.getAll(status, offset, limit);
			if (result == null || result.size() < 1) {
				return result;
			}
			// for (Topic temp : result) {
			// RedisPoolFactory.add2List(listCacheKey(), temp,
			// temp.getOrderNo(), offset);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	/**
	 * ==========================================<BR>
	 * 功能： <BR>
	 * 时间：2013-2-19 下午2:23:05 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param status
	 * @param app_id
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<Topic> getAll(int status, int app_id, int offset, int limit)
			throws SQLException {
		List<Topic> result = null;
		try {
			result = RedisPoolFactory.getList(listCacheKey(app_id),
					Topic.class, offset, offset + limit - 1, true);
			int count = getCount(status,app_id);
			if (result != null
					&& (result.size() == limit || result.size() == count)) {
				return result;
			}
			result = topicDAO.getAll(status, app_id, offset, limit);
			if (result == null || result.size() < 1) {
				return result;
			}
			for (Topic temp : result) {
				RedisPoolFactory.add2List(listCacheKey(app_id), temp,
						temp.getOrderNo(), offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	public int getCount(int status,int app_id) throws SQLException {
		Integer count = 0;
		try {
			if(app_id<0)
			{
				count = topicDAO.getCount(status);
				if (count == null || count < 1) {
					return count == null ? 0 : count;
				}else
				{
					return 0;
				}
			}
			
			count = RedisPoolFactory.get(countCacheKey(app_id), Integer.class);
			if (count != null && count > 0) {
				return count == null ? 0 : count;
			}
			count = topicDAO.getCount(status);
			if (count == null || count < 1) {
				return count == null ? 0 : count;
			}
			RedisPoolFactory.set(countCacheKey(app_id), count, 60 * 60 * 24 * 7);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return count == null ? 0 : count;
	}

	public int update(Topic topic) throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(topic.getApp_id()));
			return topicDAO.update(topic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int setAvialable(List<Integer> topicIdList) throws SQLException {
		try {
			for (int i = 0; i < Apps.MAXAPPID; i++)
				RedisPoolFactory.delete(listCacheKey(i));
			return topicDAO.setAvialable(topicIdList, TopicHome.STATUS_OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int setUnavialable(List<Integer> topicIdList) throws SQLException {
		try {
			for (int i = 0; i < Apps.MAXAPPID; i++)
				RedisPoolFactory.delete(listCacheKey(i));
			return topicDAO.setUnavialable(topicIdList,
					TopicHome.STATUS_NO_DISPLAY);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int updateStatus(int id, int status) throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(0));
			RedisPoolFactory.delete(listCacheKey(1));
			return topicDAO.updateStatus(id, status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int update(String topicName, int app_id, String topicPic,
			String topicPadPic, String content, int status, int type,
			long numIid, String url, int orderNo, int id) throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(app_id));
			return topicDAO.update(topicName, app_id, topicPic, topicPadPic,
					content, status, type, numIid, url, orderNo, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public Topic getById(int id) throws SQLException {
		try {
			return topicDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int insert(Topic topic) throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(topic.getApp_id()));
			RedisPoolFactory.delete(this.countCacheKey(topic.getApp_id()));
			return topicDAO.insert(topic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int insert(String topicName, int app_id, String topicPic,
			String topicPadPic, String content, int status, int type,
			long numIid, String url, int orderNo) throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(app_id));
			RedisPoolFactory.delete(this.countCacheKey(app_id));
			return topicDAO.insert(topicName, app_id, topicPic, topicPadPic,
					content, status, type, numIid, url, orderNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int del(int id) throws SQLException {
		try {
			Topic topic = topicDAO.getById(id);
			if (topic == null)
				return -1;
			RedisPoolFactory.delete(listCacheKey(topic.getApp_id()));
			return topicDAO.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	//
	// private String listCacheKey() {
	// return "topic_list";
	// }

	private String listCacheKey(int app_id) {
//		if (app_id == 0) {// 兼容以前的缓存
//			return "topic_list";
//		}
		return "topic_list_appId_" + app_id;
	}

	private String countCacheKey(int app_id) {
		return "topic_count"+app_id;
	}

	public static void main(String[] args) {
		// RoseAppContext context = new RoseAppContext();
		// TopicHome home = context.getBean(TopicHome.class);
		// try {
		//
		// System.out.println("sss+++"
		// + home.getAll(TopicHome.STATUS_OK, 0, Integer.MAX_VALUE)
		// .size());
		// System.out.println("sss+++");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println("sfsdljfkjslkdf");
		// FeedDAO dao = context.getBean(FeedDAO.class);
		// try {
		//
		// System.out.println("sss+++" + dao.delete(100378));
		// System.out.println("sss+++");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println("sfsdljfkjslkdf");

	}
}
