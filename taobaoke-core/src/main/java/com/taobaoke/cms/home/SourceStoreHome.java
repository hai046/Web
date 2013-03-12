package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.SourceStoreDAO;
import com.taobaoke.cms.model.SourceStore;

@Component
public class SourceStoreHome {

	/**
	 * 收藏类型
	 */
	public static int TYPE_COLLECT = 1;
	public static int TYPE_OTHER = 0;
	@Autowired
	private SourceStoreDAO mSourceStoreDAO;

	/**
	 * 插入或者更新数据 ，如果数据库里面没有数据则insert or update
	 * 
	 * @param source_key
	 * @param value
	 * @param type
	 * @return
	 */
	public boolean insertOrUpdateValue(String source_key, String value, int type)
			throws SQLException {
		try {
			SourceStore mSourceStore = getBySource_key(source_key, type);
			if (mSourceStore != null) {
				update(source_key, type, value);
			} else {
				insert(source_key, value, type);
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		}
		return true;
	}

	public int getCount() throws SQLException {
		try {
			return mSourceStoreDAO.getCount();
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int getCount(int type) throws SQLException {
		try {
			return mSourceStoreDAO.getCount(type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public SourceStore getBySource_key(String source_key, int type)
			throws SQLException {
		try {
			return mSourceStoreDAO.getBySource_key(source_key, type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int insert(String source_key, String value, int type)
			throws SQLException {
		try {
			return mSourceStoreDAO.insert(source_key, value, type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int insert(String source_key, String value, int type,
			Date update_time) throws SQLException {
		try {
			return mSourceStoreDAO.insert(source_key, value, type, update_time);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public List<SourceStore> getTypeSourceList(int type, int offset, int limit)
			throws SQLException {
		try {
			return mSourceStoreDAO.getTypeSourceList(type, offset, limit);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	/**************************************************
	 * 获取保存数据value
	 * @param source_key
	 * @param type
	 * @return
	 * @throws SQLException
	 **************************************************/
	public String getSourceValue(String source_key, int type)
			throws SQLException {
		try {
			return mSourceStoreDAO.getSourceValue(source_key, type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int deleteBysource_key(String source_key, int type)
			throws SQLException {
		try {
			return mSourceStoreDAO.deleteBysource_key(source_key, type);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int deleteId(int id) throws SQLException {
		try {
			return mSourceStoreDAO.deleteId(id);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public int update(String source_key, int type, String value)
			throws SQLException {
		try {
			return mSourceStoreDAO.update(source_key, type, value);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	public SourceStore getBy_id(int id) throws SQLException {
		try {
			return mSourceStoreDAO.getBy_id(id);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

}
