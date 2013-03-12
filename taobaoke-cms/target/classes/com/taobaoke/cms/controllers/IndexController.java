package com.taobaoke.cms.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.stereotype.Component;

@Component
@Path("/")
public class IndexController {
    
    @Get("")
    public String index(Invocation inv, @Param("tcId") int tcId, @Param("currentPage") int page, @Param("backUrl") String url) {
        
        return "r:/category/list";
    }
}
