package com.taobaoke.cms.home;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.DailyJokeDAO;
import com.taobaoke.cms.model.DailyJoke;

/**
 * 时间格式会处理成 yyyy-MM-dd 00:00:00样式，传入时候不用处理
 * 
 * @author Haizhu
 * 
 */
@Component
public class DailyJokeHome {

	@Autowired
	private DailyJokeDAO mDailyJokeDAO;

	private Date filterDateFormat(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.set(can.get(Calendar.YEAR), can.get(Calendar.MONTH),
				can.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return can.getTime();
	}

	private Date offsetDate(int offset, Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.add(Calendar.DAY_OF_MONTH, offset);
		can.set(can.get(Calendar.YEAR), can.get(Calendar.MONTH),
				can.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return can.getTime();
	}

	/**
	 * 添加段子
	 * 
	 * @param content
	 * @param afterToday
	 *            true今天之后，反之今天之前
	 * @return
	 */
	public boolean inSertContent(String content, boolean afterToday) {
		Date date = afterToday ? mDailyJokeDAO.getLastDate() : mDailyJokeDAO
				.getBeforeDate();
		if (date == null) {
			date = filterDateFormat(new Date());
			if (getByDate(date) != null)
				return false;
		} else {
			date = offsetDate(afterToday ? 1 : -1, date);
		}
		DailyJoke dj = new DailyJoke();
		dj.setContent(content);
		dj.setDate(date);
		return inSert(dj);
	}

	public boolean inSert(DailyJoke mDailyJoke) {
		try {
			if (getByDate(mDailyJoke.getDate()) != null) {
				return false;
			}
			mDailyJoke.setDate(filterDateFormat(mDailyJoke.getDate()));
			return mDailyJokeDAO.insert(mDailyJoke) == 1 ? true : false;
		} catch (Exception e) {
			e.toString();
		}
		return false;
	}

	public DailyJoke getById(int id) {
		try {
			return mDailyJokeDAO.getById(id);
		} catch (Exception e) {
			e.toString();
		}
		return null;
	}

	public DailyJoke getByDate(Date date) {
		try {
			date = filterDateFormat(date);
			return mDailyJokeDAO.getByDate(date);
		} catch (Exception e) {
			e.toString();
		}
		return null;
	}

	public List<DailyJoke> getAll(Date date, int offset, int limit) {
		try {
			date = filterDateFormat(date);
			return mDailyJokeDAO.getAll(date, offset, limit);
		} catch (Exception e) {
			e.toString();
		}
		return null;
	}

	/**
	 * 得到的为当前时间点以前的【包含今天】笑话
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<DailyJoke> getAllBeforeNow(int offset, int limit) {
		try {
			return mDailyJokeDAO.getAllBeforeNow(offset, limit);
		} catch (Exception e) {
			e.toString();
		}
		return null;
	}

	public List<DailyJoke> getAll(int offset, int limit) {
		try {
			return mDailyJokeDAO.getAll(offset, limit);
		} catch (Exception e) {
			e.toString();
		}
		return null;
	}

	public int getCount() {
		try {
			return mDailyJokeDAO.getCount();
		} catch (Exception e) {
			e.toString();
		}
		return 0;
	}

	/**
	 * 
	 * @param date
	 *            删除date[包括date]之前的段子
	 * @return
	 */
	public boolean deleteBefore(Date date) {
		try {
			return mDailyJokeDAO.deleteBefore(date) >= 1 ? true : false;
		} catch (Exception e) {
			e.toString();
		}
		return false;
	}

	/**
	 * 删除今天起多少天以前的段子
	 * 
	 * @param offset
	 * @return
	 */
	public boolean deleteBefore(int offset) {
		try {

			return mDailyJokeDAO.deleteBefore(offsetDate(-Math.abs(offset),
					new Date())) >= 1 ? true : false;
		} catch (Exception e) {
			e.toString();
		}
		return false;
	}

	public boolean update(String content, int id) {
		try {
			return mDailyJokeDAO.update(content, id) == 1 ? true : false;
		} catch (Exception e) {
			e.toString();
		}
		return false;
	}

	public boolean update(String content, Date date) {
		try {
			return mDailyJokeDAO.update(content, date) == 1 ? true : false;
		} catch (Exception e) {
			e.toString();
		}
		return false;
	}

}
