package com.taobaoke.api.controllers;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.IosUtil;
import com.taobaoke.api.utils.JSonFiledPutUtil;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TopicHome;
import com.taobaoke.cms.home.TopicItemHome;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.model.Topic;
import com.taobaoke.cms.model.TopicItem;

@Component
@Path("/topic")
public class TopicController {
	@Autowired
	TopicHome topicHome;
	@Autowired
	TopicItemHome topicItemHome;

	@Autowired
	TItemHome mTItemHome;
	private static final int DEFAULT_SIZE = 80;

	/**
	 * ==========================================<BR>
	 * 功能： 专题 <BR>
	 * 时间：2013-2-19 下午2:48:57 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param page
	 * @param size
	 * @param app_key
	 * @param v
	 * @param app_id
	 * @return
	 */
	@Get("/topicList")
	public String topicList(@Param("page") int page, @Param("pgSize") int size,
			@Param("app_key") String app_key, @Param("v") int v,
			@Param("app_id") int app_id) {
		page = page < 1 ? 1 : page;
		if (size < 1) {
			size = DEFAULT_SIZE;
		}
		boolean isPad = (app_key != null && app_key.contains("pad"));
		int offset = (page - 1) * size;
		try {
			List<Topic> topicList = topicHome.getAll(TopicHome.STATUS_OK,
					app_id, offset, (IosUtil.isIosIPadDev(app_key, v)
							|| IosUtil.isIosIPhoneDev(app_key, v) || IosUtil
							.isAndroidPassed(app_key, v)) ? 1 : size);// TODO
			int count = topicHome.getCount(TopicHome.STATUS_OK, app_id);
			long pageCount = count / (long) size;
			pageCount = pageCount * size == count ? pageCount : pageCount + 1;
			JSONArray arrays = getTopics(topicList, isPad);
			JSONObject obj = new JSONObject();
			obj.put("list", arrays);
			obj.put("pageCount", pageCount);
			return "@" + MsgTools.createOKJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@" + MsgTools.createErrorJSON("400001", "获取主题信息失败");
	}

	private JSONArray getTopics(List<Topic> topicList, boolean isPad) {
		JSONArray arrays = new JSONArray();
		if (topicList != null)
			for (Topic mTopic : topicList) {
				JSONObject obj = new JSONObject();
				obj.put("name", mTopic.getTopicName());
				if (isPad) {
					if (mTopic.getRealTopicPadPic() == null
							|| mTopic.getRealTopicPadPic().isEmpty()) {
						obj.put("pic", mTopic.getRealTopicPic());
					} else {
						obj.put("pic", mTopic.getRealTopicPadPic());
					}

				} else {
					obj.put("pic", mTopic.getRealTopicPic());
				}
				obj.put("content", mTopic.getContent());
				obj.put("id", mTopic.getId());
				obj.put("numiid", mTopic.getNumIid());
				obj.put("url", mTopic.getUrl());
				obj.put("type", mTopic.getType());
				arrays.add(obj);
			}
		return arrays;
	}

	/**
	 * ==========================================<BR>
	 * 功能： topicId = 0 代表首页推荐，否则为专题相关下面的列表 <BR>
	 * 时间：2013-2-19 下午2:49:10 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param topicId
	 * @param page
	 * @param size
	 * @param app_key
	 * @param v
	 * @param app_id
	 * @return
	 */
	@Get("/itemList")
	public String itemList(@Param("id") int topicId, @Param("page") int page,
			@Param("pgSize") int size, @Param("app_key") String app_key,
			@Param("v") int v, @Param("app_id") int app_id) {
		// 这是应用 淘色的审核机制
		if (topicId == 0
				&& app_id == 0
				&& (IosUtil.isIosIPadDev(app_key, v)
						|| IosUtil.isIosIPhoneDev(app_key, v) || IosUtil
							.isAndroidPassed(app_key, v))) {
			long ids[] = new long[] { 21378108335L, 10629917721L, 10710451277L,
					5579576570L, 16382749200L, 12371442528L, 15970415546L,
					15365632880L, 3914710075L, 16671088829L };
			JSONArray arrays = new JSONArray();
			for (long numIid : ids) {
				try {
					TItem item = mTItemHome.getByNumId(numIid);
					if (item != null) {
						JSONObject obj = JSonFiledPutUtil
								.getTaobokeItemObject(item);

						arrays.add(obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("list", arrays);
			obj.put("pageCount", 1);
			return "@" + MsgTools.createOKJSON(obj);
		}

		page = page < 1 ? 1 : page;
		if (size < 1) {
			size = DEFAULT_SIZE;
		}
		int offset = (page - 1) * size;
		try {
			List<TopicItem> topicItemList = null;
			// if (topicId == 0) {
			topicItemList = topicItemHome.getAll(topicId, app_id,
					TopicItemHome.STATUS_OK, offset, size);
			// } else {
			// topicItemList = topicItemHome.getAll(topicId,a
			// TopicItemHome.STATUS_OK, offset, size);
			// }

			int count = topicItemHome
					.getCount(topicId, TopicItemHome.STATUS_OK);

			long pageCount = count / (long) size;
			pageCount = pageCount * size == count ? pageCount : pageCount + 1;

			JSONArray arrays = getTopicItems(topicItemList);
			JSONObject obj = new JSONObject();
			obj.put("list", arrays);
			obj.put("pageCount", pageCount);
			return "@" + MsgTools.createOKJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@" + MsgTools.createErrorJSON("400002", "获取主题详细信息失败");
	}

	private JSONArray getTopicItems(List<TopicItem> topicItemList) {
		JSONArray arrays = new JSONArray();
		if (topicItemList != null)
			for (TopicItem mTopic : topicItemList) {
				try {
					TItem item = mTItemHome.getById(mTopic.gettItemId());
					if (item != null) {
						JSONObject obj = JSonFiledPutUtil
								.getTaobokeItemObject(item);
						obj.put("name", mTopic.gettItemName());

						if (mTopic.getRealPic() != null
								&& mTopic.getRealPic().startsWith("http://")) {
							obj.put("pic_url", mTopic.getRealPic());
						}
						arrays.add(obj);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		return arrays;
	}

}
