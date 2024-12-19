package com.side.moneymoriapi.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan(basePackageClasses = RedisProperties.class)
@EnableRedisRepositories(basePackages = "com.side")
@EntityScan(basePackages = "com.side")
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConfiguration redisConfiguration;

        // Standalone:
        redisConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort()
        );
//        else {
//            // Cluster: (Node가 1개여도 클러스터가 될 수 있음. 최소 3개일 때 생성 -> 2개 지우면 1개)
//            redisConfiguration = new RedisClusterConfiguration(
//                    redisProperties.getCluster().getNodes()
//            );
//        }

        return new LettuceConnectionFactory(redisConfiguration);
    }
}
