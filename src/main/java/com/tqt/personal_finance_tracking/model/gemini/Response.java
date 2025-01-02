package com.tqt.personal_finance_tracking.model.gemini;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;
}
