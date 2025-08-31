package com.demo.consumer.resttemplate;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/rest-template")
public class RestTemplateController {

    private RestTemplatedClient restTemplatedClient;

    public RestTemplateController(RestTemplatedClient restTemplatedClient) {
        this.restTemplatedClient = restTemplatedClient;
    }

    @GetMapping("/instance")
    public String getInstance(){
        return restTemplatedClient.getInstanceInfo();
    }
}
