package com.example.nasaproxyservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class NasaProxyController {

    @Value("${nasa.api.url}")
    private String baseUrl;

    @Value("${nasa.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @GetMapping("/pictures")
    public JsonNode getPictures(@RequestParam Integer sol,
                                @RequestParam Optional<String> camera,
                                @RequestParam Optional<String> apiKey) {
        return restTemplate.getForObject(buildUri(sol, camera, apiKey), JsonNode.class);
    }

    private URI buildUri(Integer sol, Optional<String> camera, Optional<String> apiKey) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("sol", sol)
                .queryParam("api_key", apiKey.orElse(this.apiKey))
                .queryParamIfPresent("camera", camera)
                .build()
                .toUri();
    }
}
