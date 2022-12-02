package it.cgmconsulting.sentence.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("loadBalancedWebClientBuilder")
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
    @Bean("loadBalancedWebClient")
    public WebClient loadBalancedWebClient(
            WebClient.Builder loadBalancedWebClientBuilder) {
        return loadBalancedWebClientBuilder.build();
    }

}