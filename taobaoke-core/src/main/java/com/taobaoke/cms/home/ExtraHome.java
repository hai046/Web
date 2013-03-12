package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.ExtraDAO;
import com.taobaoke.cms.model.ExtraItem;

@Component
public class ExtraHome {

	@Autowired
	private ExtraDAO mExtraDAO;

	private static ApplicationContext context;

	private static ExtraHome instance;

	public static ExtraHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (ExtraHome) BeanFactoryUtils.beanOfType(context,
				ExtraHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	public boolean insert(ExtraItem mExtraItem) throws SQLException {
		try {
			deleteByKey(mExtraItem.getKey());
			return mExtraDAO.insert(mExtraItem) == 1 ? true : false;
		} catch (Exception e) {
			throw new SQLException("插入数据失败");
		}

	}

	public ExtraItem getById(int id) throws SQLException {
		try {
			return mExtraDAO.getById(id);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public List<ExtraItem> getAll(int offset, int limit) throws SQLException {
		try {
			return mExtraDAO.getAll(offset, limit);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public int getCount() throws SQLException {
		try {
			return mExtraDAO.getCount();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public int deleteById(int id) throws SQLException {
		try {
			return mExtraDAO.deleteById(id);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

	public String getValueByKey(String type) throws SQLException {
		try {
			return mExtraDAO.getValueByKey(type);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public ExtraItem getByKey(String type) throws SQLException {
		try {
			return mExtraDAO.getByKey(type);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public int deleteByKey(String key) throws SQLException {
		try {
			return mExtraDAO.deleteByKey(key);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public boolean updateById(int id, String name, String value)
			throws SQLException {
		try {
			return mExtraDAO.updateById(id, name, value) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

}
