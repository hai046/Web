package com.taobaoke.cms.crawlers;

import java.sql.SQLException;

import org.apache.commons.lang.math.NumberUtils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobaoke.cms.home.TItemDescHome;
import com.taobaoke.cms.home.TItemDetailHome;
import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.home.TItemImgHome;
import com.taobaoke.cms.model.TItemAllDetail;
import com.taobaoke.cms.model.TItemDetail;
import com.taobaoke.cms.model.TItemImg;
import com.taobaoke.cms.utils.TaobaokeConstants;

public class ItemDetailCrawler {
    static TaobaoClient client = new DefaultTaobaoClient(TaobaokeConstants.url, TaobaokeConstants.appkey, TaobaokeConstants.appSecret);

    private ItemDetailCrawler() {
    }

    public static void saveTaobaokeItem(long numIid) throws SQLException {

        ItemGetRequest req = new ItemGetRequest();
        req.setFields("detail_url,num_iid,title,nick,type,desc,cid,pic_url,num,valid_thru,list_time,dlist_time,stuff_status,location,"
                + "price,post_fee,express_fee,ems_fee,has_discount,freight_payer,has_invoice,has_warranty,has_showcase,modified,increment,"
                + "approve_status,postage_id,product_id,item_img,outer_id,is_virtual,is_taobao,is_ex,is_timing,is_3D,one_station,second_kill,auto_fill,wap_desc");
        req.setNumIid(numIid);
        TItemAllDetail mItemAllDetail = null;
        try {
            ItemGetResponse response = client.execute(req);
            if (response == null || response.getItem() == null) {
                TItemHome.getInstance().setUnuseableByNumIid(numIid);
            }
            System.out.println(response.getItem().getWapDesc());
            Item item = response.getItem();
            // TaobaokeItemDetail dfa;
            // dfa.getItem()

            mItemAllDetail = new TItemAllDetail();
            TItemDetail tItemDetail = changeItem2TItemDetail(item);
            mItemAllDetail.setmItemDetail(tItemDetail);
            TItemDetailHome.getInstance().insert(tItemDetail);
            TItemDescHome.getInstance().insert(tItemDetail.getNumIid(), item.getDesc());
            mItemAllDetail.setDesc(item.getDesc());
            for (ItemImg itemImg : item.getItemImgs()) {
                TItemImg tItemImg = changeItemImg2TItemImg(itemImg, tItemDetail.getNumIid());
                mItemAllDetail.addImg(tItemImg);
                TItemImgHome.getInstance().insert(tItemImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return;
    }

    private static TItemImg changeItemImg2TItemImg(ItemImg itemImg, long numIid) {
        TItemImg tItemImg = new TItemImg();
        tItemImg.setNumIid(numIid);
        tItemImg.setPicUrl(itemImg.getUrl());
        tItemImg.setPosition(itemImg.getPosition().intValue());

        return tItemImg;
    }

    private static TItemDetail changeItem2TItemDetail(Item item) {
        TItemDetail tItem = new TItemDetail();
        tItem.setDetailUrl(item.getDetailUrl());
        tItem.setNumIid(item.getNumIid());
        tItem.setTitle(item.getTitle());
        tItem.setNick(item.getNick());
        tItem.setType(item.getType());
        tItem.setcId(item.getCid());
        tItem.setPicUrl(item.getPicUrl());
        tItem.setNum(item.getNum());
        tItem.setValidThru(item.getValidThru().intValue());
        tItem.setListTime(item.getListTime());
        tItem.setDelistTime(item.getDelistTime());
        tItem.setStuffStatus(item.getStuffStatus());
        tItem.setLocation(item.getLocation().getState() + " " + item.getLocation().getCity());
        tItem.setPrice(NumberUtils.toFloat(item.getPrice()));
        tItem.setPostFee(NumberUtils.toFloat(item.getPostFee()));
        tItem.setExpressFee(NumberUtils.toFloat(item.getExpressFee()));
        tItem.setEmsFee(NumberUtils.toFloat(item.getEmsFee()));
        tItem.setHasDiscount(item.getHasDiscount() ? 1 : 0);
        tItem.setFreightPayer(item.getFreightPayer());
        tItem.setHasInvoice(item.getHasInvoice() ? 1 : 0);
        tItem.setHasWarranty(item.getHasWarranty() ? 1 : 0);
        tItem.setHasShowcase(item.getHasShowcase() ? 1 : 0);
        tItem.setModified(item.getModified());
        tItem.setApproveStatus(item.getApproveStatus());
        tItem.setProductId(item.getProductId() == null ? 0 : item.getProductId());
        tItem.setOuterId(item.getOuterId());
        tItem.setIsVirtual(item.getIsVirtual() ? 1 : 0);
        tItem.setIsTaobao(item.getIsTaobao() ? 1 : 0);
        tItem.setIsEx(item.getIsEx() ? 1 : 0);
        tItem.setIsTiming(item.getIsTiming() ? 1 : 0);
        // tItem.setAutoFil( item.getAutoFill() );
        return tItem;
    }

    public static void main(String[] args) {
        // RoseAppContext context = new RoseAppContext();
        try {
            // saveTaobaokeItem();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
