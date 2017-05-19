package com.yangmao.dal.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import com.yangmao.dal.cache.CacheClient;
import com.yangmao.dal.cache.CacheConfig;
import com.yangmao.dal.cache.CacheNode;
import freemarker.template.utility.StringUtil;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xxm on 15/9/15.
 */
public class SimpleRedisCacheClient implements CacheClient {

    private boolean isCluster;

    static Logger logger = LoggerFactory.getLogger(SimpleRedisCacheClient.class);

    private CacheConfig config;

    private JedisCommands jedis;

    private JedisPool singleJedisPool;


    public SimpleRedisCacheClient(CacheConfig cacheConfig) {
        try {

            this.config = cacheConfig;
            ArrayList<CacheNode> nodes = getNodes(cacheConfig.getHost());

            if(nodes.size() == 0) throw new Exception("redis node length = 0");

            if(nodes.size() > 1) {
                this.isCluster = true;
                Set<HostAndPort> jedisClusterNodes = new HashSet<>();
                for (CacheNode node : nodes) {
                    jedisClusterNodes.add(new HostAndPort(node.getHost(), node.getPort()));
                }
                JedisCluster jc = new JedisCluster(jedisClusterNodes);
                this.jedis = jc;
            } else {
                CacheNode cacheNode = nodes.get(0);

                JedisPoolConfig poolConfig = new JedisPoolConfig();
                poolConfig.setMaxTotal(100);  //连接池允许的最大连接数
                poolConfig.setMaxIdle(100);  //连接池允许的最大空闲连接数
                poolConfig.setMaxWaitMillis(10 * 1000); //连接池超时配置，单位：毫秒

                this.singleJedisPool = new JedisPool(poolConfig, cacheNode.getHost(), cacheNode.getPort());

            }


        } catch (Exception e) {
            logger.error(e.getMessage() + "redis cluster init error", e);
        }

    }


    public <T extends Object> T get(String key, Class<T> clazz) {
        try {
            if(!isCluster) {
                synchronized(this) {
                    this.jedis = this.singleJedisPool.getResource();
                }
            }
            String r = jedis.get(formatKey(key));
            if(!com.mysql.jdbc.StringUtils.isNullOrEmpty(r)) {
                T t = JSON.parseObject(r, clazz);
                return t;
            }
            return null;

        } catch (Exception e) {
            logger.error("jedis error : " + e.getMessage(), e);
            return null;
        } finally {
            if(this.singleJedisPool != null && this.jedis != null) {
                ((Jedis)this.jedis).close();
                //this.singleJedisPool.returnResource((Jedis)this.jedis);
            }
        }

    }


    public String get(String key) {
        try {
            if(!isCluster) {
                synchronized(this) {
                    this.jedis = this.singleJedisPool.getResource();
                }
            }
            String r = jedis.get(formatKey(key));
            return r;
        } catch (Exception e) {
            logger.error("jedis error : " + e.getMessage(), e);
            return null;
        } finally {
            if(this.singleJedisPool != null) {
                this.singleJedisPool.returnResource((Jedis) this.jedis);
            }
        }
    }


    public void set(String key, String value) {
        try {
            if(!isCluster) {
                synchronized(this) {
                    this.jedis = this.singleJedisPool.getResource();
                }
            }
            jedis.setex(formatKey(key), config.getExpireTime(), value);
        } catch (Exception e) {
            logger.error("jedis error : " + e.getMessage(), e);
        } finally {
            if(this.singleJedisPool != null) {
                this.singleJedisPool.returnResource((Jedis) this.jedis);
            }
        }
    }

    public void set(String key, Object value) {
        try {
            if(!isCluster) {
                synchronized(this) {
                    this.jedis = this.singleJedisPool.getResource();
                }
            }
            String v = JSONObject.toJSONString(value);
            jedis.setex(formatKey(key), config.getExpireTime(), v);
        } catch (Exception e) {
            logger.error("jedis error : " + e.getMessage(), e);
        } finally {
            if(this.singleJedisPool != null) {
                this.singleJedisPool.returnResource((Jedis) this.jedis);
            }
        }
    }


    public void remove(String key) {

        try {
            if(!isCluster) {
                synchronized(this) {
                    this.jedis = this.singleJedisPool.getResource();
                }
            }
            jedis.del(formatKey(key));
        } catch (Exception e) {
            logger.error("jedis error : " + e.getMessage(), e);
        } finally {
            if(this.singleJedisPool != null) {
                this.singleJedisPool.returnResource((Jedis) this.jedis);
            }
        }
    }


    @Override
    public JedisCommands getJedisCmds() {
        if(!isCluster) {
            this.jedis = this.singleJedisPool.getResource();
        }
        return this.jedis;
    }

    /**************************************
     private methods
     **************************************/


    private String formatKey(String key) {
        return String.format("%s_%s"
                , this.config.getPrefix()
                , key
        );
    }

    private ArrayList<CacheNode> getNodes(String config) throws Exception {
        if(StringUtils.isEmpty(config)) {
            throw new Exception("config can not be null");
        }
        String[] hosts = config.split(",");
        ArrayList<CacheNode> nodes = new ArrayList<>();

        for(String h : hosts) {
            String[] hp = h.split(":");
            CacheNode cacheNode = new CacheNode();
            cacheNode.setHost(hp[0]);
            cacheNode.setPort(Integer.parseInt(hp[1]));
            nodes.add(cacheNode);
        }

        return nodes;
    }
}
