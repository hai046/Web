package com.taobaoke.cms.redis;

public enum PoolSchema {
	FEED_CONTENT(0),

	MINI_FEED(1),

	NEARBY_FEED(2),

	LOCAL(3),
	
	SHORT_TIME_TEMP(4),
	
	FEED_CONTENT_USER(5),
	
	NOTIFY(6),
	
	;

	public int db;

	PoolSchema(int db) {
	    this.db = db;
	}

}
