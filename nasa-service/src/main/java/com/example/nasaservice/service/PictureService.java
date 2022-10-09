package com.example.nasaservice.service;

import com.example.nasaservice.client.NasaProxyClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final RestTemplate restTemplate;
    private final NasaProxyClient nasaProxyClient;

    public Optional<URI> findPicture(Integer sol, String camera, String apiKey) {
        return Optional.ofNullable(nasaProxyClient.getPictures(sol, camera, apiKey)).stream()
                .flatMap(response -> response.findValues("img_src")
                        .stream()
                        .map(node -> URI.create(node.asText())))
                .parallel()
                .map(url -> new AbstractMap.SimpleEntry<>(url, getLength(url)))
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

    }

    private Long getLength(URI url) {
        URI location = restTemplate.headForHeaders(url)
                .getLocation();
        return restTemplate.headForHeaders(location)
                .getContentLength();
    }

}
