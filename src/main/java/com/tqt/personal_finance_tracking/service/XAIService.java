package com.tqt.personal_finance_tracking.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tqt.personal_finance_tracking.config.AppConfig;
import com.tqt.personal_finance_tracking.model.xai.ChatCompletion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class XAIService {

    private static final String XAI_API_URL = "https://api.x.ai/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AppConfig appConfig;

    public XAIService(RestTemplate restTemplate, ObjectMapper objectMapper, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.appConfig = appConfig;
    }

    public ChatCompletion callXAI(String userMessage) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", "grok-beta");
            requestBody.put("stream", false);
            requestBody.put("temperature", 0);

            // Add messages array
            ObjectNode systemMessage = objectMapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a highly advanced Natural Language Processing (NLP) chatbot. Your primary goal is to optimize language understanding, extraction, and generation for seamless communication. Adhere to the following principles:" +
                    "Natural Understanding: Parse and interpret user inputs with precision, capturing intent, sentiment, and context accurately. Account for both explicit and implicit meanings." +
                    "Optimized Response: Generate concise, relevant, and human-like responses that align with the user's context and query. Avoid ambiguous or overly complex phrasing.");

            ObjectNode userMessageNode = objectMapper.createObjectNode();
            userMessageNode.put("role", "user");
            userMessageNode.put("content", userMessage);

            requestBody.putArray("messages").add(systemMessage).add(userMessageNode);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + appConfig.getXaiKey());

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            ResponseEntity<ChatCompletion> response = restTemplate.exchange(XAI_API_URL, HttpMethod.POST, entity, ChatCompletion.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while calling XAI API", e);
        }
    }

}
