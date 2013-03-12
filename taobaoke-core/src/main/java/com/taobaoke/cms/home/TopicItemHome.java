package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TopicItemDAO;
import com.taobaoke.cms.model.Topic;
import com.taobaoke.cms.model.TopicItem;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
public class TopicItemHome {

	@Autowired
	private TopicItemDAO topicItemDAO;

	private static ApplicationContext context;

	private static TopicItemHome instance;

	public static int STATUS_OK = 0;
	public static int STATUS_NO_DISPLAY = 1;

	public static TopicItemHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (TopicItemHome) BeanFactoryUtils.beanOfType(context,
				TopicItemHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	/**
	 * ==========================================<BR>
	 * 功能：没有加缓存 ，只提供给cms使用 <BR>
	 * 时间：2013-2-22 下午2:51:35 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param topicId
	 * @param status
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<TopicItem> getAll(int topicId, int status, int offset, int limit)
			throws SQLException {
		List<TopicItem> result = null;
		try {
			// result = RedisPoolFactory.getList(listCacheKey(topicId),
			// TopicItem.class, offset, offset + limit - 1, true);
			// if (result != null && result.size() == limit) {
			// return result;
			// }

			result = topicItemDAO.getAll(topicId, status, offset, limit);

			if (result == null || result.size() < 1) {
				return result;
			}
			// for (TopicItem ti : result) {
			// RedisPoolFactory.add2List(listCacheKey(topicId), ti,
			// ti.getOrderNo(), offset);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	/**
	 * ==========================================<BR>
	 * 功能：获取对应app下面的专题和这推荐列表 <BR>
	 * 时间：2013-2-19 下午2:54:33 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param topicId
	 * @param app_id
	 * @param status
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<TopicItem> getAll(int topicId, int app_id, int status,
			int offset, int limit) throws SQLException {
		List<TopicItem> result = null;
		try {
			result = RedisPoolFactory.getList(listCacheKey(topicId, app_id),
					TopicItem.class, offset, offset + limit - 1, true);
			if (result != null && result.size() == limit) {
				return result;
			}
			result = topicItemDAO
					.getAll(topicId, app_id, status, offset, limit);
			if (result == null || result.size() < 1) {
				return result;
			}
			for (int i = result.size() - 1; i >= 0; i--) {
				TopicItem ti = result.get(i);
				if (ti.getApp_id() != app_id) {
					result.remove(i);
					continue;
				}
				RedisPoolFactory.add2List(listCacheKey(topicId, app_id), ti,
						ti.getOrderNo(), offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	public int getCount(int topicId, int status) throws SQLException {
		Integer count = 0;
		try {
			count = RedisPoolFactory.get(countCacheKey(topicId), Integer.class);
			if (count != null && count > 0) {
				return count == null ? 0 : count;
			}
			count = topicItemDAO.getCount(topicId, status);
			if (count == null || count < 1) {
				return count == null ? 0 : count;
			}
			RedisPoolFactory.set(countCacheKey(topicId), count,
					60 * 60 * 24 * 7);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return count == null ? 0 : count;
	}

	public int update(TopicItem topicItem) throws SQLException {
		try {
			if (topicItem == null) {
				return -1;
			}
			RedisPoolFactory.delete(listCacheKey(topicItem.getTopicId()));
			RedisPoolFactory.delete(listCacheKey(topicItem.getTopicId(),
					topicItem.getApp_id()));
			RedisPoolFactory.delete(countCacheKey(topicItem.getTopicId()));
			return topicItemDAO.update(topicItem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	@Deprecated
	public int update(int topicId, int tItemId, String tItemName,
			String tItemPic, int status, int orderNo, int id)
			throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(topicId));
			return topicItemDAO.update(topicId, tItemId, tItemName, tItemPic,
					status, orderNo, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public TopicItem getById(int id) throws SQLException {
		try {
			return topicItemDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int insert(TopicItem topicItem) throws SQLException {
		try {
			if (topicItem == null) {
				return -1;
			}
			RedisPoolFactory.delete(listCacheKey(topicItem.getTopicId()));
			RedisPoolFactory.delete(listCacheKey(topicItem.getTopicId(),
					topicItem.getApp_id()));
			RedisPoolFactory.delete(countCacheKey(topicItem.getTopicId()));
			return topicItemDAO.insert(topicItem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int insert(int topicId, int tItemId, String tItemName,
			String tItemPic, int status, int orderNo, int app_id)
			throws SQLException {
		try {
			RedisPoolFactory.delete(listCacheKey(topicId));
			RedisPoolFactory.delete(listCacheKey(topicId, app_id));
			RedisPoolFactory.delete(countCacheKey(topicId));
			return topicItemDAO.insert(topicId, tItemId, tItemName, tItemPic,
					status, orderNo, app_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int incOrderNo(int increment) throws SQLException {
		try {
			List<Topic> topicLists = TopicHome.getInstance().getAll(
					TopicHome.STATUS_ALL, 0, Integer.MAX_VALUE);
			if (topicLists != null && topicLists.size() > 0) {
				for (Topic topic : topicLists) {
					RedisPoolFactory.delete(listCacheKey(topic.getId()));
				}
			}
			return topicItemDAO.incOrderNo(increment);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int getMinOrderNo(int topicId, int status, List<Integer> tItemIdList)
			throws SQLException {
		try {
			return topicItemDAO.getMinOrderNo(topicId, status, tItemIdList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int del(int id) throws SQLException {
		try {
			TopicItem item = this.getById(id);
			if (item == null) {
				return -1;
			}
			RedisPoolFactory.delete(listCacheKey(item.getTopicId()));
			RedisPoolFactory.delete(listCacheKey(item.getTopicId(),
					item.getApp_id()));
			RedisPoolFactory.delete(countCacheKey(item.getTopicId()));
			return topicItemDAO.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	private String listCacheKey(int topicId) {
		return "topic_item_" + topicId;
	}

	private String listCacheKey(int topicId, int app_id) {
		if (app_id == 0) {
			return listCacheKey(topicId);
		}
		return "topic_item_" + topicId + "_app_" + app_id;
	}

	private String countCacheKey(int topicId) {
		return "topic_item_count_" + topicId;
	}

	public static void main(String[] args) {
		RoseAppContext context = new RoseAppContext();
		TopicItemHome home = context.getBean(TopicItemHome.class);
		try {

			System.out.println("sss+++"
					+ home.getAll(0, TopicItemHome.STATUS_OK, 0,
							Integer.MAX_VALUE).size());
			System.out.println("sss+++");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sfsdljfkjslkdf");

	}
}
