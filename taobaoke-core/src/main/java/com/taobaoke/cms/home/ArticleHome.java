package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.ArticleDAO;
import com.taobaoke.cms.model.Article;
import com.taobaoke.cms.redis.RedisPoolFactory;
import com.taobaoke.cms.utils.Apps;

@Component
public class ArticleHome {
	public static int STATUS_OK = 0;
	public static int STATUS_HIDE = 0;
	public static int STATUS_ALL = -1;

	@Autowired
	private ArticleDAO articleDAO;

	private static ApplicationContext context;

	private static ArticleHome instance;

	public static ArticleHome getInstance() {
		if (instance != null) {
			return instance;
		}
		if (context == null) {
			throw new RuntimeException("Initial Error");
		}
		instance = (ArticleHome) BeanFactoryUtils.beanOfType(context,
				ArticleHome.class);
		return instance;
	}

	@Autowired
	public void setContext(ApplicationContext rContext) {
		context = rContext;
	}

	public int insert(String articleName, int app_id, String articleBody,
			int categoryId, int status, Date createTime) throws SQLException {
		int id = -1;
		try {
			id = articleDAO.insert(articleName, app_id, articleBody,
					categoryId, status, createTime);
			Article article = this.getArticleByID(id);
			delCacheById(article, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" insert FeedComment error articleName="
					+ articleName + ", articleBody=" + articleBody, e);
		}
		return id;
	}

	public Article getArticleByID(int id) throws SQLException {
		Article article = null;
		try {
			article = RedisPoolFactory.get(singleCacheKey(id), Article.class);
			if (article != null) {
				return article;
			}
			article = articleDAO.getById(id);
			RedisPoolFactory.set(singleCacheKey(id), article, 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get Comment error id=" + id, e);
		}
		return article;
	}

	/**
	 * ==========================================<BR>
	 * 功能：此接口只提供给cms调用 没加缓存 <BR>
	 * 时间：2013-2-21 下午5:05:46 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param status
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<Article> getArticleList(int status, int offset, int limit)
			throws SQLException {
		List<Article> list = null;
		try {

			list = articleDAO.getList(status, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get getFeedCommentList error status="
					+ status + ", offset=" + offset + ", limit=" + limit, e);
		}
		return list;
	}

	public List<Article> getArticleList(int status, int app_id, int offset,
			int limit) throws SQLException {
		List<Article> list = null;
		try {
			String cacheKey = listCacheKey(status, app_id);
			list = getListFromCache(cacheKey, offset, limit);
			if (list != null
					&& (list.size() == limit || list.size() == this.getCount(
							status, app_id))) {
				return list;
			}
			list = articleDAO.getList(status, app_id, offset, limit);
			if (list == null || list.size() < 1) {
				return list;
			}
			for ( int i=list.size()-1;i>=0;i--) {
				Article article=list.get(i);
				if(article.getApp_id()!=app_id)
				{
					list.remove(i);
					continue;
				}
				add2Cache(cacheKey, article, offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get getFeedCommentList error status="
					+ status + ", offset=" + offset + ", limit=" + limit, e);
		}
		return list;
	}

	public int getCount(int status, int app_id) throws SQLException {
		Integer count = 0;
		try {
			count = RedisPoolFactory.get(countCacheKey(status, app_id),
					Integer.class);
			if (count != null && count > 0) {
				return count == null ? 0 : count;
			}
			count = articleDAO.getCount(status,app_id);
			if (count == null || count < 1) {
				return count == null ? 0 : count;
			}
			RedisPoolFactory.set(countCacheKey(status, app_id), count,
					60 * 60 * 24 * 7);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get getFeedCommentList error status="
					+ status, e);
		}
		return count == null ? 0 : count;
	}

	/**
	 * ==========================================<BR>
	 * 功能：未加缓存 cms 使用 <BR>
	 * 时间：2013-2-21 下午5:27:53 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public int getCount(int status) throws SQLException {
		Integer count = 0;
		try {
			count = articleDAO.getCount(status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get getFeedCommentList error status="
					+ status, e);
		}
		return count == null ? 0 : count;
	}

	public boolean deleteById(int id) throws SQLException {
		int result = 0;
		try {
			Article article = this.getArticleByID(id);
			if (article == null) {
				return false;
			}
			result = articleDAO.deleteById(id);
			delCacheById(article, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" del comment error id=" + id, e);
		}
		return result > 0;
	}

	public int updateStatus(int id, int status) throws SQLException {
		Integer count = 0;
		try {
			Article article = this.getArticleByID(id);
			if (article == null) {
				return -1;
			}
			count = articleDAO.updateStatus(id, status);
			delCacheById(article, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" get getFeedCommentList error status="
					+ status, e);
		}
		return count == null ? 0 : count;
	}

	public int update(int id, int app_id, String articleName,
			String articleBody, int categoryId, int status, Date createTime)
			throws SQLException {
		int result = -1;
		try {
			Article article = this.getArticleByID(id);
			if (article == null) {
				return result;
			}
			result = articleDAO.update(id, app_id, articleName, articleBody,
					categoryId, status, createTime);
			delCacheById(article, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(" insert FeedComment error articleName="
					+ articleName + ", articleBody=" + articleBody, e);
		}
		return result;
	}

	private void delCacheById(Article article, boolean delOperation) {
		if (article == null) {
			return;
		}
		RedisPoolFactory.delete(singleCacheKey((int) article.getId()));
		if (!delOperation) {
			return;
		}

		try {

			RedisPoolFactory.delete(listCacheKey(article.getStatus(),
					article.getApp_id()));
			RedisPoolFactory.delete(countCacheKey(article.getStatus(),
					article.getApp_id()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<Article> getListFromCache(String cacheKey, int offset,
			int limit) {
		List<Integer> idList = RedisPoolFactory.getList(cacheKey,
				Integer.class, offset, offset + limit - 1, false);
		if (idList == null || idList.size() < 1) {
			return null;
		}
		List<Article> articleList = new ArrayList<Article>(limit);
		for (Integer id : idList) {
			try {
				Article article = this.getArticleByID(id);
				if (article == null) {
					RedisPoolFactory.delete(singleCacheKey(id));
					return null;

				}
				articleList.add(article);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return articleList;
	}

	private void add2Cache(String cacheKey, Article article, int offset) {
		RedisPoolFactory.set(singleCacheKey(article.getId()), article,
				60 * 60 * 24);
		RedisPoolFactory.add2List(cacheKey, article.getId(), article
				.getCreateTime().getTime(), offset);
	}

	private String singleCacheKey(int id) {
		return "article_" + id;
	}

	private String listCacheKey(int status, int app_id) {
		return "article_list_" + status + "_appId_" + app_id;
	}

	private String countCacheKey(int status, int app_id) {
		return "article_list_count_" + status + "_appId_" + app_id;
	}

	public static void main(String[] args) {
		// RoseAppContext context = new RoseAppContext();
		// ArticleHome home = context.getBean(ArticleHome.class);
		try {
			int id = 0;
			while (id < 20) {

				RedisPoolFactory.delete("article_" + id);
				RedisPoolFactory.delete("article_list_" + 0);
				RedisPoolFactory.delete("article_list_count_" + 0);

				RedisPoolFactory.delete("comment_list_" + id);
				RedisPoolFactory.delete("comment_count_" + id);
				id++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
