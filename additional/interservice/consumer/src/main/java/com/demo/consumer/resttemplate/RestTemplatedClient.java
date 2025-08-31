package com.demo.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplatedClient{

    private RestTemplate restTemplate;

    public RestTemplatedClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    private static  final String PROVIDER_URL = "http://localhost:8081";
private static  final String PROVIDER_URL = "http://PROVIDER";

    public String getInstanceInfo(){
        return restTemplate.getForObject(PROVIDER_URL + "/instance-info", String.class);
    }


}
