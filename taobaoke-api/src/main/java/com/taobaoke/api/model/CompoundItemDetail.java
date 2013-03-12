package com.taobaoke.api.model;

import org.springframework.stereotype.Component;

import com.taobaoke.cms.model.TItemDetail;
import com.taobaoke.cms.model.TItemImg;
@Component
public class CompoundItemDetail {
    private TItemDetail tItemDetail;
    private String tItemDesc;
    private TItemImg tItemImg;
    
    
    public TItemDetail gettItemDetail() {
        return tItemDetail;
    }
    public void settItemDetail(TItemDetail tItemDetail) {
        this.tItemDetail = tItemDetail;
    }
    public String gettItemDesc() {
        return tItemDesc;
    }
    public void settItemDesc(String tItemDesc) {
        this.tItemDesc = tItemDesc;
    }
    public TItemImg gettItemImg() {
        return tItemImg;
    }
    public void settItemImg(TItemImg tItemImg) {
        this.tItemImg = tItemImg;
    }

    
}
