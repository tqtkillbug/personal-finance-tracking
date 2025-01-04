package com.tqt.personal_finance_tracking.notation;

import com.tqt.personal_finance_tracking.contants.Contants;
import com.tqt.personal_finance_tracking.model.NotionPageResponse;
import com.tqt.personal_finance_tracking.model.NotionProperties;
import com.tqt.personal_finance_tracking.model.notion.NotionQuery;
import com.tqt.personal_finance_tracking.model.notion.NotionQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotionService {
    @Autowired
    private NotionClient notionClient;


    private static final String PAGE_ID = "15c0d9d8-0cda-802d-9971-f9d0288785a1";


    public NotionPageResponse insertPage(NotionProperties properties, String databaseId){
        return notionClient.insertData(databaseId, properties);
    }

    public Map<String, Object> getPageData() {
        NotionPageResponse pageData = notionClient.fetchPage(PAGE_ID);

        Map<String, Object> data = new HashMap<>();


        return data;
    }

    public Map<String, Object> getUsers() {
        NotionUserList usersData = notionClient.getUsers();


        return new HashMap<>();
    }
    public Map<String, Object> retrieveDatabase() {
        NotionDatabase usersData = notionClient.retrieveDatabase(Contants.DATABASE_INCOME_ID);


        return new HashMap<>();
    }

    public NotionQueryResponse queryDatabase(NotionQuery query) {
        NotionQueryResponse usersData = notionClient.queryDatabase(Contants.DATABASE_EXPESENS_ID, query);


        return usersData;
    }

    public NotionPageResponse deletePage(String pageId) {
       return notionClient.deletePage(pageId);
    }







        

    }