package com.taobaoke.cms.controllers;

import java.net.URLEncoder;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.home.FeedBackHome;
import com.taobaoke.cms.model.FeedBack;
@Component
@Path("feedBack")
public class FeedBackController {
	private int DEFAULT_SIZE = 20;
	@Autowired
	private FeedBackHome mFeedBackHome;

	@Get("/list")
	public String categoryList(Invocation inv, @Param("currentPage") int page) {
		page = page < 1 ? 1 : page;
		int offset = (page - 1) * DEFAULT_SIZE;
		try {

			int count = mFeedBackHome.getCount();
			List<FeedBack> list = mFeedBackHome.getAll(offset, DEFAULT_SIZE);
			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;

			inv.addModel("list", list);
			inv.addModel("count", count);
			inv.addModel("pageCount", pageCount);
			inv.addModel("currentPage", page);
			// inv.addModel("callBackForNotSameRoot", URLEncoder.encode(
			// "/feedBack/list?currentPage=" + page, "utf-8"));
			inv.addModel("callBack",
					URLEncoder.encode("./list?currentPage=" + page, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "feedBack/list";
	}

	@Get("update")
	public String getFeedBack(@Param("id") int id) {
		mFeedBackHome.updateStatus(FeedBackHome.STATUS_CONDUCT, id);
		return "r:/feedBack/list";
	}
	@Get("delete")
	public String delete(@Param("id") int id) {
		mFeedBackHome.deleteById(id);
		return "r:/feedBack/list";
	}
	
	
	@Get("getStatus")
	public String getStatus(@Param("status") int status,
			@Param("offset") int offset, @Param("limit") int limit) {

		return "@id=" + mFeedBackHome.getAllByStatus(status, offset, limit);
	}

}
