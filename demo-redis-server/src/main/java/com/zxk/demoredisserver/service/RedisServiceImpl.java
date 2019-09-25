package com.zxk.demoredisserver.service;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Tuple;

import java.util.*;

@Service
@RestController
@Api(value = "redis接口服务")
public class RedisServiceImpl{
	private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
//	@Autowired
//	private JedisSentinelPool jedisPool;
	@Autowired
	private JedisPool jedisPool;

	@RequestMapping(value = "getPaymentOrderParamter", method = RequestMethod.POST)
	public String getPaymentOrderParamter(String orderCode) {
		String paymentData = get(orderCode);
		if (StringUtils.isNotBlank(paymentData)) {
			return paymentData;
		}

		return "";
	}

	@SuppressWarnings("deprecation")
	public void close(Jedis jedis) {
		try {
			jedisPool.returnResource(jedis);
			// jedis.close();
		} catch (Exception e) {
			if (jedis.isConnected()) {
				jedis.quit();
				jedis.disconnect();
			}
		}
	}

	@RequestMapping(value = "setnx", method = RequestMethod.POST)
    public Long setnx(String key, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("redis setnx error", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    @RequestMapping(value = "set", method = RequestMethod.POST)
    public boolean set(@RequestParam(value = "key", required = true) String key,
                       @RequestParam(value = "value", required = true) String value,
                       @RequestParam(value = "seconds", required = false) Integer seconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
			if (seconds != null) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error("redis set error", e);
		} finally {
			close(jedis);
		}
		return result.equalsIgnoreCase("OK");
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	public String get(@RequestParam(value = "key", required = true) String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("redis get error", e);
		} finally {
			close(jedis);
		}
		return value;

	}

	@RequestMapping(value = "keys", method = RequestMethod.POST)
	public Set<String> keys(@RequestParam(value = "keyPattarn", required = true) String keyPattarn) {
		Set<String> keys = new TreeSet<>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			keys = jedis.keys(keyPattarn);
		} finally {
			close(jedis);
		}
		return keys;

	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	public Long del(@RequestParam(value = "key", required = true) String key) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.del(key);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "hasKey", method = RequestMethod.POST)
	public Boolean hasKey(@RequestParam(value = "key", required = true) String key) {
		Jedis jedis = null;
		Boolean result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
		} finally {
			close(jedis);
		}
		return result;

	}

	/**
	 * [string]用于浮点型数值的增加，譬如marvel级别的交易额的增加等
	 *
	 * @param key
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "incrByFloat", method = RequestMethod.POST)
	public Double incrByFloat(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "amount", required = true) Double amount) {
		Jedis jedis = null;
		Double result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.incrByFloat(key, amount);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [string]用于整型数值的增加，常规操作，譬如：某贴牌当前的用户总数等
	 *
	 * @param key
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "incrBy", method = RequestMethod.POST)
	public Long incrBy(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "num", required = true) Long num) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.incrBy(key, num);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [hash]key:marvel:time 平台级按时间统计；field: 指标，如turnover
	 * value：用于浮点型数值的增加，譬如marvel级别的交易额的增加等
	 *
	 * @param key
	 * @param field
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "hincrByFloat", method = RequestMethod.POST)
	public Double hincrByFloat(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "field", required = true) String field,
			@RequestParam(value = "amount", required = true) Double amount) {

		Jedis jedis = null;
		Double result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hincrByFloat(key, field, amount);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [hash]key:marvel:branid:time 平台级按时间统计；field：指标，如用户数
	 * value：用于整型数值的增加，常规操作，譬如：某贴牌当前的用户总数等
	 *
	 * @param key
	 * @param field
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "hincrBy", method = RequestMethod.POST)
	public Long hincrBy(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "field", required = true) String field, @RequestParam(value = "num") Long num) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hincrBy(key, field, num);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [set] 判断某个成员是否属于集合
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "sismember", method = RequestMethod.POST)
	public Boolean sismember(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "member", required = true) String member) {

		Jedis jedis = null;
		Boolean result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.sismember(key, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [set]用于往集合中添加一个元素，无序/唯一
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "sadd", method = RequestMethod.POST)
	public Long sadd(String key, String... member) {

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.sadd(key, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [set]用于获取集合的长度
	 *
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "scard", method = RequestMethod.POST)
	public Long scard(String key) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.scard(key);
		} finally {
			close(jedis);
		}
		return result;

	}

	/**
	 * [set]用于获取集合的长度
	 *
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "srem", method = RequestMethod.POST)
	public Long srem(String key, String member) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.srem(key, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "expice", method = RequestMethod.POST)
	public Long expice(String key, Integer seconds) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.expire(key, seconds);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [zset] 用户获取有序集合中某个成员的得分，适用于查看某个用户其黄金会员的数量
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "zscore", method = RequestMethod.POST)
	public Double zscore(String key, String member) {
		Jedis jedis = null;
		Double result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zscore(key, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * 将某个成员从srckey集合移动到dstkey集合
	 *
	 * @param srckey
	 * @param dstkey
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "smove", method = RequestMethod.POST)
	public Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.smove(srckey, dstkey, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "zadd", method = RequestMethod.POST)
	public Long zadd(String key, float score, String member) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zadd(key, score, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "zadds", method = RequestMethod.POST)
	public Long zadds(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zadd(key, scoreMembers);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "zrem", method = RequestMethod.POST)
	public Long zrem(String key, String member) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zrem(key, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "zcard", method = RequestMethod.POST)
	public Long zcard(String key) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zcard(key);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "zincrby", method = RequestMethod.POST)
	public Double zincrby(String key, float score, String member) {
		Jedis jedis = null;
		Double result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zincrby(key, score, member);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [zset] 根据有序集合对象score区间获取集合对象set
	 *
	 * @param key
	 * @param scoremin
	 * @param scoremax
	 * @return
	 */
	@RequestMapping(value = "zrangebyscore", method = RequestMethod.POST)
	public Set zrangebyscore(String key, float scoremin, float scoremax) {
		Jedis jedis = null;
		Set result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zrangeByScore(key, scoremin, scoremax);
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * [zset] 根据有序集合下标区间获取集合对象set
	 *
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	@RequestMapping(value = "zrange", method = RequestMethod.POST)
	public Set zrange(String key, long start, long stop) {
		Jedis jedis = null;
		Set result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zrange(key, start, stop);
		} finally {
			close(jedis);
		}
		return result;
	}

	public Set zrevrange(String key, long start, long stop) {
		Jedis jedis = null;
		Set result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zrevrange(key, start, stop);
		} finally {
			close(jedis);
		}
		return result;
	}

	/***
	 * @Author qin
	 * @Description [zSet]根据有序集合下标区间获取集合对象set 降序,去掉score，只返回key的set
	 * @Date 14:05 2019/8/21
	 * @Param [key, start, stop]
	 * @return java.util.Set
	 **/
	@RequestMapping(value = "zrevrangeWithScores", method = RequestMethod.POST)
	public List<String> zrevrangeWithscores(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop){
		Jedis jedis = null;
		Set<Tuple> temp;
		List<String> result=new ArrayList<>();
		try {
			jedis = jedisPool.getResource();
			temp = jedis.zrevrangeWithScores(key, start, stop);
			Iterator<Tuple> iterator = temp.iterator();
			while (iterator.hasNext()){
				Tuple tuple = iterator.next();
				result.add(tuple.getElement());
			}
		} finally {
			close(jedis);
		}
		return result;
	}

	/***
	 * @Author zhangchaofeng
	 * @Description [zSet]根据有序集合下标区间获取集合对象set 降序
	 * @Date 14:05 2019/8/21
	 * @Param [key, start, stop]
	 * @return java.util.Set
	 **/
	@RequestMapping(value = "zrevrange", method = RequestMethod.POST)
	public Set zrevrangeWithScores(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("stop") long stop){
		Jedis jedis = null;
		Set result;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zrevrangeWithScores(key, start, stop);
		} finally {
			close(jedis);
		}
		return result;
	}


	/**
	 * [zset] 根据有序集合的并集
	 *
	 * @param newkey
	 *            将结果放入新集合的key 名称
	 * @param key
	 *            需要做 并 操作的集合key的数组
	 * @return
	 */
	@RequestMapping(value = "zunionstore", method = RequestMethod.POST)
	public Long zunionstore(String newkey, String... key) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.zunionstore(newkey, key);
		} finally {
			close(jedis);
		}
		return result;
	}

	@RequestMapping(value = "setex", method = RequestMethod.POST)
	public String setex(String key, String value, int second) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, second, value);
        } catch (Exception e) {
            logger.error("redis setnx error", e);
        } finally {
            close(jedis);
        }
        return result;
    }

	public Long setnx(String key, String value, int second) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
            jedis.expire(key, second);
        } catch (Exception e) {
            logger.error("redis setnx error", e);
        } finally {
            close(jedis);
        }
        return result;
    }

}
