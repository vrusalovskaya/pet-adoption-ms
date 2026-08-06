package com.wise.adoption_service.adoption.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class RestClientConfig {
    @Bean
    @LoadBalanced
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    CatalogApiClient catalogApiClient(@LoadBalanced RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://catalog-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CatalogApiClient.class);
    }

    @Bean
    UserApiClient userApiClient(@LoadBalanced RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://user-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserApiClient.class);
    }
}