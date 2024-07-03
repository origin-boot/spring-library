package com.origin.library.port.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.origin.library.infrastructure.redis.ShortcutOperator;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      @Value("${spring.redis.host}") final String host,
      @Value("${spring.redis.port}") final int port,
      @Value("${spring.redis.database}") final int database,
      @Value("${spring.redis.password}") final String password,
      @Value("${spring.redis.prefix}") final String prefix) {

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    JedisConnectionFactory connectionFactory = jedisConnectionFactory(
        host, port, database, password);
    connectionFactory.start();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(template.getStringSerializer());

    if (prefix != null && !prefix.isEmpty()) {
      ShortcutOperator.setGlobalPrefix(prefix);
    }

    return template;
  }

  private JedisConnectionFactory jedisConnectionFactory(
      String host, int port, int database, String password) {

    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setDatabase(database);
    if (password != null && !password.isEmpty()) {
      redisStandaloneConfiguration.setPassword(password);
    }

    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Autowired
  private LogHandlerInterceptor logHandlerInterceptor;

  @Autowired
  private IdentityHandlerInterceptor identityHandlerInterceptor;

  @Autowired
  private UserHandlerInterceptor userHandlerInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logHandlerInterceptor);

    identityHandlerInterceptor.addInterceptors(registry);
    userHandlerInterceptor.addInterceptors(registry);

    // FIXME: Interceptor to implement permission verification
    // FIXME: The data returned to the user from the production environment needs to
    // be removed,
    // but Error.details should be retained in the log.

    // FIXME: Add more interceptors to increase security and robustness
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new RequestUserHandlerMethodArgumentResolver());
  }
}
