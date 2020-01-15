package org.apereo.cas.web;

import lombok.NoArgsConstructor;
import lombok.val;
import org.apereo.cas.CasEmbeddedContainerUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This is {@link CasWebApplication}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class,
        JerseyAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class,
        JmxAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceHealthIndicatorAutoConfiguration.class,
        RedisAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        CassandraAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@EnableConfigurationProperties(CasConfigurationProperties.class)
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@NoArgsConstructor
@EnableCircuitBreaker
public class CasWebApplication {
    /**
     * Main entry point of the CAS web application.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        val properties = CasEmbeddedContainerUtils.getRuntimeProperties(Boolean.TRUE);
        val banner = CasEmbeddedContainerUtils.getCasBannerInstance();
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CasWebApplication.class)
                .banner(banner)
                .web(WebApplicationType.SERVLET)
                .properties(properties)
                .logStartupInfo(true)
                .contextClass(CasWebApplicationContext.class);
        builder.application().setAllowBeanDefinitionOverriding(true);
        builder.run(args);
    }
}
