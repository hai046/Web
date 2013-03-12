package com.taobaoke.api.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobaoke.api.crawler.ItemCommentCrawler;
import com.taobaoke.cms.home.TItemRateHome;
import com.taobaoke.cms.model.TItemRate;

@Component
public class CommentService {
	@Autowired
	TItemRateHome tItemRateHome;

	ItemCommentCrawler itemCommentCrawler = new ItemCommentCrawler();

	private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors());
	Log log = LogFactory.getLog(getClass());

	

	public List<TItemRate> getComment(long numIid, int offset, int limit)
			throws Exception {
		List<TItemRate> list = null;
		try {
			list = tItemRateHome.getListByNumiid(numIid, offset, limit);
			if ((list == null || list.size() < 1) && offset == 0) {
				list = getCommentsFromCrawler(numIid, offset, limit);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return list;
	}

	private List<TItemRate> getCommentsFromCrawler(final long numIid,
			int offset, int limit) {
		List<TItemRate> list = null;
		try {
			list = itemCommentCrawler.start(numIid, 1);
			if (list != null && list.size() > limit) {
				list = list.subList(0, limit);
			}
			Runnable thread = new Runnable() {

				@Override
				public void run() {
					List<TItemRate> list = null;
					int pageNum = 2;
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
				}

			};

			threadPool.execute(thread);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
}
