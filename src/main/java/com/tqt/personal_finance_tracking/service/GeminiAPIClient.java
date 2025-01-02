package com.tqt.personal_finance_tracking.service;

import com.tqt.personal_finance_tracking.config.AppConfig;
import com.tqt.personal_finance_tracking.model.gemini.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class GeminiAPIClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    @Autowired
    private AppConfig appConfig;

    public Response callGenerativeAPI(String prompt) {
        // Tạo URL endpoint với API Key
        String url = endpoint + appConfig.getGeminiKey();

        // Tạo payload JSON
        JSONObject parts = new JSONObject().put("text", prompt);
        JSONObject contents = new JSONObject().put("parts", new JSONObject[]{parts});
        JSONObject payload = new JSONObject().put("contents", new JSONObject[]{contents});

        // Tạo headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(payload.toString(), headers);

        try {
            // Gửi POST request và nhận phản hồi
            ResponseEntity<Response> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Response.class
            );
            return response.getBody(); // Trả về nội dung phản hồi
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
