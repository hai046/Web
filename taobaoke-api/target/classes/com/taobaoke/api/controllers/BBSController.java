package com.taobaoke.api.controllers;

import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobaoke.api.utils.MsgTools;
import com.taobaoke.cms.home.ArticleHome;
import com.taobaoke.cms.home.CommentHome;
import com.taobaoke.cms.home.SourceCounterHome;
import com.taobaoke.cms.model.Article;
import com.taobaoke.cms.model.Comment;

/**
 * 帖子相关的api
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
@Component
@Path("/bbs")
public class BBSController {
	private static final int DEFAULT_SIZE = 20;

	@Autowired
	ArticleHome articleHome;

	@Autowired
	CommentHome commentHome;

	@Autowired
	SourceCounterHome mSourceCounterHome;

	/**
	 * ==========================================<BR>
	 * 功能：帖子列表 <BR>
	 * 时间：2013-1-28 下午4:51:25 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 */
	@Get("/articleList")
	public String articleList(@Param("limit") int limit,
			@Param("offset") int offset,@Param("app_id")int app_id) {
		if (limit < 1) {
			limit = DEFAULT_SIZE;
		}
		try {
			List<Article> articleList = articleHome.getArticleList(
					ArticleHome.STATUS_OK,app_id, offset, limit);
			int count = articleHome.getCount(ArticleHome.STATUS_OK,app_id);
			JSONObject obj = new JSONObject();
			obj.put("articleList", filterArticle(articleList));
			obj.put("count", count);
			return "@" + MsgTools.createOKJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100001", "获取帖子列表失败");
		}
	}

	/**
	 * ==========================================<BR>
	 * 功能：获取帖子评论 <BR>
	 * 时间：2013-1-29 下午3:54:49 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param articleID
	 *            帖子的ID
	 * @param limit
	 * @param offset
	 * @param isContainArticle
	 *            是否包含帖子本身 ture/false
	 * @param counter
	 *            是否计数 if true 看过的人+1
	 * @return
	 */
	@Get("/getComments")
	public String getCommentList(@Param("articleID") int articleID,
			@Param("limit") int limit, @Param("offset") int offset,
			@Param("isContainArticle") boolean isContainArticle,
			@Param("isCounter") boolean counter) {
		if (articleID < 1) {
			return "@"
					+ MsgTools.createErrorJSON("100002",
							"忘了传articleID 吧  articleID>=1亲");
		}

		if (limit < 1)
			limit = DEFAULT_SIZE;
		try {
			List<Comment> commentList = commentHome.getCommentListAsc(
					articleID, offset, limit);
			int count = commentHome.getCommentCount(articleID);
			JSONObject obj = new JSONObject();
			obj.put("commentList", commentList);
			obj.put("count", count);
			if (isContainArticle) {
				try {
					Article mArticle = articleHome.getArticleByID(articleID);
					mArticle.setReplyNum(commentHome.getCommentCount(mArticle
							.getId()));
					JSONObject objArticle = getJsonObjByArtice(mArticle);
					objArticle.put("has_see", mSourceCounterHome
							.getSourceCount(articleID,
									SourceCounterHome.Type.ARTICLE));
					obj.put("article", objArticle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// TODO
			// ExtraItem mExtraItem = ExtraHome.getInstance().getByKey(
			// "switch_comment");
			// if (mExtraItem != null) {
			// String vaule = mExtraItem.getValue();
			// obj.put("switch_comment",
			// (vaule != null && vaule.equalsIgnoreCase("true")));
			// } else {
			// obj.put("switch_comment", true);
			// }
			if (counter) {
				try {
					mSourceCounterHome.updateSourceCounterAsc(articleID,
							SourceCounterHome.Type.ARTICLE);
					// mArticleCounterHome.updateArticleCounterAsc(articleID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return "@" + MsgTools.createOKJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "@"
					+ MsgTools.createErrorJSON("100001", "获取评论失败",
							e.getMessage());
		} finally {

		}

	}

	private JSONObject getJsonObjByArtice(Article mArticle) {
		JSONObject obj = new JSONObject();
		if (mArticle != null) {
			obj.put("id", mArticle.getId());
			obj.put("articleName", mArticle.getArticleName());
			obj.put("articleBody", mArticle.getArticleBody());
			obj.put("categoryId", mArticle.getCategoryId());
			obj.put("status", mArticle.getStatus());
			obj.put("createTime", mArticle.getCreateTime());
			obj.put("replyNum", mArticle.getReplyNum());

		}
		return obj;
	}

	private JSONArray filterArticle(List<Article> articleList) {
		JSONArray array = new JSONArray();
		for (Article mArticle : articleList) {
			if (mArticle.getArticleBody() != null
					&& mArticle.getArticleBody().length() > 140) {
				mArticle.setArticleBody(mArticle.getArticleBody().substring(0,
						120));
			}
			try {
				mArticle.setReplyNum(commentHome.getCommentCount(mArticle
						.getId()));
			} catch (Exception e) {
				System.out.println(e);
			}
			JSONObject obj = getJsonObjByArtice(mArticle);
			try {
				Integer has_see = mSourceCounterHome.getSourceCount(
						mArticle.getId(), SourceCounterHome.Type.ARTICLE);
				if (has_see == null) {
					obj.put("has_see", 0);
				} else {
					obj.put("has_see", has_see);
				}

			} catch (Exception e) {
				e.printStackTrace();
				obj.put("has_see", 0);
			}
			array.add(obj);
		}
		return array;
	}

	/**
	 * ==========================================<BR>
	 * 功能：提交评论 <BR>
	 * 时间：2013-1-28 下午4:50:53 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param comment
	 *            {@link com.taobaoke.cms.model.Comment}
	 * @param articleID
	 * @return
	 */
	@Post("/comment")
	public String comment(Comment comment, @Param("articleID") int articleID) {
		if (articleID < 0)
			return "@" + MsgTools.createErrorJSON("100002", "请选择评论的帖子");
		try {
			commentHome.insert(comment.getCommentorName(),
					comment.getReplieeName(), comment.getRepliedCommentId(),
					comment.getParentId(), articleID, comment.getMsg(),
					CommentHome.TYPE_ARTICLE, null);
			JSONObject obj = new JSONObject();
			obj.put("count", commentHome.getCommentCount(articleID));
			obj.put("extra", "api  说明  count代表目前所有的评论数目，也就是本条评论的数目");
			return "@" + MsgTools.createOKJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "@"
					+ MsgTools
							.createErrorJSON("100001", "评论失败", e.getMessage());
		}

	}

	/**
	 * ==========================================<BR>
	 * 功能：删除评论 <BR>
	 * 时间：2013-1-28 下午4:51:12 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param commentId
	 *            评论的id
	 * @return
	 */
	@Post("/commentDel")
	public String commentDel(@Param("commentId") int commentId) {
		try {
			commentHome.delCommentByID(commentId);
			return "@" + MsgTools.createOKJSON(null);
		} catch (Exception e) {
			e.printStackTrace();
			return "@" + MsgTools.createErrorJSON("100001", "删除评论失败");
		}

	}
}
