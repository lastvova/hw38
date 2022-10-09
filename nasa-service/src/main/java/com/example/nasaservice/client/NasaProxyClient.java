package com.example.nasaservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nasa-proxy-service")
public interface NasaProxyClient {

    @GetMapping("/pictures")
    JsonNode getPictures(@RequestParam Integer sol,
                         @RequestParam(required = false) String camera,
                         @RequestParam(required = false) String apiKey);
}
