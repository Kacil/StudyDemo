package com.zxk.demoredisserver.conf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

//	@Value(value = "${spring.redis.password}")
	private String password = null;

//	@Value(value = "${spring.redis.address}")
	private String node = "127.1.0.1:6379";

	static final String DELIMITER = ":";
//	@Value(value = "${spring.redis.master}")
	private String master = "mymaster";

//	@Bean
//	public GenericObjectPoolConfig genericObjectPoolConfig() {
//		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//		config.setMaxWaitMillis(2000);
//		config.setMaxTotal(500);
//		config.setMinIdle(1);
//		config.setMaxIdle(800);
//		config.setTestOnBorrow(true);
//		return config;
//	}

//	@Bean
//	public JedisCluster jedisCluster(GenericObjectPoolConfig genericObjectPoolConfig) {
//		return new JedisCluster(getHostAndPorts(), 5000, 5000, 3000, password, genericObjectPoolConfig);
//	}
//
//	private Set<HostAndPort> getHostAndPorts() {
//		Set<HostAndPort> nodes = new HashSet<>();
////		String[] hostPortPair = node.split(DELIMITER);
////		nodes.add(new HostAndPort(hostPortPair[0].trim(), Integer.valueOf(hostPortPair[1].trim())));
//		String[] nodeArr = node.split(",");
//		for (String hostAndPort : nodeArr) {
//			String[] hostPortPair = hostAndPort.split(DELIMITER);
//			nodes.add(new HostAndPort(hostPortPair[0].trim(), Integer.valueOf(hostPortPair[1].trim())));
//		}
//		return nodes;
//	}

	/**redis集群配置**/
//	@Bean
//	public JedisSentinelPool jedisSentinelPool(JedisPoolConfig jedisPoolConfig) {
//		return new JedisSentinelPool(master, getSentinels(), jedisPoolConfig, password);
//	}

	/**redis配置**/
	@Bean
	public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
		return new JedisPool(jedisPoolConfig, "127.1.0.1", 6379);
	}

	@Bean
	public JedisPoolConfig genericObjectPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(5000);
		config.setMaxIdle(256);
		config.setMaxWaitMillis(5000L);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
		config.setMinEvictableIdleTimeMillis(60000L);
		config.setTimeBetweenEvictionRunsMillis(3000L);
		config.setNumTestsPerEvictionRun(-1);
		return config;
	}



//	private Set<String> getSentinels() {
//		Set<String> sentinels = new HashSet<String>();
////		sentinels.add(node);
//		String[] nodeArr = node.split(",");
//		for (String hostAndPort : nodeArr) {
//			sentinels.add(hostAndPort);
//		}
//		return sentinels;
//	}

}
