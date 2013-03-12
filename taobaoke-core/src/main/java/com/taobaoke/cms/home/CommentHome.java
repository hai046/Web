package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.CommentDAO;
import com.taobaoke.cms.dao.CommentIDGenerateDAO;
import com.taobaoke.cms.model.Comment;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
public class CommentHome {
	public static int TYPE_ARTICLE = 1;
	public static int TYPE_ALL = -1;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private CommentIDGenerateDAO commentIDGenerateDAO;
	
	private static ApplicationContext context;
	
	private static CommentHome instance;
	
	public static CommentHome getInstance(){
		if( instance != null ){
			return instance;
		}
		if( context == null ){
			throw new RuntimeException("Initial Error");
		}
		instance = (CommentHome) BeanFactoryUtils.beanOfType(context, CommentHome.class);
		return instance;
	}
	
	@Autowired
	public void setContext(ApplicationContext rContext){
		context = rContext;
	}
	
	public int insert(String commentorName, String replieeName, int repliedCommentId, int parentId, int sourceId, String msg, int type, Date createTime) throws SQLException{
		int commentId = -1;
		try{
			commentId = commentIDGenerateDAO.generateId();
			if( commentId < 1 ){
				return commentId;
			}
			if(commentorName==null||commentorName.isEmpty())
			{
				commentorName="匿名用户";
			}
			commentDAO.insert(commentId, commentorName, replieeName, repliedCommentId, parentId, sourceId, type, msg, createTime);
			delCacheById(getCommentByID(commentId), true);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " insert FeedComment error articleId=" + sourceId + ", commentorName=" + commentorName + ",msg=" + msg, e );
		}
		return commentId;
	}
	
	
	
	public Comment getCommentByID(int commentID) throws SQLException{
		Comment comment = null;
		try{
		    comment = RedisPoolFactory.get(singleCacheKey(commentID), Comment.class);
            if (comment != null) {
                return comment;
            }
			comment = commentDAO.getById(commentID);
			RedisPoolFactory.set(singleCacheKey(commentID), comment, 60 * 60 * 24);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " get Comment error commentID=" + commentID, e );
		}
		return comment;
	}
	
	public List<Comment> getCommentList(long articleID, int offset, int limit) throws SQLException{
	    List<Comment> result = null;
        try {
            String cachedKey = listCacheKey(articleID, false);
            result = getListFromCache(cachedKey, offset, limit, false);
            if( result != null && (result.size() == limit || result.size() == this.getCommentCount(articleID)) ){
                return result;
            }
            result = commentDAO.getCommentListBySourceId(articleID, offset, limit);
            if( result == null || result.size() < 1 ){
                return result;
            }
            
            for( Comment comment : result ){
//                RedisPoolFactory.add2List("comment_list_asc_" + articleID, comment, comment.getCreateTime().getTime());
                this.add2Cache(cachedKey, comment, offset);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " get getFeedCommentList error feedId=" + articleID + ", offset=" + offset + ", limit=" + limit, e );
		}
		return result;
	}
	
	public List<Comment> getCommentListAsc(long articleID, int offset, int limit) throws SQLException{
	    List<Comment> result = null;
        try {
            String cachedKey = listCacheKey(articleID, true);
            result = getListFromCache(cachedKey, offset, limit, true);
            if( result != null && (result.size() == limit || result.size() == getCommentCount(articleID)) ){
                return result;
            }
            result = commentDAO.getCommentListBySourceIdAsc(articleID, offset, limit);
            if( result == null || result.size() < 1 ){
                return result;
            }
            
            for( Comment comment : result ){
//                RedisPoolFactory.add2List("comment_list_desc_" + articleID, comment, comment.getCreateTime().getTime());
                this.add2Cache(cachedKey, comment, offset);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " get getFeedCommentList error feedId=" + articleID + ", offset=" + offset + ", limit=" + limit, e );
		}
		return result;
	}
	
	public int getCommentCount(long sourceID) throws SQLException{
	    Integer count = 0;
        try {
            count = RedisPoolFactory.get(countCacheKey(sourceID), Integer.class);
            if (count != null && count > 0) {
                return count == null ? 0 : count;
            }
            count = commentDAO.getCommentCount(sourceID, TYPE_ALL);
            if (count == null || count < 1) {
                return count == null ? 0 : count;
            }
            RedisPoolFactory.set(countCacheKey(sourceID), count, 60 * 60 * 24 * 7);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " get getFeedCommentList error feedId=" + sourceID, e );
		}
		return count == null ? 0 : count;
	}
	
	
	public boolean delCommentByID(long commentId) throws SQLException{
		int result = 0;
		try{
		    Comment comment = getCommentByID((int)commentId);
		    if( comment == null ){
		        return false;
		    }
		    
			result = commentDAO.deleteById(commentId);
			delCacheById(comment, true);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " del comment error commentId=" + commentId, e );
		}
		return result > 0;
	}
	
	public boolean delCommentBySource(long sourceId) throws SQLException{
		int result = 0;
		try{
		    List<Comment> commentList = getCommentList(sourceId, 0, Integer.MAX_VALUE);
		    
		    if( commentList == null || commentList.size() < 1 ){
		        return false;
		    }
		    
			result = commentDAO.deleteBySource(sourceId);
			for( Comment comment : commentList ){
                delCacheById(comment, true);
            }
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " del comment error sourceId=" + sourceId, e );
		}
		return result > 0;
	}
	
	public boolean deleteByUserAndSource(long sourceId, int userId) throws SQLException{
		int result = 0;
		try{
		    //TODO::need 2 add del cache stage
			result = commentDAO.deleteByUserAndSource(sourceId, userId);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " del comment error commentId=" + sourceId, e );
		}
		return result > 0;
	}
	
	public boolean delCommentByUser(long commentId, int userId) throws SQLException{
		int result = 0;
		try{
		  //TODO::need 2 add del cache stage
			result = commentDAO.deleteByUser(commentId, userId);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " del comment error commentId=" + commentId, e );
		}
		return result > 0;
	}
	
	public List<Long> getUserCommentedSourceIds(int userId, int limit, int offset) throws SQLException{
		List<Long> sourceIds = new ArrayList<Long>();
		try{
			sourceIds = commentDAO.getUserCommentedSourceIds(userId, TYPE_ALL, offset, limit);
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException( " del comment error userId=" + userId, e );
		}
		return sourceIds;
	}
	
	
	private List<Comment> getListFromCache(String cacheKey,int offset, int limit, boolean isAsc){
        List<Integer> idList = RedisPoolFactory.getList(cacheKey, Integer.class, offset, offset + limit - 1, isAsc);
        if( idList == null || idList.size() < 1 ){
            return null;
        }
        List<Comment> itemList = new ArrayList<Comment>( limit );
        for( Integer id : idList ){
            try {
                Comment comment =  getCommentByID(id);
                if( comment == null ){
                    RedisPoolFactory.delete(singleCacheKey(id));
                    return null;
                    
                }
                itemList.add( comment );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    private void add2Cache(String cacheKey, Comment comment, int offset) {
        //
        RedisPoolFactory.set(singleCacheKey(comment.getId()), comment, 60 * 60 * 24);

        RedisPoolFactory.add2List(cacheKey, comment.getId(), comment.getCreateTime().getTime(), offset);
    }

    private void delCacheById(Comment comment, boolean delOperation) {
        if (comment == null) {
            return;
        }
        RedisPoolFactory.delete(singleCacheKey((int) comment.getId()));
        if( !delOperation ){
            return ;
        }
        
//        Comment comment;
        try {
//            comment = this.getCommentByID(id);
            
            RedisPoolFactory.delete(listCacheKey(comment.getId(), false));
            RedisPoolFactory.delete(listCacheKey(comment.getId(), true));
            RedisPoolFactory.delete(countCacheKey(comment.getSourceId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String singleCacheKey(int id) {
        return "comment_" + id;
    }


    private String listCacheKey(long articleID, boolean isAsc) {
        return "comment_list_" + isAsc + articleID;
    }

    private String countCacheKey(long articleID) {
        return "comment_count_" + articleID;
    }
	
	
	public static void main(String []args){
		RoseAppContext context = new RoseAppContext();
		CommentHome home = context.getBean(CommentHome.class);
		try {
			home.delCommentBySource(11);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
