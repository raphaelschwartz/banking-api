package com.rschwartz.bankingapi.common.adapter.out.config;

import com.rschwartz.bankingapi.common.adapter.out.constants.CacheNames;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
@Configuration
public class RedisConfig {

  @Value("${cache.config.entryTtl:60}")
  private int entryTtl;

  @Value("${cache.config.account.entryTtl:30}")
  private int accountEntryTtl;

  @Bean
  public RedisCacheConfiguration cacheConfiguration() {

    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(entryTtl))
        .disableCachingNullValues()
        .serializeValuesWith(
            SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {

    return builder -> {
      final RedisCacheConfiguration accountConfiguration = RedisCacheConfiguration.defaultCacheConfig()
          .entryTtl(Duration.ofMinutes(accountEntryTtl));
      builder.withCacheConfiguration(CacheNames.ACCOUNT, accountConfiguration);
    };

  }

  @Bean
  public CacheManager cacheManager() {

    final ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager();
    concurrentMapCacheManager.setCacheNames(List.of(CacheNames.ACCOUNT));

    return concurrentMapCacheManager;
  }

}
