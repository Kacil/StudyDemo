package com.zxk.demoredisserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Collections;

@Service
@RestController
public class RedisLockImpl{

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

//    @Autowired
//    private JedisSentinelPool jedisPool;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取锁
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    @RequestMapping(value = "/m2.0/redis/tryGetDistributedLock",method = RequestMethod.POST)
    public boolean tryGetDistributedLock(@RequestParam("lockKey") String lockKey,@RequestParam("requestId") String requestId,@RequestParam("expireTime") int expireTime){
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.set(lockKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,expireTime);
            if (LOCK_SUCCESS.equals(result)){
                return true;
            }
        }catch (Exception e){
            throw new RuntimeException("异常结果"+e);
        }finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 释放锁
     * @param lockKey
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/m2.0/redis/releaseDistributedLock",method = RequestMethod.POST)
    public boolean releaseDistributedLock(@RequestParam("lockKey") String lockKey,@RequestParam("requestId") String requestId){
        Jedis jedis = jedisPool.getResource();
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey),Collections.singletonList(requestId));
            if (RELEASE_SUCCESS.equals(result)){
                return true;
            }
        }catch (Exception e){
            throw new RuntimeException("异常结果"+e);
        }finally {
            jedis.close();
        }

        return false;
    }

}
