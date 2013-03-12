package com.taobaoke.cms.crawlers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.math.NumberUtils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TaobaokeItem;
import com.taobao.api.request.TaobaokeItemsGetRequest;
import com.taobao.api.response.TaobaokeItemsGetResponse;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.model.TCategory;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.model.TItemRate;

public class SingleCrawler extends Thread {

    static String url = "http://gw.api.taobao.com/router/rest";
    // 正式环境需要设置为:http://gw.api.taobao.com/router/rest
    protected static String appkey = "21267618";
    protected static String appSecret = "b41ba667ae9fe43f44355c8d816a0af9";

    public static final long PAGE_COUNT = 40;
    int cacheIndex = 0;
    CrawlerFacade facade = null;
    TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
    ItemCommentCrawler itemCommentCrawler = new ItemCommentCrawler();

    // TItemHome tItemHome

    public SingleCrawler(int cacheIndex, CrawlerFacade facade) {
        this.cacheIndex = cacheIndex;
        this.facade = facade;
    }

    @Override
    public void run() {
        // while(true){
        // System.out.println( " I am  " + cacheIndex);
        CopyOnWriteArraySet<TCategory> list = facade.getDataByIndex(cacheIndex);

        for (TCategory tCategory : list) {
            list.remove(tCategory);
            saveTaobaokeItem(tCategory, "1diamond", "5diamond", "false");
            saveTaobaokeItem(tCategory, "1diamond", "5diamond", "true");
            saveTaobaokeItem(tCategory, "1crown", "5goldencrown", "true");
            saveTaobaokeItem(tCategory, "1crown", "5goldencrown", "false");
            facade.setOk(tCategory);
        }
        facade.callMeWhenFinished();
        // }
    }

    private void saveTaobaokeItem(TCategory tCategory, String startCredit, String endCredit, String cashOndelivery) {

        TaobaokeItemsGetRequest req = new TaobaokeItemsGetRequest();

        String[] cids = tCategory.getCid().split(",");
        for (String cidStr : cids) {
            req.setFields("desc,num_iid,nick,title,price,item_location,seller_id,seller_credit_score,click_url,shop_click_url,pic_url,commission,commission_rate,commission_num,commission_volume,shop_click_url,item_location,volume,shop_type");
            req.setNick("iterry1985");
            long cid = NumberUtils.toLong(cidStr.trim());
            if (cid == 0) {
                continue;
            }
            req.setCid(cid);
            req.setPageSize(PAGE_COUNT);
            req.setStartPrice("0.0");
            req.setEndPrice("9999");
            req.setStartCredit(startCredit);
            req.setEndCredit(endCredit);
            req.setCashOndelivery(cashOndelivery);
            req.setSort("commissionNum_desc");
            long pageNo = 0;
            long totalCount = Long.MAX_VALUE;
            int cashOndeliveryInt = "true".equalsIgnoreCase(cashOndelivery) ? 1 : 0;
            while ((++pageNo) * PAGE_COUNT < totalCount && pageNo <= 10) {
                req.setPageNo(pageNo);
                req.setOuterCode("iterry1985");
                req.setIsMobile(true);
                TaobaokeItemsGetResponse response;
                try {
                    response = client.execute(req);
                    totalCount = response.getTotalResults();
                    if (totalCount < 1) {
                        break;
                    }
                    List<TaobaokeItem> list = response.getTaobaokeItems();
                    for (TaobaokeItem ti : list) {
                        TItem tItem = changeTaobaokeItem2TItem(ti);
                        tItem.setcId(cid);
                        tItem.setTcId(tCategory.getId());
                        tItem.setCashOndelivery(cashOndeliveryInt);
                        
                        ItemDetailCrawler.saveTaobaokeItem(ti.getNumIid());
                        updateCommentsFromCrawler(ti.getNumIid());
                        try {
                            System.out.println("insert...return " + tItem.getNumIid() + " || " + TItemHome.getInstance().insert(tItem));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        // testRefound(list);
    }

    private TItem changeTaobaokeItem2TItem(TaobaokeItem ti) {
        TItem tItem = new TItem();
        tItem.setNumIid(ti.getNumIid());
        tItem.setNick(ti.getNick());
        tItem.setTitle(ti.getTitle());
        tItem.setPrice(NumberUtils.toFloat(ti.getPrice()));
        tItem.setLocation(ti.getItemLocation());
        tItem.setSellerCreditScore(ti.getSellerCreditScore());
        tItem.setClickUrl(ti.getClickUrl());
        tItem.setShopClickUrl(ti.getShopClickUrl());
        tItem.setPicUrl(ti.getPicUrl());
        tItem.setCommissionRate(NumberUtils.toFloat(ti.getCommissionRate()));
        tItem.setCommission(NumberUtils.toFloat(ti.getCommission()));
        tItem.setCommissionNum(NumberUtils.toFloat(ti.getCommissionNum()));
        tItem.setCommissionVolume(NumberUtils.toLong(ti.getCommissionVolume()));
        tItem.setVolume(ti.getVolume());
        return tItem;
    }

    private void updateCommentsFromCrawler(final long numIid) throws Exception {
        try {

            List<TItemRate> list = null;
            int pageNum = 1;
            while (true) {
                try {
                    list = itemCommentCrawler.start(numIid, pageNum++);
                    if (list == null || list.size() < 100) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return;
    }

    public static void main(String[] args) {
        String[] cids = "50012832, 50024156".split(",");
        for (String temp : cids) {

            System.out.println("aa==" + NumberUtils.toLong(temp));
        }

    }
}
