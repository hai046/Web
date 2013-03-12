package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TCategoryDAO;
import com.taobaoke.cms.model.TCategory;
import com.taobaoke.cms.redis.RedisPoolFactory;
import com.taobaoke.cms.utils.Apps;

@Component
public class TCategoryHome {

	@Autowired
	private TCategoryDAO tCategoryDAO;

	private static ApplicationContext context;

	private static TCategoryHome instance;

	public static int TYPE_MOBILE = 1;
	public static int TYPE_WEB = 2;
	public static int ALL = -1;

	public static int STATUS_OK = 0;
	public static int STATUS_ERR = 1;

	public static TCategoryHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (TCategoryHome) BeanFactoryUtils.beanOfType(context,
				TCategoryHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	public List<TCategory> getAll(int type, int status, int offset, int limit)
			throws SQLException {
		try {
			return tCategoryDAO.getAll(type, status, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public List<TCategory> getAll(int type, int app_id, int status, int offset,
			int limit) throws SQLException {
		try {
			return tCategoryDAO.getAll(type, app_id, status, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public List<TCategory> getAllSubList(int type, int status, int parentId,
			int offset, int limit) throws SQLException {
		List<TCategory> result = null;
		try {
			result = RedisPoolFactory.getList("category_list_" + parentId,
					TCategory.class, offset, offset + limit - 1, true);
			if (result != null) {
				return result;
			}
			result = tCategoryDAO.getAllSubList(type, status, parentId, offset,
					limit);
			if (result == null || result.size() < 1) {
				return result;
			}

			for (TCategory tc : result) {
				RedisPoolFactory.add2List("category_list_" + parentId, tc,
						tc.getOrderNo(), offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	public List<TCategory> getAllSubList(int type, int status, int offset,
			int limit) throws SQLException {
		try {
			return tCategoryDAO.getAllSubList(type, status, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public List<TCategory> getAllCacheFacdeParentList(int type, int status,
			int offset, int limit) throws SQLException {
		List<TCategory> result = null;
		try {
			result = tCategoryDAO.getAllParentList(type, status, offset, limit);
			if (result == null || result.size() < 1) {
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	// public List<TCategory> getAllParentList(int type, int status, int offset,
	// int limit) throws SQLException {
	// List<TCategory> result = null;
	// try {
	// result = RedisPoolFactory.getList("category_list_" + 0,
	// TCategory.class, offset, offset + limit - 1, true);
	// if (result != null) {
	// return result;
	// }
	// result = tCategoryDAO.getAllParentList(type, status, offset, limit);
	// if (result == null || result.size() < 1) {
	// return result;
	// }
	//
	// for (TCategory tc : result) {
	// RedisPoolFactory.add2List("category_list_" + 0, tc,
	// tc.getOrderNo(), offset);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new SQLException("get all error!!! ", e);
	// }
	// return result;
	// }

	private String getListCache(int tcid) {
		return "category_list_" + tcid;
	}

	private String getListCache(int app_id, int tcid) {
		// return "category_list_" + ((app_id == 0) ? 0 : ("_app_id_" +
		// app_id));
		return "category_list_" + tcid + "_app_id_" + app_id;
	}

	public List<TCategory> getAllParentList(int type, int app_id, int status,
			int offset, int limit) throws SQLException {
		List<TCategory> result = null;
		try {
			result = RedisPoolFactory.getList(getListCache(app_id, 0),
					TCategory.class, offset, offset + limit - 1, true);
			if (result != null) {
				return result;
			}
			result = tCategoryDAO.getAllParentList(type, app_id, status,
					offset, limit);

			if (result == null || result.size() < 1) {
				return result;
			}

			for (int i = result.size() - 1; i >= 0; i--) {
				TCategory tc = result.get(i);
				if (tc.getApp_id() != app_id) {
					result.remove(i);
					continue;
				}
				RedisPoolFactory.add2List(getListCache(app_id, 0), tc,
						tc.getOrderNo(), offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
		return result;
	}

	public int getCount(int type, int status) throws SQLException {
		try {
			return tCategoryDAO.getCount(type, status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}
	public int getCount(int type,int app_id, int status) throws SQLException {
		try {
			return tCategoryDAO.getCount(type,app_id, status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int update(TCategory tCategory) throws SQLException {
		try {
			if (tCategory == null) {
				return -1;
			}
			RedisPoolFactory.delete(getListCache(tCategory.getApp_id(),
					tCategory.getParentId()));
			RedisPoolFactory.delete(getListCache(tCategory.getId()));
			RedisPoolFactory.delete(getListCache(tCategory.getParentId()));
			return tCategoryDAO.update(tCategory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	// public int update(int parentId, String name, String iconUrl, String cid,
	// int status, int type, int orderNo, int id) throws SQLException {
	// try {
	// RedisPoolFactory.delete("category_list_" + parentId);
	// return tCategoryDAO.update(parentId, name, iconUrl, cid, status,
	// type, orderNo, id);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new SQLException("get all error!!! ", e);
	// }
	// }

	public TCategory getById(int id) throws SQLException {
		try {
			List<TCategory> parentCategoryList = getAllParentList(
					TCategoryHome.TYPE_MOBILE, Apps.TAOSE.getAppId(),
					TCategoryHome.STATUS_OK, 0, 10);
			for (TCategory temp : parentCategoryList) {
				if (temp.getId() == id) {
					return temp;
				}
			}
			parentCategoryList = getAllParentList(TCategoryHome.TYPE_MOBILE,
					Apps.PET.getAppId(), TCategoryHome.STATUS_OK, 0, 10);
			for (TCategory temp : parentCategoryList) {
				if (temp.getId() == id) {
					return temp;
				}
			}

			return tCategoryDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public TCategory getById(int id, int app_id) throws SQLException {
		try {
			List<TCategory> parentCategoryList = getAllParentList(
					TCategoryHome.TYPE_MOBILE, app_id, TCategoryHome.STATUS_OK,
					0, 10);
			for (TCategory temp : parentCategoryList) {
				if (temp.getId() == id) {
					return temp;
				}
			}
			return tCategoryDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public int insert(TCategory tCategory) throws SQLException {
		try {
			if (tCategory == null) {
				return -1;
			}
			RedisPoolFactory.delete("category_list_" + tCategory.getParentId());
			RedisPoolFactory.delete(getListCache(tCategory.getApp_id(),
					tCategory.getParentId()));// fix
			// cache
			// bug
			// by
			// hai
			// :当添加新分类时候应该删除缓存
			return tCategoryDAO.insert(tCategory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	// @Deprecated
	// public int insert(int parentId, String name, String iconUrl, String cid,
	// int status, int type, int orderNo) throws SQLException {
	// try {
	// RedisPoolFactory.delete("category_list_" + parentId);
	//
	// return tCategoryDAO.insert(parentId, name, iconUrl, cid, status,
	// type, orderNo);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new SQLException("get all error!!! ", e);
	// }
	// }

	public int del(int id) throws SQLException {
		try {
			TCategory tCategory = getById(id);
			if (tCategory == null) {
				return -1;
			}
			RedisPoolFactory.delete(getListCache(tCategory.getParentId()));
			RedisPoolFactory.delete("category_list_"
					+ ((tCategory.getApp_id() == 0) ? 0
							: ("_app_id_" + tCategory.getApp_id())));
			return tCategoryDAO.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("get all error!!! ", e);
		}
	}

	public static void main(String[] args) {
		RoseAppContext context = new RoseAppContext();
		TCategoryHome home = context.getBean(TCategoryHome.class);
		try {

			System.out.println("sss+++"
					+ home.getAllSubList(TYPE_WEB, STATUS_OK, 48, 0, 10));
			System.out.println("sss+++");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sfsdljfkjslkdf");

	}

	public void delCache(int app_id) {
		RedisPoolFactory.delete(getListCache(app_id, 0));

	}
}
