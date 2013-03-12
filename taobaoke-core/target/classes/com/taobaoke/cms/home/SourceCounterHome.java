package com.taobaoke.cms.home;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.SourceCounterDAO;
import com.taobaoke.cms.model.SourceCounter;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
public class SourceCounterHome {

	public enum Type {
		/**
		 * 查看了多少次帖子
		 */
		ARTICLE(1),
		/**
		 * 添加了多少次搜藏
		 */
		STORE(2),
		/**
		 * 购买闪屏的numiid
		 */
		BUY_NUMIID(3), OTHER(0);
		Type(int value) {
			this.value = value;
		}

		public int getTypeValue() {
			return this.value;
		}

		private int value;
		
		public static Type getTypeByValue(int value) {
	        for (Type type : Type.values()) {
	            if (type.value == value) {
	                return type;
	            }
	        }
	        return null;
	    }

	}

	// public static int TYPE_ARTICLE=1;
	// public static int
	@Autowired
	private SourceCounterDAO mSourceCounterDAO;

	public SourceCounter getBySource_id(int source_id,Type type) throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {
			return mSourceCounterDAO.getBySource_id(source_id,type.getTypeValue());
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}
	public SourceCounter getBy_id(int id) throws Exception {
		try {
			return mSourceCounterDAO.getBy_id(id);
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public int insertByNow(int source_id, int count, Type type)
			throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {

			return mSourceCounterDAO.insert(source_id, count,
					type.getTypeValue());
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public int insert(int source_id, int count, Type type, Date update_time)
			throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {

			return mSourceCounterDAO.insert(source_id, count,
					type.getTypeValue(), update_time);
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public Integer getSourceCount(int source_id, Type type) throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		Integer count = 0;
		try {
		    String cacheKey = countCacheKey(source_id, type);
		    count = RedisPoolFactory.get(cacheKey, Integer.class);
            if (count != null && count > 0) {
                return count == null ? 0 : count;
            }
			count = mSourceCounterDAO.getSourceCount(source_id,
					type.getTypeValue());
			if (count == null || count < 1) {
                return count == null ? 0 : count;
            }
			
			RedisPoolFactory.set(cacheKey, count, 60 * 60 * 24 * 7);
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
		return count == null ? 0 : count;
	}

	public int deleteBySource_id(int Source_id, Type type) throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {
		    RedisPoolFactory.delete(countCacheKey(Source_id, type));
		    
			return mSourceCounterDAO.deleteBySource_id(Source_id,
					type.getTypeValue());
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public int deleteById(int id) throws Exception {
		try {
		    SourceCounter counter = this.getBy_id(id);
		    if( counter == null ){
		        return -1;
		    }
		    RedisPoolFactory.delete(countCacheKey(counter.getSource_id(), Type.getTypeByValue(counter.getType())) );
			return mSourceCounterDAO.deleteId(id);
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

    public boolean updateSourceCounterAsc(int Source_id, Type type)
			throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {

			SourceCounter mSourceCounter = mSourceCounterDAO
					.getBySource_id(Source_id,type.getTypeValue());
			if (mSourceCounter == null) {
				mSourceCounterDAO.insert(Source_id, 1, type.getTypeValue());
			} else {
			    RedisPoolFactory.inc(countCacheKey(Source_id, type) );
				mSourceCounterDAO.updateSourceCounter(Source_id,
						type.getTypeValue());
			}
			return true;
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public int updateSourceCounter(int Source_id, Type type) throws Exception {
		if (type == null) {
			throw new Exception("error:type = null ");
		}
		try {
		    RedisPoolFactory.inc(countCacheKey(Source_id, type) );
			return mSourceCounterDAO.updateSourceCounter(Source_id,
					type.getTypeValue());
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}

	public int update(int Source_id, int count) throws Exception {
		try {
		    //TODO::miss type
//		    RedisPoolFactory.del(countCacheKey(Source_id, type) );
			return mSourceCounterDAO.update(Source_id, count);
		} catch (Exception e) {
			throw new Exception("Exception "+e);
		}
	}
	
	private String countCacheKey(int sourceId, Type type) {
	    if( type == null ){
	        return "source_count_" + sourceId;
	    }
        return "source_count_" + type.getTypeValue() + "_" + sourceId;
    }

}
