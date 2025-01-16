package com.bamboo.BambooBomb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class LoginController {
    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/login/callback")
    public String loginSuccess(
        @RequestParam(value="state", required=false) String state, 
        @RequestParam(value="code", required=false) String code, 
        @RequestParam(value="error", required=false) String error, 
        @RequestParam(value="error_description", required=false) String error_description
        ) {
        if (error != null) {
            return error_description;
        }
        if (state == null) {
            return "state is null";
        }
        if (code == null) {
            return "code is null";
        }

        return "Callback 성공! : " + state + code;
    }
    
    // 접근 토큰 발급 요청 url (GET / POST (json))
    @GetMapping("/login/getToken")
    public ResponseEntity<Map<String, String>> getAccessToken(
        @RequestParam(value="state") String state, 
        @RequestParam(value="code") String code
        )   
    {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
    
        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);
        
        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            tokenUrl,
            HttpMethod.POST,
            requestEntity,
            String.class
        );



        System.out.println(response.getBody());
        String responseBody = response.getBody();
        if (responseBody.contains("access_token")) {
            String accessToken = responseBody.split("\"access_token\":\"")[1].split("\"")[0];
            String refreshToken = responseBody.split("\"refresh_token\":\"")[1].split("\"")[0];
            String tokenType = responseBody.split("\"token_type\":\"")[1].split("\"")[0];
            String expires_in = responseBody.split("\"expires_in\":\"")[1].split("\"")[0];

            // 액세스 토큰을 클라이언트에게 반환
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("access_token", accessToken);
            responseMap.put("refresh_token", refreshToken);
            responseMap.put("token_type", tokenType);
            responseMap.put("expires_in", expires_in);

            return ResponseEntity.ok(responseMap);
        } else {
            String error = responseBody.split("\"error\":\"")[1].split("\"")[0];
            String error_description = responseBody.split("\"error_description\":\"")[1].split("\"")[0];

            // 액세스 토큰을 클라이언트에게 반환
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("error", error);
            responseMap.put("error_description", error_description);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
    }
}
