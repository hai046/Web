package com.taobaoke.cms.crawlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import net.paoding.rose.scanning.context.RoseAppContext;

import com.taobaoke.cms.home.TCategoryHome;
import com.taobaoke.cms.model.TCategory;

public class CrawlerFacade {
	private Map<Integer, CopyOnWriteArraySet<TCategory>> cache = new HashMap<Integer, CopyOnWriteArraySet<TCategory>>();
	private int THREAD_SIZE = 10;
	private ExecutorService threadPool = Executors
			.newFixedThreadPool(THREAD_SIZE);

	private ConcurrentHashMap<TCategory, Boolean> runStatus = new ConcurrentHashMap<TCategory, Boolean>();

	private boolean isRun = false;

	private AtomicInteger runingThread = new AtomicInteger(0);

	public CrawlerFacade() {

	}

	public CopyOnWriteArraySet<TCategory> getDataByIndex(int cacheIndex) {
		return cache.get(cacheIndex);
	}

	public void setOk(TCategory tCategory) {
		runStatus.replace(tCategory, true);
	}

	public ConcurrentHashMap<TCategory, Boolean> getAllStatus() {
		return runStatus;
	}

	public void start(int app_id) {
		for (int i = 0; i < THREAD_SIZE; i++) {
			if (cache.get(i) == null)
				cache.put(i, new CopyOnWriteArraySet<TCategory>());
		}

		TCategoryHome home = TCategoryHome.getInstance();

		try {
			List<TCategory> list = home.getAllSubList(
					TCategoryHome.TYPE_MOBILE, TCategoryHome.STATUS_OK, 0,
					Integer.MAX_VALUE);
			int index = 0;
			for (TCategory tc : list) {
				if (app_id >= 0) {
					if (tc.getApp_id() != app_id)
						continue;
				}
				int cacheIndex = index++ % THREAD_SIZE;
				cache.get(cacheIndex).add(tc);
				runStatus.put(tc, false);
			}
			if (isRun) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		runingThread.set(THREAD_SIZE);
		isRun = true;
		for (int i = 0; i < THREAD_SIZE; i++) {
			threadPool.execute(new SingleCrawler(i, this));
		}
	}

	public void startByTcId(int tcId) {
		if (isRun)
			return;
		// for (int i = 0; i < THREAD_SIZE; i++) {
		// if (cache.get(i) == null)
		cache.clear();
		cache.put(0, new CopyOnWriteArraySet<TCategory>());
		// }
		TCategoryHome home = TCategoryHome.getInstance();
		try {
			TCategory tc = home.getById(tcId);
			cache.get(0).add(tc);
			runStatus.put(tc, false);
			if (isRun) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		runingThread.set(1);
		isRun = true;
		// for (int i = 0; i < THREAD_SIZE; i++) {
		threadPool.execute(new SingleCrawler(0, this));
		// }
	}

	public void callMeWhenFinished() {
		int remainder = runingThread.decrementAndGet();
		if (remainder == 0) {
			isRun = false;
		}
	}

	public boolean isRun() {
		return isRun;
	}

	public void crawlerBasicInfoFromCids() {

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RoseAppContext context = new RoseAppContext();
		CrawlerFacade facade = new CrawlerFacade();
		facade.start(-1);
	}

}
