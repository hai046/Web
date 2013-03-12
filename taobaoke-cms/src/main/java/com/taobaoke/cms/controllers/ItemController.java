package com.taobaoke.cms.controllers;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.home.TItemHome;
import com.taobaoke.cms.model.TItem;

@Component
@Path("/item")
public class ItemController {
    @Autowired
    TItemHome tItemHome;
    
    private static final int DEFAULT_SIZE = 20;
    
    @Get("/list")
    public String categoryList(Invocation inv, @Param("tcId") int tcId, @Param("currentPage") int page, @Param("backUrl") String url, @Param("orderItem") String orderItem
            , @Param("orderOption") boolean orderOption) {
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * DEFAULT_SIZE;
        
        try {
            if( StringUtils.isBlank(orderItem) ){
                orderItem = "volume";
            }
            
            List<TItem> itemList = tItemHome.getAll(tcId, orderItem, orderOption, offset, DEFAULT_SIZE);
            int count = tItemHome.getCount(tcId);
            
            
            long pageCount = count / (long) DEFAULT_SIZE;
            pageCount = pageCount * DEFAULT_SIZE == count ? pageCount : pageCount + 1;
            
            inv.addModel("itemList", itemList);
            inv.addModel("orderItem", orderItem);
            //显示当前排序的item
            inv.addModel(orderItem, "icon-arrow-"+(orderOption? "up" : "down"));
            inv.addModel("orderOption", orderOption);
            inv.addModel("count",  count);
            inv.addModel("pageCount", pageCount);
            inv.addModel("currentPage", page);
            inv.addModel("backUrl", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "item/list";
    }
}
