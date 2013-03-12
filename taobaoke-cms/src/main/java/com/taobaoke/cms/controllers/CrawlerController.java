package com.taobaoke.cms.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.stereotype.Component;

import com.taobaoke.cms.crawlers.CrawlerFacade;

@Component
@Path("/crawler")
public class CrawlerController {
	CrawlerFacade crawlerFacade = new CrawlerFacade();

	@Get("/status")
	public String categoryList(Invocation inv) {
		inv.addModel("allStatus", crawlerFacade.getAllStatus());
		inv.addModel("isRun", crawlerFacade.isRun());
		return "crawler/status";
	}

	@Post("/run")
	public String run(Invocation inv, @Param("app_id") int app_id) {
		crawlerFacade.start(app_id);
		return "r:./status";
	}

	@Post("/runByTcId")
	public String runByCategoryId(Invocation inv, @Param("tcId") int tcId) {
		do {
			if (tcId < 0)
				break;
			crawlerFacade.startByTcId(tcId);
		} while (false);

		return "r:./status";
	}
}
