package com.demo.consumer.httpinterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/http-interface")
public class HttpController {

    private final ProviderHttpInterface clientInterface;

    public HttpController(ProviderHttpInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    @GetMapping("/instance")
    public String getInstanceDetails(){
        return clientInterface.getInstanceInfo();
    }

}
