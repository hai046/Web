package com.taobaoke.api.controllers;

import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;

import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.FeedBackHome;
import com.taobaoke.cms.model.FeedBack;

@Path("feedBack")
public class FeedBackController {
	@Autowired
	private FeedBackHome mFeedBackHome;

	@Post("commit")
	public String commit(FeedBack mFeedBack) {
		if (mFeedBackHome.insert(mFeedBack)) {
			return "@" + MsgTools.createOKJSON(null);
		}
		return "@" + MsgTools.createErrorJSON("200001", "添加留言失败");
	}

}
