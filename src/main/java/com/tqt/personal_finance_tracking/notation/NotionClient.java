package com.tqt.personal_finance_tracking.notation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqt.personal_finance_tracking.config.AppConfig;
import com.tqt.personal_finance_tracking.model.NotionPageResponse;
import com.tqt.personal_finance_tracking.model.NotionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NotionClient {

    private static final String NOTION_API_URL = "https://api.notion.com/v1/";
    private static final String API_VERSION = "2022-06-28";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppConfig appConfig;

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + appConfig.getNotionApiKey());
        headers.add("Notion-Version", API_VERSION);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T> T executeRequest(String url, HttpMethod method, Object body, Class<T> responseType, HttpHeaders headers) {
        try {
            objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

            String requestBody = body != null ? objectMapper.writeValueAsString(body) : "null";
            log.info("Request Body: {}", requestBody);

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
            return response.getBody();
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize request body", e);
            throw new RuntimeException("Failed to process JSON", e);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API request failed: " + e.getResponseBodyAsString(), e);
        }
    }

    public NotionPageResponse insertData(String databaseId, NotionProperties properties) {
        String url = NOTION_API_URL + "pages";

        // Tạo đối tượng request
        NotionPageRequest requestBody = new NotionPageRequest();
        requestBody.setParent(new NotionParent(databaseId));
        requestBody.setProperties(properties);

        HttpHeaders headers = buildHeaders();
        return executeRequest(url, HttpMethod.POST, requestBody, NotionPageResponse.class, headers);
    }

    public NotionPageResponse fetchPage(String pageId) {
        String url = NOTION_API_URL + "pages/" + pageId;

        HttpHeaders headers = buildHeaders();
        return executeRequest(url, HttpMethod.GET, null, NotionPageResponse.class, headers);
    }

    public NotionUserList getUsers() {
        String url = NOTION_API_URL + "users";

        HttpHeaders headers = buildHeaders();
        return executeRequest(url, HttpMethod.GET, null, NotionUserList.class, headers);
    }

    public NotionDatabase retrieveDatabase(String databaseId) {
        String url = NOTION_API_URL + "databases/" + databaseId;

        HttpHeaders headers = buildHeaders();
        return executeRequest(url, HttpMethod.GET, null, NotionDatabase.class, headers);
    }

    public NotionQueryResponse queryDatabase(String databaseId, NotionQuery query) {
        String url = NOTION_API_URL + "databases/" + databaseId + "/query";

        HttpHeaders headers = buildHeaders();
        return executeRequest(url, HttpMethod.POST, query, NotionQueryResponse.class, headers);
    }
}

@Configuration
class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

class NotionParent {
    private String database_id;

    public NotionParent(String database_id) {
        this.database_id = database_id;
    }

    public String getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(String database_id) {
        this.database_id = database_id;
    }
}

class NotionPageRequest {
    private NotionParent parent;
    private NotionProperties properties;

    public NotionParent getParent() {
        return parent;
    }

    public void setParent(NotionParent parent) {
        this.parent = parent;
    }

    public NotionProperties getProperties() {
        return properties;
    }

    public void setProperties(NotionProperties properties) {
        this.properties = properties;
    }
}




class NotionUserList {
}

class NotionDatabase {
}

class NotionQuery {
}

class NotionQueryResponse {
}
