package com.demo.consumer.feign;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feign")
public class FeignController {

   private final ProviderFeignClient providerFeignClient;

    public FeignController(ProviderFeignClient providerFeignClient) {
        this.providerFeignClient = providerFeignClient;
    }

    @GetMapping("/instance")
    public String getInstanceInfo(){
            return providerFeignClient.getInstanceInfo();
    }

}
