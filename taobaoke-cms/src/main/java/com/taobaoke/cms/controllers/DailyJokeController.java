package com.taobaoke.cms.controllers;

import java.util.Date;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.home.DailyJokeHome;
import com.taobaoke.cms.model.DailyJoke;

@Component
@Path("joke")
public class DailyJokeController {

	@Autowired
	private DailyJokeHome mDailyJokeHome;
	private int DEFAULT_SIZE = 20;

	@Get("list")
	public String getJoke(Invocation inv, @Param("date") Date date,
			@Param("currentPage") int page) {
		page = page < 1 ? 1 : page;
		int offset = (page - 1) * DEFAULT_SIZE;
		List<DailyJoke> list = null;
		if (date == null) {
			list = mDailyJokeHome.getAll(offset, DEFAULT_SIZE);
		} else {
			list = mDailyJokeHome.getAll(date, offset, DEFAULT_SIZE);
		}
		int count = mDailyJokeHome.getCount();
		long pageCount = count / (long) DEFAULT_SIZE;
		pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
				: pageCount + 1;

		inv.addModel("list", list);
		inv.addModel("count", count);
		inv.addModel("pageCount", pageCount);
		inv.addModel("currentPage", page);

		return "dailyJoke/list";
	}

	Log log = LogFactory.getLog(getClass());

	@Post("add")
	public String addJoke(Invocation inv, DailyJoke mDailyJoke,
			@Param("flag") String flag) {
		log.error("----------------------flag="+flag);
		boolean afterToday=false;
		if(flag==null||flag.endsWith("after"))
		{
			afterToday=true;
		}
			
		if (mDailyJoke == null) {
			return "@";
		}
		if (mDailyJoke.getDate() == null) {
			if (!mDailyJokeHome.inSertContent(mDailyJoke.getContent(),afterToday)) {
				return "@";
			}
		} else {
			if (!mDailyJokeHome.inSert(mDailyJoke)) {
				return "@";
			}
		}
		return "r:./list";
	}

	@Post("update")
	public String update(Invocation inv, DailyJoke mDailyJoke,
			@Param("currentPage") int page) {
		boolean flag = false;
		if (mDailyJoke.getDate() == null && mDailyJoke.getId() > 0) {
			flag = mDailyJokeHome.update(mDailyJoke.getContent(),
					mDailyJoke.getId());
		} else {
			flag = mDailyJokeHome.update(mDailyJoke.getContent(),
					mDailyJoke.getDate());
		}
		inv.addModel("currentPage", page);
		return "r:./list";
	}

	@Post("deleteBefore")
	public String delete(Invocation inv, @Param("offset") int offset) {
		if(offset>0)
		{
			mDailyJokeHome.deleteBefore(offset);
		}
		return "r:./list";
	}

}
