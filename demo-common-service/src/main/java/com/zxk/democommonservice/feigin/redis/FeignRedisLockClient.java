package com.zxk.democommonservice.feigin.redis;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "redis")
public interface FeignRedisLockClient {

    @RequestMapping(value = "/m2.0/redis/tryGetDistributedLock",method = RequestMethod.POST)
    boolean tryGetDistributedLock(@RequestParam("lockKey") String lockKey, @RequestParam("requestId") String requestId, @RequestParam("expireTime") int expireTime);

    @RequestMapping(value = "/m2.0/redis/releaseDistributedLock",method = RequestMethod.POST)
    boolean releaseDistributedLock(@RequestParam("lockKey") String lockKey, @RequestParam("requestId") String requestId);
}
