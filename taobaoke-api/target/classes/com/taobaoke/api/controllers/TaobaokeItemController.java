package com.taobaoke.api.controllers;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.crawler.ItemDetailCrawler;
import com.taobaoke.api.service.CommentService;
import com.taobaoke.api.utils.IosUtil;
import com.taobaoke.api.utils.JSonFiledPutUtil;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.TCategoryHome;
import com.taobaoke.cms.home.TItemDescHome;
import com.taobaoke.cms.home.TItemDetailHome;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TItemImgHome;
import com.taobaoke.cms.home.TItemRateHome;
import com.taobaoke.cms.model.TCategory;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.model.TItemAllDetail;
import com.taobaoke.cms.model.TItemDetail;
import com.taobaoke.cms.model.TItemImg;
import com.taobaoke.cms.model.TItemRate;

@Path("items")
public class TaobaokeItemController {

	private static final int DEF_NUM = 40;
	@Autowired
	private TCategoryHome mTCategoryHome;
	@Autowired
	private TItemHome mTItemHome;
	@Autowired
	private TItemDetailHome mTItemDetailHome;
	@Autowired
	private TItemDescHome mTItemDescHome;
	@Autowired
	private TItemImgHome mTItemImgHome;

	@Autowired
	private CommentService mCommentService;

	@Get("comments/{numIid:[0-9]+}")
	public String getCommentsByNumIid(@Param("numIid") long numIid,
			@Param("offset") int offset, @Param("limit") int limit) {
		if (numIid < 1) {
			return "@" + MsgTools.createErrorJSON("1000008", "没有该数据");
		}
		JSONArray arrays = null;
		List<TItemRate> list;
		try {
			list = mCommentService.getComment(numIid, offset, limit);
			if (list == null || list.size() < 1) {
				return "@"
						+ MsgTools.createErrorJSON("1000006", "获取数据失败，请稍后再试");
			} else {
				arrays = getCommentsJsonArray(list);
				return "@" + MsgTools.createOKJSON(arrays).toJSONString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("1000007", e.getMessage());
		}

	}

	@Get("{numIid:[0-9]+}")
	public String getItemById(@Param("numIid") long numIid) {
		if (numIid < 0)
			return "@"
					+ MsgTools.createErrorJSON("100003", "请传入商品id")
							.toJSONString();
		try {
			TItemDetail item = mTItemDetailHome.getByNumiid(numIid);
			JSONObject obj = null;
			if (item == null) {
				TItemAllDetail mTItemAllDetail = ItemDetailCrawler
						.saveTaobaokeItem(numIid);
				if (mTItemAllDetail == null) {
					return "@" + MsgTools.createErrorJSON("100005", "获取商品信息失败");
				} else {
					obj = getItemDetail(mTItemAllDetail.getmItemDetail(),
							mTItemAllDetail.getImgs(),
							mTItemAllDetail.getDesc(),
							mTItemHome.getByNumId(numIid));

				}

			} else {
				obj = getItemDetail(item,
						mTItemImgHome.getListByNumiid(numIid),
						mTItemDescHome.getByNumIid(numIid),
						mTItemHome.getByNumId(numIid));
			}

			checkComment(numIid);

			return "@" + MsgTools.createOKJSON(obj);
		} catch (SQLException e) {
			log.warn(e);
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100004", "获取商品信息失败");
		}

	}

	/**
	 * 开启一个新线程，检查是否有该商品的评论
	 * 
	 * @param numIid
	 */
	private void checkComment(final long numIid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int count;
				try {
					count = TItemRateHome.getInstance()
							.getCountByNumIid(numIid);
					if (count < 1) {
						mCommentService.getComment(numIid, 0, 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	/**
	 * 
	 * @param tcId
	 * @param orderBy
	 * @param isAsc
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Get("getItemsById")
	public String getItemsBytcId(@Param("id") int tcId,
			@Param("orderBy") String orderBy, @Param("asc") boolean isAsc,
			@Param("offset") int offset, @Param("limit") int limit) {
		if (limit == 0)
			limit = DEF_NUM;
		do {
			try {
				if (orderBy == null) {
					orderBy = "commission";
				} else {
					String columsName = "seller_credit_score,num_iid,price,commission,commission_rate,commission_num,commission_volume,auto_send,volume, auto_send,";
					if (!columsName.contains(orderBy)) {
						orderBy = "commission";
					}
				}
				List<TItem> list = mTItemHome.getAll(tcId, orderBy, isAsc,
						offset, limit);
				if (list == null)
					break;
				JSONArray arrays = new JSONArray();
				for (TItem item : list) {
					arrays.add(JSonFiledPutUtil.getTaobokeItemObject(item));
				}
				JSONObject obj = new JSONObject();
				obj.put("list", arrays);

				obj.put("hasCategory", false);

				return "@" + MsgTools.createOKJSON(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} while (false);
		return "@" + MsgTools.createErrorJSON("100002", "系统错误");
	}

	/**
	 * 得到父分类的商品
	 * 
	 * @param tcId
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Get("getItemsByParentsId")
	public String getItemsByParentsId(@Param("id") int tcId,
			@Param("orderBy") String orderBy, @Param("asc") boolean isAsc,
			@Param("offset") int offset, @Param("limit") int limit,
			@Param("app_id") int app_id) {

		if (limit == 0)
			limit = DEF_NUM;
		do {
			try {
				if (orderBy == null) {
					orderBy = "commission";
				} else {
					String columsName = "seller_credit_score,num_iid,price,commission,commission_rate,commission_num,commission_volume,auto_send,volume, auto_send,";
					if (!columsName.contains(orderBy)) {
						orderBy = "commission";
					}
				}
				List<TItem> list = mTItemHome.getAllByTCategoryParentId(tcId,
						orderBy, isAsc, offset, limit);
				if (list == null)
					break;
				JSONArray arrays = new JSONArray();
				for (TItem item : list) {
					arrays.add(JSonFiledPutUtil.getTaobokeItemObject(item));
				}
				JSONObject obj = new JSONObject();
				obj.put("list", arrays);

				if (offset == 0) {
					JSONObject jobj = getCategoryById(tcId, app_id, offset,
							limit);
					if (jobj != null) {
						obj.put("categorys", jobj);
						obj.put("hasCategory", true);
					} else {
						obj.put("hasCategory", false);
					}
				} else {
					obj.put("hasCategory", false);
				}

				return "@" + MsgTools.createOKJSON(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while (false);
		return "@" + MsgTools.createErrorJSON("100002", "系统错误");
	}

	/**
	 * 得到相关的分类的信息
	 * 
	 * @param id
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Get("getCategory")
	public String getCategorys(@Param("id") int id,
			@Param("offset") int offset, @Param("limit") int limit,
			@Param("app_id") int app_id) {
		if (limit == 0)
			limit = DEF_NUM;
		try {
			return "@"
					+ MsgTools.createOKJSON(
							getCategoryById(id, app_id, offset, limit))
							.toJSONString();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "@" + MsgTools.createErrorJSON("100008", "获取类别失败");
	}

	private JSONObject getCategoryById(int id, int app_id, int offset, int limit)
			throws SQLException {
		TCategory parentCategory = mTCategoryHome.getById(id, app_id);
		if (parentCategory == null)
			return null;

		List<TCategory> list = mTCategoryHome.getAllSubList(
				TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK,
				parentCategory.getId(), offset, limit);
		if (list != null && list.size() > 0) {
			JSONObject parents = getCategoryObject(parentCategory);
			JSONArray contains = new JSONArray();
			for (TCategory son : list) {
				contains.add(getCategoryObject(son));
			}
			parents.put("items", contains);
			return parents;
		}

		return null;
	}

	@Get("getAllParentsCategorys")
	public String getParentsCategorys(Invocation inv,
			@Param("offset") int offset, @Param("limit") int limit,
			@Param("app_key") String app_key, @Param("v") int v,
			@Param("app_id") int app_id) {
		if (offset >= 20)// TODO 当分类大于20种的时候 请扩大之
		{
			return "@";
		}

		boolean isIosDev = IosUtil.isIosIPadDev(app_key, v)
				|| IosUtil.isIosIPhoneDev(app_key, v)
				|| IosUtil.isAndroidPassed(app_key, v);
		if (limit == 0)
			limit = DEF_NUM;
		do {
			try {
				List<TCategory> parentslist = mTCategoryHome.getAllParentList(
						TCategoryHome.TYPE_MOBILE, app_id,
						TCategoryHome.STATUS_OK, offset, limit);
				if (parentslist == null)
					break;
				JSONArray arrays = new JSONArray();
				for (TCategory mTCategory : parentslist) {
					if (isIosDev
							&& (mTCategory.getName().equals("男用器具")
									|| mTCategory.getName().equals("女用器具")
									|| mTCategory.getName().equals("情趣用品") || mTCategory
									.getName().equals("情趣服饰"))) {
						continue;
					}

					if (mTCategory.getApp_id() != app_id) {
						mTCategoryHome.delCache(app_id);
						continue;
					}

					JSONObject parents = getCategoryObject(mTCategory);
					List<TCategory> list = mTCategoryHome.getAllSubList(
							TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK,
							mTCategory.getId(), 0, 20);// 子分类同样也不会超过20
					StringBuilder sb = new StringBuilder();
					if (list != null) {
						for (TCategory son : list) {
							if(son.getApp_id()!=app_id)
								continue;
							// contains.add(getCategoryObject(son));
							sb.append(son.getName() + "/");
						}
					}
					parents.put("explain", sb.length() > 0 ? sb.toString()
							.substring(0, sb.length() - 1) : "");
					arrays.add(parents);
				}

				return "@" + MsgTools.createOKJSON(arrays).toJSONString();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		} while (false);
		return "@" + MsgTools.createErrorJSON("100001", "获取类别失败");
	}

	@Get("favourite")
	public String favourite(@Param("numIid") List<String> numIids,
			@Param("token") String token) {

		return "@" + MsgTools.createErrorJSON("100001", "此接口还在开发中");
	}

	Log log = LogFactory.getLog(getClass());

	private JSONArray getCommentsJsonArray(List<TItemRate> list) {
		if (list == null || list.size() < 1)
			return null;
		JSONArray arrays = new JSONArray();
		for (TItemRate mTItemRate : list) {
			arrays.add(mTItemRate);

		}
		return arrays;
	}

	private JSONObject getItemDetail(TItemDetail item, List<TItemImg> imgs,
			String desc, TItem iTItem) {
		JSONObject obj = JSonFiledPutUtil.getTaobaokeDetailObject(item);
		obj.put("desc", desc);
		JSONArray arrays = getImgagesJson(imgs);
		obj.put("imgs", arrays);
		if (iTItem != null) {
			obj.put("click_url", iTItem.getClickUrl());
			obj.put("volume", iTItem.getVolume());
			obj.put("sellerCreditScore", iTItem.getSellerCreditScore());
			obj.put("cashOndelivery", iTItem.getCashOndelivery());
		}
		return obj;
	}

	public JSONObject getCategoryObject(TCategory mTCategory) {
		if (mTCategory == null)
			return null;
		JSONObject obj = new JSONObject();
		obj.put("cid", mTCategory.getCid());
		obj.put("iconUrl", mTCategory.getRealIconUrl());
		obj.put("id", mTCategory.getId());
		obj.put("name", mTCategory.getName());
		return obj;
	}

	private JSONArray getImgagesJson(List<TItemImg> list) {
		if (list == null)
			return null;
		JSONArray arrays = new JSONArray();
		for (TItemImg img : list) {
			arrays.add(img.getPicUrl());
		}

		return arrays;
	}

}
