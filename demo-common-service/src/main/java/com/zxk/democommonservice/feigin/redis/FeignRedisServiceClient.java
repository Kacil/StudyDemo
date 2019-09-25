package com.zxk.democommonservice.feigin.redis;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@FeignClient(value = "redis")
public interface FeignRedisServiceClient {

    @RequestMapping(value = "getPaymentOrderParamter", method = RequestMethod.POST)
    String getPaymentOrderParamter(String orderCode);

    @RequestMapping(value = "set", method = RequestMethod.POST)
    boolean set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("seconds") Integer seconds);

    @RequestMapping(value = "setnx", method = RequestMethod.POST)
    Long setnx(@RequestParam("key") String key, @RequestParam("value") String value);
    
    @RequestMapping(value = "setnxSecond", method = RequestMethod.POST)
    Long setnx(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("second") int second);
    
    @RequestMapping(value = "setex", method = RequestMethod.POST)
    String setex(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("second") int second);

    @RequestMapping(value = "get", method = RequestMethod.POST)
    String get(@RequestParam("key") String key);

    @RequestMapping(value = "del", method = RequestMethod.POST)
    Long del(@RequestParam("key") String key);

    @RequestMapping(value = "hasKey", method = RequestMethod.POST)
    Boolean hasKey(@RequestParam("key") String key);

    @RequestMapping(value = "keys", method = RequestMethod.POST)
    Set<String> keys(@RequestParam("keyPattarn") String keyPattarn);

    @RequestMapping(value = "incrByFloat", method = RequestMethod.POST)
    Double incrByFloat(@RequestParam("key") String key, @RequestParam("amount") Double amount);

    @RequestMapping(value = "incrBy", method = RequestMethod.POST)
    Long incrBy(@RequestParam("key") String key, @RequestParam("num") Long num);

    //--hash--start---
    @RequestMapping(value = "hincrByFloat", method = RequestMethod.POST)
    Double hincrByFloat(@RequestParam("key") String key, @RequestParam("field") String field, @RequestParam("amount") Double amount);

    @RequestMapping(value = "hincrBy", method = RequestMethod.POST)
    Long hincrBy(@RequestParam("key") String key, @RequestParam("field") String field, @RequestParam("num") Long num);
    //--hash--end---

    //--set--start---
    @RequestMapping(value = "sismember", method = RequestMethod.POST)
    Boolean sismember(@RequestParam("key") String key, @RequestParam("member") String member);

    @RequestMapping(value = "sadd", method = RequestMethod.POST)
    Long sadd(@RequestParam("key") String key, @RequestParam("member") String... member);

    @RequestMapping(value = "scard", method = RequestMethod.POST)
    Long scard(@RequestParam("key") String key);

    @RequestMapping(value = "srem", method = RequestMethod.POST)
    Long srem(@RequestParam("key") String key, @RequestParam("member") String member);

    @RequestMapping(value = "smove", method = RequestMethod.POST)
    Long smove(@RequestParam("srckey") String srckey, @RequestParam("dstkey") String dstkey, @RequestParam("member") String member);
    //--set--end---

    //--zset--start---
    @RequestMapping(value = "zadd", method = RequestMethod.POST)
    Long zadd(@RequestParam("key") String key, @RequestParam("score") float score, @RequestParam("member") String member);

    @RequestMapping(value = "zadds", method = RequestMethod.POST)
    Long zadds(@RequestParam("key") String key, @RequestParam("scoreMembers") Map<String, Double> scoreMembers) ;

    @RequestMapping(value = "zrem", method = RequestMethod.POST)
    Long zrem(@RequestParam("key") String key, @RequestParam("member") String member);

    @RequestMapping(value = "zcard", method = RequestMethod.POST)
    Long zcard(@RequestParam("key") String key);

    @RequestMapping(value = "zincrby", method = RequestMethod.POST)
    Double zincrby(@RequestParam("key") String key, @RequestParam("score") float score, @RequestParam("member") String member);

    @RequestMapping(value = "expice", method = RequestMethod.POST)
    Long expice(@RequestParam("key") String key, @RequestParam("seconds") Integer seconds);

    @RequestMapping(value = "zrangebyscore", method = RequestMethod.POST)
    Set zrangebyscore(@RequestParam("key") String key, @RequestParam("scoremin") float scoremin, @RequestParam("scoremax") float scoremax);

    @RequestMapping(value = "zrange", method = RequestMethod.POST)
    Set zrange(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop);

    @RequestMapping(value = "zrevranges", method = RequestMethod.POST)
    Set zrevrange(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop);

    @RequestMapping(value = "zrevrange", method = RequestMethod.POST)
    Set zrevrangeWithScores(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop);

    @RequestMapping(value = "zrevrangeWithScores", method = RequestMethod.POST)
    List<String> zrevrangeWithscores(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop);

    @RequestMapping(value = "zunionstore", method = RequestMethod.POST)
    Long zunionstore(@RequestParam("newkey") String newkey, @RequestParam("key") String... key);

    @RequestMapping(value = "zscore", method = RequestMethod.POST)
    Double zscore(@RequestParam("key") String key, @RequestParam("member") String member);

}

