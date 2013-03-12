package com.taobaoke.cms.model;

import java.net.URLEncoder;
import java.util.Date;

public class TItem {
    private long id;
    private long numIid;
    private long cId;
    private long tcId;
    private String nick;
    private String title;
    private float price;
    private String location;
    private long sellerCreditScore;
    private String clickUrl;
    private String shopClickUrl;
    private String picUrl;
    private float commissionRate;
    private float commission;
    private float commissionNum;
    private long commissionVolume;
    private long volume;
    private int autoSend = 0;
    private int guarantee = 0;
    private int cashCoupon = 0;
    private int vipCard = 0;
    private int overseasItem = 0;
    private int sevendaysReturn=0;
    private int realDiscribe = 0;
    private int onemonthRepair = 0;
    private int cashOndelivery = 0;
    private int mallItem = 0;
    private Date createTime;
    private Date updateTime;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getNumIid() {
        return numIid;
    }
    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }
    public long getcId() {
        return cId;
    }
    public void setcId(long cId) {
        this.cId = cId;
    }
    public long getTcId() {
        return tcId;
    }
    public void setTcId(long tcId) {
        this.tcId = tcId;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public long getSellerCreditScore() {
        return sellerCreditScore;
    }
    public void setSellerCreditScore(long sellerCreditScore) {
        this.sellerCreditScore = sellerCreditScore;
    }
    public String getClickUrl() {
        return clickUrl;
    }
    public String getEncodedClickUrl() {
        try {
            return URLEncoder.encode(clickUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
    public String getShopClickUrl() {
        return shopClickUrl;
    }
    public void setShopClickUrl(String shopClickUrl) {
        this.shopClickUrl = shopClickUrl;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public float getCommissionRate() {
        return commissionRate;
    }
    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }
    public float getCommission() {
        return commission;
    }
    public void setCommission(float commission) {
        this.commission = commission;
    }
    public float getCommissionNum() {
        return commissionNum;
    }
    public void setCommissionNum(float commissionNum) {
        this.commissionNum = commissionNum;
    }
    public long getCommissionVolume() {
        return commissionVolume;
    }
    public void setCommissionVolume(long commissionVolume) {
        this.commissionVolume = commissionVolume;
    }
    public long getVolume() {
        return volume;
    }
    public void setVolume(long volume) {
        this.volume = volume;
    }
    public int getAutoSend() {
        return autoSend;
    }
    public void setAutoSend(int autoSend) {
        this.autoSend = autoSend;
    }
    public int getGuarantee() {
        return guarantee;
    }
    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }
    public int getCashCoupon() {
        return cashCoupon;
    }
    public void setCashCoupon(int cashCoupon) {
        this.cashCoupon = cashCoupon;
    }
    public int getVipCard() {
        return vipCard;
    }
    public void setVipCard(int vipCard) {
        this.vipCard = vipCard;
    }
    public int getOverseasItem() {
        return overseasItem;
    }
    public void setOverseasItem(int overseasItem) {
        this.overseasItem = overseasItem;
    }
    public int getRealDiscribe() {
        return realDiscribe;
    }
    public void setRealDiscribe(int realDiscribe) {
        this.realDiscribe = realDiscribe;
    }
    public int getOnemonthRepair() {
        return onemonthRepair;
    }
    public void setOnemonthRepair(int onemonthRepair) {
        this.onemonthRepair = onemonthRepair;
    }
    public int getCashOndelivery() {
        return cashOndelivery;
    }
    public void setCashOndelivery(int cashOndelivery) {
        this.cashOndelivery = cashOndelivery;
    }
    public int getMallItem() {
        return mallItem;
    }
    public void setMallItem(int mallItem) {
        this.mallItem = mallItem;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public int getSevendaysReturn() {
        return sevendaysReturn;
    }
    public void setSevendaysReturn(int sevendaysReturn) {
        this.sevendaysReturn = sevendaysReturn;
    }
    
    
    
}
