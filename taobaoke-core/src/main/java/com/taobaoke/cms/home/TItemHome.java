package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.TItemDAO;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.redis.RedisPoolFactory;

@Component
public class TItemHome {

    @Autowired
    private TItemDAO tItemDAO;

    private static ApplicationContext context;

    private static TItemHome instance;

    public static int TYPE_MOBILE = 1;
    public static int TYPE_WEB = 2;
    public static int ALL = -1;

    public static int STATUS_OK = 0;
    public static int STATUS_ERR = 1;

    public static TItemHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (TItemHome) BeanFactoryUtils.beanOfType(context, TItemHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }

    // public List<TItem> getAll(int tcId, int offset, int limit) throws
    // SQLException {
    // try {
    // return tItemDAO.getAll(tcId, offset, limit);
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new SQLException("get all error!!! ", e);
    // }
    // }

    public TItem getByNumId(long numIid) throws SQLException {
        try {
            Integer id = RedisPoolFactory.get(numIidCacheKey(numIid), Integer.class);
            if (id != null && id != 0) {
                return getById(id);
            }
            TItem item = tItemDAO.getByNumId(numIid);
            if (item != null) {
                getById((int) item.getId());
                RedisPoolFactory.set(numIidCacheKey(numIid), item.getId(), 60 * 60 * 24 * 7);
            }
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get item error!!! ", e);
        }
    }

    public TItem getById(int id) throws SQLException {
        try {
            TItem item = RedisPoolFactory.get(singleCacheKey(id), TItem.class);
            if (item != null) {
                return item;
            }
            item = tItemDAO.getById(id);
             RedisPoolFactory.set(singleCacheKey(id), item, 60 * 60 * 24);
//            this.add2Cache(item);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get item error!!! ", e);
        }
    }

    public List<TItem> getAll(int tcId, String orderBy, boolean isAsc, int offset, int limit) throws SQLException {
        List<TItem> result = null;
        try {
            if (!hasCached(orderBy, isAsc)) {
                return tItemDAO.getAll(tcId, orderBy + (isAsc ? "" : " desc"), offset, limit);
            }

            String cacheKey = listCacheKey(tcId, orderBy, isAsc);
            result = getListFromCache(cacheKey, offset, limit, isAsc);

            if (result != null && result.size() == limit) {
                return result;
            }
            result = tItemDAO.getAll(tcId, orderBy + (isAsc ? "" : " desc"), offset, limit);
            if (result == null || result.size() < 1) {
                return result;
            }
            for (TItem tItem : result) {
                add2Cache(cacheKey, tItem, orderBy, isAsc, offset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
        return result;
    }

    public int getCount(int tcId) throws SQLException {
        Integer count = 0;
        try {
            count = RedisPoolFactory.get(countCacheKey(tcId), Integer.class);
            if (count != null && count > 0) {
                return count == null ? 0 : count;
            }
            count = tItemDAO.getCount(tcId);
            if (count == null || count < 1) {
                return count == null ? 0 : count;
            }
            RedisPoolFactory.set(countCacheKey(tcId), count, 60 * 60 * 24 * 7);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
        return count == null ? 0 : count;
    }

    public List<TItem> getAllByTCategoryParentId(int tcId, String orderBy, boolean isAsc, int offset, int limit) throws SQLException {
        List<TItem> result = null;
        try {
            if (!hasCached(orderBy, isAsc)) {
                return tItemDAO.getAllByTCategoryParentId(tcId, orderBy + (isAsc ? "" : " desc"), offset, limit);
            }
            String cacheKey = listCacheKey(tcId, orderBy, isAsc);
            result = getListFromCache(cacheKey, offset, limit, isAsc);
            if (result != null && result.size() == limit) {
                return result;
            }

            result = tItemDAO.getAllByTCategoryParentId(tcId, orderBy + (isAsc ? "" : " desc"), offset, limit);
            if (result == null || result.size() < 1) {
                return result;
            }
            for (TItem tItem : result) {
                add2Cache(cacheKey, tItem, orderBy, isAsc, offset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
        return result;
    }

    public int getCountByTCategoryParentId(int tcId) throws SQLException {
        Integer count = 0;
        try {
            count = RedisPoolFactory.get(countCacheKey(tcId), Integer.class);
            if (count != null && count > 0) {
                return count == null ? 0 : count;
            }
            count = tItemDAO.getCountByTCategoryParentId(tcId);
            if (count == null || count < 1) {
                return count == null ? 0 : count;
            }
            RedisPoolFactory.set(countCacheKey(tcId), count, 60 * 60 * 24 * 7);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
        return count == null ? 0 : count;
    }

    public int insert(TItem tItem) throws SQLException {
        int result = -1;
        try {
            if (tItem == null) {
                return -1;
            }
            // RedisPoolFactory.delete("category_item_" + tItem.getTcId());

            result = tItemDAO.insert(tItem);
            getByNumId(tItem.getNumIid());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int del(int id) throws SQLException {
        try {
            TItem item = this.getById(id);
            if (item == null) {
                return -1;
            }
            // RedisPoolFactory.delete( listCacheKey(item.getTcId()) );
            this.delCacheById(id, true);
            return tItemDAO.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int setUnuseable(int id) throws SQLException {
        try {
            TItem item = this.getById(id);
            if (item == null) {
                return -1;
            }
            // RedisPoolFactory.delete( listCacheKey(item.getTcId()) );
            this.delCacheById(id, true);
            return tItemDAO.updateStatus(id, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    public int setUnuseableByNumIid(long numIid) throws SQLException {
        try {
            TItem item = this.getByNumId(numIid);

            if (item == null) {
                return -1;
            }
            // RedisPoolFactory.delete( listCacheKey(item.getTcId()) );
            this.delCacheById((int) item.getId(), true);
            return tItemDAO.updateStatus(numIid, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("get all error!!! ", e);
        }
    }

    private boolean hasCached(String orderBy, boolean isAsc) {
        if (StringUtils.isEmpty(orderBy)) {
            return false;
        }
        if( ("volume".equals(orderBy) && isAsc == false) || 
                ("price".equals(orderBy)) || ("seller_credit_score".equals(orderBy) && isAsc == false) ) {
            return true;
        }
        return false;
    }
    
    private List<TItem> getListFromCache(String cacheKey,int offset, int limit, boolean isAsc){
        List<Integer> idList = RedisPoolFactory.getList(cacheKey, Integer.class, offset, offset + limit - 1, isAsc);
        if( idList == null || idList.size() < 1 || idList.size() != limit ){
            return null;
        }
        List<TItem> itemList = new ArrayList<TItem>( limit );
        for( Integer id : idList ){
            try {
                itemList.add( getById(id) );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    private void add2Cache(String cacheKey, TItem tItem, String orderBy, boolean isAsc, int offset) {
        //
        RedisPoolFactory.set(singleCacheKey((int) tItem.getId()), tItem, 60 * 60 * 24);

        if ("volume".equals(orderBy)) {
            RedisPoolFactory.add2List(cacheKey, tItem.getId(), tItem.getVolume(), offset);
        } else if ("price".equals(orderBy)) {
            RedisPoolFactory.add2List(cacheKey, tItem.getId(), tItem.getPrice(), offset);
        } else if ("seller_credit_score".equals(orderBy)) {
            RedisPoolFactory.add2List(cacheKey, tItem.getId(), tItem.getSellerCreditScore(), offset);
        }
    }

    private void delCacheById(int id, boolean delOperation) {
        RedisPoolFactory.delete(singleCacheKey((int) id));
        if( !delOperation ){
            return ;
        }
        
        TItem tItem;
        try {
            tItem = this.getById(id);
            if (tItem == null) {
                return;
            }
            RedisPoolFactory.delete(listCacheKey((int) tItem.getTcId(), "volume", false));
            RedisPoolFactory.delete(listCacheKey((int) tItem.getTcId(), "price", true));
            RedisPoolFactory.delete(listCacheKey((int) tItem.getTcId(), "price", false));
            RedisPoolFactory.delete(listCacheKey((int) tItem.getTcId(), "seller_credit_score", false));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String singleCacheKey(int id) {
        return "titem_" + id;
    }

    private String numIidCacheKey(long numIid) {
        return "titem_num_iid" + numIid;
    }

    private String listCacheKey(long tcId, String orderBy, boolean isAsc) {
        return "item_" + orderBy + isAsc + tcId;
    }

    private String countCacheKey(int tcId) {
        return "item_count_" + tcId;
    }

    public static void main(String[] args) {
        RoseAppContext context = new RoseAppContext();
        TItemHome home = context.getBean(TItemHome.class);
        try {
            System.out.println("sss+++" + home.getAll(3, "price", true, 0, 10).get(0).getId());
            // System.out.println("sss+++"
            // + home.setUnuseable(19862));
            System.out.println("sss+++");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sfsdljfkjslkdf");

    }
}
