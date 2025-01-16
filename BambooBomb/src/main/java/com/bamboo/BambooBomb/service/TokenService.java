package com.bamboo.BambooBomb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;
    private final String tokenUrl = "https://nid.naver.com/oauth2.0/token";

    private final RestTemplate restTemplate;

    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 공통 토큰 요청 메소드
    private ResponseEntity<String> requestToken(MultiValueMap<String, String> params) {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // 요청 보내기
        return restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            requestEntity,
            String.class
        );
    }

    // 토큰 발급
    public Map<String, String> getAccessToken(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);

        ResponseEntity<String> response = requestToken(params);
        return parseTokenResponse(response.getBody());
    }


    // 응답에서 토큰 정보를 파싱
    private Map<String, String> parseTokenResponse(String responseBody) {
        Map<String, String> responseMap = new HashMap<>();

        if (responseBody.contains("access_token")) {
            String accessToken = responseBody.split("\"access_token\":\"")[1].split("\"")[0];
            String refreshToken = responseBody.split("\"refresh_token\":\"")[1].split("\"")[0];
            String tokenType = responseBody.split("\"token_type\":\"")[1].split("\"")[0];
            String expiresIn = responseBody.split("\"expires_in\":\"")[1].split("\"")[0];

            responseMap.put("access_token", accessToken);
            responseMap.put("refresh_token", refreshToken);
            responseMap.put("token_type", tokenType);
            responseMap.put("expires_in", expiresIn);
        } else {
            String error = responseBody.split("\"error\":\"")[1].split("\"")[0];
            String errorDescription = responseBody.split("\"error_description\":\"")[1].split("\"")[0];

            responseMap.put("error", error);
            responseMap.put("error_description", errorDescription);
        }

        return responseMap;
    }
}
