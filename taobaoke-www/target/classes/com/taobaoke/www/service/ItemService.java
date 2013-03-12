package com.taobaoke.www.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TaobaokeItemDetail;
import com.taobao.api.request.TaobaokeItemsDetailGetRequest;
import com.taobao.api.response.TaobaokeItemsDetailGetResponse;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TItemPcHome;
import com.taobaoke.cms.home.WebItemHome;
import com.taobaoke.cms.model.TItem;
import com.taobaoke.cms.utils.TaobaokeConstants;

public class ItemService {

    static TaobaoClient client = new DefaultTaobaoClient(TaobaokeConstants.url, TaobaokeConstants.appkey, TaobaokeConstants.appSecret);

    public ItemService() {
    }

    public String getClickUrl(long numIid) {
        String clickUrl = getClickUrlFromDB(numIid);
        if (!StringUtils.isEmpty(clickUrl)) {
            return clickUrl;
        }

        TaobaokeItemsDetailGetRequest req = new TaobaokeItemsDetailGetRequest();
        req.setFields("click_url,shop_click_url,seller_credit_score,num_iid,title,nick");
        req.setNumIids(numIid + "");
        req.setNick("iterry1985");
        req.setOuterCode("iterry1985");
        req.setIsMobile(false);
        try {
            TaobaokeItemsDetailGetResponse response = client.execute(req);
            List<TaobaokeItemDetail> list = response.getTaobaokeItemDetails();
            if (list == null || list.size() < 1) {
                return null;
            }
            clickUrl = list.get(0).getClickUrl();
            if (!StringUtils.isEmpty(clickUrl)) {
                TItemPcHome.getInstance().insert(numIid, clickUrl);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return clickUrl;
    }

    public String getClickUrlFromDB(long numIid) {
        String clickUrl = "";
        try {
            clickUrl = TItemPcHome.getInstance().getClickUrl(numIid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clickUrl;
    }
    
    public List<TItem> getWebItemList(long connectId, String deviceNo){
        List<Long> itemList = null;
//        String deviceNo = "";
        try {
//            deviceNo = DeviceConnectHome.getInstance().getDeviceNoByConnectId(connectId);
            itemList = WebItemHome.getInstance().getItemList(deviceNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if( itemList == null || itemList.size() < 1 ){
            return null;
        }
//        JSONArray result = new JSONArray();
        List<TItem> tItemList = new ArrayList<TItem>(itemList.size());
        for( Long numIid : itemList ){
            try {
                TItem tItem = TItemHome.getInstance().getByNumId(numIid);
                if(tItem == null){
                    WebItemHome.getInstance().delete(deviceNo, numIid, WebItemHome.TYPE_ITEM);
                    continue;
                }
                String pcClickUrl = StringUtils.defaultIfEmpty(getClickUrl(numIid), tItem.getClickUrl());
                tItem.setClickUrl(pcClickUrl);
                tItemList.add(tItem);
//                JSONObject json = new JSONObject();
                
//                json.put("name", tItem.getTitle());
//                json.put("cash_on_delivery", tItem.getCashOndelivery());
//                json.put("price", tItem.getPrice());
//                json.put("pic_url", tItem.getPicUrl());
//                json.put("volume", tItem.getVolume());
//                json.put("pc_click_url", pcClickUrl);
//                
//                result.add(json);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tItemList;
    }

    // private static TItemImg changeItemImg2TItemImg(ItemImg itemImg, long
    // numIid) {
    // TItemImg tItemImg = new TItemImg();
    // tItemImg.setNumIid(numIid);
    // tItemImg.setPicUrl(itemImg.getUrl());
    // tItemImg.setPosition(itemImg.getPosition().intValue());
    //
    // return tItemImg;
    // }
    //
    // private static TItemDetail changeItem2TItemDetail(Item item) {
    // TItemDetail tItem = new TItemDetail();
    // tItem.setDetailUrl(item.getDetailUrl());
    // tItem.setNumIid(item.getNumIid());
    // tItem.setTitle(item.getTitle());
    // tItem.setNick(item.getNick());
    // tItem.setType(item.getType());
    // tItem.setcId(item.getCid());
    // tItem.setPicUrl(item.getPicUrl());
    // tItem.setNum(item.getNum());
    // tItem.setValidThru(item.getValidThru().intValue());
    // tItem.setListTime(item.getListTime());
    // tItem.setDelistTime(item.getDelistTime());
    // tItem.setStuffStatus(item.getStuffStatus());
    // tItem.setLocation(item.getLocation().getState() + " "
    // + item.getLocation().getCity());
    // tItem.setPrice(NumberUtils.toFloat(item.getPrice()));
    // tItem.setPostFee(NumberUtils.toFloat(item.getPostFee()));
    // tItem.setExpressFee(NumberUtils.toFloat(item.getExpressFee()));
    // tItem.setEmsFee(NumberUtils.toFloat(item.getEmsFee()));
    // tItem.setHasDiscount(item.getHasDiscount() ? 1 : 0);
    // tItem.setFreightPayer(item.getFreightPayer());
    // tItem.setHasInvoice(item.getHasInvoice() ? 1 : 0);
    // tItem.setHasWarranty(item.getHasWarranty() ? 1 : 0);
    // tItem.setHasShowcase(item.getHasShowcase() ? 1 : 0);
    // tItem.setModified(item.getModified());
    // tItem.setApproveStatus(item.getApproveStatus());
    // tItem.setProductId(item.getProductId() == null ? 0 : item
    // .getProductId());
    // tItem.setOuterId(item.getOuterId());
    // tItem.setIsVirtual(item.getIsVirtual() ? 1 : 0);
    // tItem.setIsTaobao(item.getIsTaobao() ? 1 : 0);
    // tItem.setIsEx(item.getIsEx() ? 1 : 0);
    // tItem.setIsTiming(item.getIsTiming() ? 1 : 0);
    // // tItem.setAutoFil( item.getAutoFill() );
    // return tItem;
    // }

    public static void main(String[] args) {
        // RoseAppContext context = new RoseAppContext();
        try {
            // saveTaobaokeItem();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
