package com.taobaoke.cms.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.FeedBackDAO;
import com.taobaoke.cms.model.FeedBack;

@Component
public class FeedBackHome {

	/**
	 * 新添加的信用信息
	 */
	public static final int STATUS_NEW = 1;
	/**
	 * 处理过该建议标示
	 */
	public static final int STATUS_CONDUCT = 2;

	@Autowired
	private FeedBackDAO mFeedBackDAO;

	public boolean insert(FeedBack feedBack) {

		try {
			feedBack.setStatus(STATUS_NEW);
			return mFeedBackDAO.insert(feedBack)==1?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public FeedBack getById(int id) {
		try {
			return mFeedBackDAO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<FeedBack> getAllByStatus(int status, int offset, int limit) {
		try {
			if (isRightStatus(status)) {
				return mFeedBackDAO.getAllByStatus(status, offset, limit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<FeedBack> getAll(int offset, int limit) {
		try {
			return mFeedBackDAO.getAll(offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getCount() {
		try {
			return mFeedBackDAO.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getCountByStatus(int status) {
		try {
			if (isRightStatus(status)) {
				return mFeedBackDAO.getCountByStatus(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteById(int id) {
		try {
			return mFeedBackDAO.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateStatus(int status, int id) {
		try {
			if (isRightStatus(status)) {
				return mFeedBackDAO.updateStatus(status, id) == 1 ? true
						: false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isRightStatus(int status) {
		if (status == STATUS_NEW || status == STATUS_CONDUCT) {
			return true;
		} else
			return false;
	}

}
