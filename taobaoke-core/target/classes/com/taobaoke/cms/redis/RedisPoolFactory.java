package com.taobaoke.cms.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;

public class RedisPoolFactory {

    private static JedisPool pool;

    private static final int DEFAULT_TIMEOUT = 10000;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Configurations config = Configurations.getInstance();

        if (config.hasConfigFile()) {

            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setTestWhileIdle(true);
            jedisPoolConfig.setMaxIdle(NumberUtils.toInt(config.getConfig("max_idel"), 5000));
            jedisPoolConfig.setMaxWait(NumberUtils.toInt(config.getConfig("max_wait"), 10000));
            jedisPoolConfig.setMaxActive(NumberUtils.toInt(config.getConfig("max_active"), 50000));

            if (StringUtils.isBlank(config.getConfig("pass"))) {
                pool = new JedisPool(jedisPoolConfig, config.getConfig("host"), NumberUtils.toInt(config.getConfig("port"), 6379), NumberUtils.toInt(config.getConfig("timeout"),
                        DEFAULT_TIMEOUT));
            } else {
                pool = new JedisPool(jedisPoolConfig, config.getConfig("host"), NumberUtils.toInt(config.getConfig("port"), 6379), NumberUtils.toInt(config.getConfig("timeout"),
                        DEFAULT_TIMEOUT), config.getConfig("pass"));
            }
        }
        // pool.getResource();
    }

    public static <T> T get(String key, Class<T> returnClass) {
        if (pool == null) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String strVal = jedis.get(key);
            return JSON.parseObject(strVal, returnClass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return null;
    }

    public static <T> void set(String key, T value, int expiredSeconds) {
        if (pool == null) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String strVal = JSON.toJSONString(value);
            jedis.set(key, strVal);
            jedis.expire(key, expiredSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
    }

    public static <T> void delete(String key) {
        if (pool == null) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
    }

    public static <T> void inc(String key) {
        if (pool == null) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
    }

    public static <T> void add2List(String key, T value, double score, int offset) {
        if (pool == null) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if( jedis.zcard(key) < offset ){
                return ;
            }
            String strVal = JSON.toJSONString(value);
            jedis.zadd(key, score, strVal);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return;
    }

    public static <T> void add2List(String key, List<T> values, List<Double> scores) {
        if (pool == null || values == null || scores == null || values.size() != scores.size()) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();

            int index = 0;
            while (index < values.size()) {
                String strVal = JSON.toJSONString(values.get(index));
                jedis.zadd(key, scores.get(index), strVal);
                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return;
    }

    public static <T> List<T> getList(String key, Class<T> returnClass, int start, int end, boolean asc) {
        if (pool == null) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> list = null;
            if (asc) {
                list = jedis.zrange(key, start, end);
            } else {
                list = jedis.zrevrange(key, start, end);
            }
            if (list == null || list.size() < 1) {
                return null;
            }
            List<T> result = new ArrayList<T>();
            for (String temp : list) {
                T origVal = JSON.parseObject(temp, returnClass);
                result.add(origVal);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        RedisPoolFactory.set("123", "test", 6);
        System.out.println("sfsdjfls===" + RedisPoolFactory.get("123", String.class));

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.zadd("aa", 0.3, "123");
            jedis.zadd("aa", 0.2, "1234");
            jedis.zadd("aa", 0.4, "12346");
            jedis.zadd("aa", 0.1, "1");
            jedis.zadd("aa", 0.11, "1.1");
            jedis.zadd("aa", 0.111, "1.11");
            // jedis.zadd("aa", 0.4, "12346");

            // Set<String> lists = jedis.zrevrange("aa", 0, 3);
            Set<String> lists = jedis.zrange("aa", 0, 4);
            for (String temp : lists) {
                System.out.println(temp);
            }
            jedis.del("aa");

            System.out.println("sfsdf");
            lists = jedis.zrevrange("aa", 0, 3);
            for (String temp : lists) {
                System.out.println(temp);
            }
            // jedis.zadd(key, score, member);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
    }

}