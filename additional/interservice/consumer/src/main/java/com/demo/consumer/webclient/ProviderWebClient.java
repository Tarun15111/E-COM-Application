package com.demo.consumer.webclient;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProviderWebClient {

    private final WebClient webClient;

    public ProviderWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getInstanceInfo(){

        return webClient.get()
                        .uri("/instance-info")
                        .retrieve()
                        .bodyToMono(String.class);

    }

}
