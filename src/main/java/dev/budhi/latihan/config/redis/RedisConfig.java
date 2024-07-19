package dev.budhi.latihan.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {

//    // config legacy
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return redisTemplate;
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
//                .RedisCacheManagerBuilder
//                .fromConnectionFactory(redisConnectionFactory());
//        return builder.build();
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(5)); // Set the TTL to 5 seconds
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(cacheConfiguration)
//                .build();
//    }
//
//
//}

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {
    // config latest

    private final ObjectMapper objectMapper;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

//        Mengatur connectionFactory untuk menghubungkan dengan instance Redis. Menggunakan StringRedisSerializer untuk serialisasi kunci.
//        Menggunakan GenericJackson2JsonRedisSerializer untuk serialisasi nilai, yang memungkinkan penyimpanan objek Java
//        sebagai JSON dalam Redis.

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        return template;
    }

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
////         Mengatur waktu hidup (TTL) cache menjadi 5 detik. Ini berarti setiap entri cache akan kedaluwarsa dan dihapus dari Redis setelah 5 detik.
////         Cache yang dikelola akan secara otomatis menghapus data yang kedaluwarsa, membantu mengelola penggunaan memori dan memastikan data yang tersimpan selalu segar.
//
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(5)); // Set the TTL to 5 seconds
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }

//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//
////     Mengatur waktu hidup (TTL) cache menjadi 5 detik. Ini berarti setiap entri cache akan kedaluwarsa dan dihapus dari Redis setelah 5 detik.
////     Cache yang dikelola akan secara otomatis menghapus data yang kedaluwarsa, membantu mengelola penggunaan memori dan memastikan data yang tersimpan selalu segar.
////     Sama dengan konfigurasi CacheManager pertama, tetapi dengan penambahan eksplisit penggunaan GenericJackson2JsonRedisSerializer untuk serialisasi nilai.
////     Sama dengan konfigurasi sebelumnya, tetapi dengan penegasan pada jenis serialisasi yang digunakan untuk nilai.
//
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                .entryTtl(Duration.ofSeconds(5)); // Set the TTL to 5 seconds
//
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // ObjectMapper dikonfigurasi dengan JavaTimeModule untuk penanganan yang lebih baik terhadap tipe data waktu.
        // TTL diatur menjadi 1 menit.
        // Konfigurasi ini memungkinkan serialisasi dan deserialisasi yang lebih akurat untuk objek yang mengandung data waktu, dengan TTL yang lebih panjang untuk cache.

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Ensure dates are written as strings
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                .entryTtl(Duration.ofMinutes(1)); // Set the TTL cache to 1 minutes;

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }

}