package com.taobaoke.cms.controllers;

import java.net.URLEncoder;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.home.ArticleHome;
import com.taobaoke.cms.home.CommentHome;
import com.taobaoke.cms.model.Article;
import com.taobaoke.cms.model.Comment;
import com.taobaoke.cms.utils.CookieUtil;

@Component
@Path("/bbs")
public class BBSController {
	private static final int DEFAULT_SIZE = 20;

	@Autowired
	ArticleHome articleHome;

	@Autowired
	CommentHome commentHome;

	@Get("/articleList")
	public String articleList(Invocation inv, @Param("currentPage") int page) {
		page = page < 1 ? 1 : page;
		int offset = (page - 1) * DEFAULT_SIZE;

		try {
			// fix cache bug :因为所有帖子都是ok
			List<Article> articleList = null;
			int count = 0;
			if (CookieUtil.getApp_id() < 0) {
				articleList = articleHome.getArticleList(ArticleHome.STATUS_OK,
						offset, DEFAULT_SIZE);
				count = articleHome.getCount(ArticleHome.STATUS_OK);
			} else {
				articleList = articleHome.getArticleList(ArticleHome.STATUS_OK,
						CookieUtil.getApp_id(), offset, DEFAULT_SIZE);
				count = articleHome.getCount(ArticleHome.STATUS_OK,
						CookieUtil.getApp_id());
			}

			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;

			inv.addModel("articleList", articleList);
			inv.addModel("count", count);
			inv.addModel("pageCount", pageCount);
			inv.addModel("currentPage", page);
			inv.addModel("callBackForNotSameRoot", URLEncoder.encode(
					"/bbs/articleList?currentPage=" + page, "utf-8"));
			inv.addModel("callBack", URLEncoder.encode(
					"./articleList?currentPage=" + page, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/articleList";
	}

	@Get("/articleUpdate")
	public String articleUpdateView(Invocation inv,
			@Param("callback") String callback,
			@Param("articleId") int articleId) {

		try {
			if (articleId > 0) {
				inv.addModel("article", articleHome.getArticleByID(articleId));
			}

			inv.addModel("callBack", URLEncoder.encode(callback, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/article";
	}

	@Post("/articleUpdate")
	public String articleUpdate(Invocation inv,
			@Param("callback") String callback, Article article) {

		try {
			if (article.getId() < 1) {
				int id = articleHome.insert(article.getArticleName(),
						article.getApp_id(), article.getArticleBody(),
						article.getCategoryId(), article.getStatus(),
						article.getCreateTime());
				article.setId(id);
			} else {
				articleHome.update(article.getId(), article.getApp_id(),
						article.getArticleName(), article.getArticleBody(),
						article.getCategoryId(), article.getStatus(),
						article.getCreateTime());
			}
			inv.addModel("article", article);
			inv.addModel("callBack", URLEncoder.encode(callback, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:./articleUpdate?articleId=" + article.getId() + "&callback="
				+ callback;
	}

	@Post("/articleDel")
	public String articleDel(Invocation inv,
			@Param("callback") String callback,
			@Param("articleId") int articleId,
			@Param("currentPage") int currentPage) {

		try {
			articleHome.deleteById(articleId);
			commentHome.delCommentBySource(articleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:./articleList?currentPage=" + currentPage;
	}

	@Get("/comment")
	public String getCommentList(Invocation inv,
			@Param("currentPage") int page, @Param("articleID") int articleID,
			@Param("callback") String callback) {
		page = page < 1 ? 1 : page;
		int offset = (page - 1) * DEFAULT_SIZE;

		try {
			List<Comment> commentList = commentHome.getCommentList(articleID,
					offset, DEFAULT_SIZE);
			int count = commentHome.getCommentCount(articleID);

			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;

			if (articleID > 0) {
				inv.addModel("article", articleHome.getArticleByID(articleID));
			}

			inv.addModel("commentList", commentList);
			inv.addModel("count", count);
			inv.addModel("pageCount", pageCount);
			inv.addModel("currentPage", page);
			// inv.addModel("callBack", URLEncoder.encode(callback, "utf-8"));
			inv.addModel("callBack", callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bbs/commentList";
	}

	@Post("/comment")
	public String comment(Invocation inv, @Param("currentPage") int page,
			Comment comment, @Param("callback") String callback) {
		try {
			commentHome.insert(comment.getCommentorName(),
					comment.getReplieeName(), comment.getRepliedCommentId(),
					comment.getParentId(), comment.getSourceId(),
					comment.getMsg(), comment.getType(),
					comment.getCreateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:./comment?currentPage=" + page + "&articleID="
				+ comment.getSourceId() + "&callback=" + callback;
	}

	@Post("/commentDel")
	public String commentDel(Invocation inv, @Param("currentPage") int page,
			@Param("commentId") int commentId,
			@Param("articleID") int articleID,
			@Param("callback") String callback) {
		try {
			commentHome.delCommentByID(commentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:./comment?currentPage=" + page + "&articleID=" + articleID
				+ "&callback=" + callback;
	}
}
