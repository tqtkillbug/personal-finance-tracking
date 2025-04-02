package com.tqt.personal_finance_tracking.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.requests.TranscriptParams;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptLanguageCode;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AssemblyAIService {

    private final AssemblyAI assemblyAI;

    public AssemblyAIService(@Value("${assemblyai.apiKey}") String apiKey) {
        this.assemblyAI = AssemblyAI.builder()
                .apiKey(apiKey)
                .build();
    }

    /**
     * Gửi file audio (qua URL) đến AssemblyAI để chuyển đổi thành text.
     * @param fileUrl URL của file audio trên Telegram.
     * @return Nội dung transcript nếu thành công, ngược lại trả về null.
     * @throws InterruptedException
     */
    public String transcribe(String fileUrl) throws InterruptedException {
        TranscriptParams params = TranscriptParams.builder()
                .audioUrl(fileUrl)
                .languageCode(TranscriptLanguageCode.VI) // Đặt ngôn ngữ đầu vào là tiếng Việt
                .punctuate(true)       // Bật thêm dấu câu
                .formatText(true)      // Định dạng văn bản đầu ra
                .build();
        Transcript transcript = assemblyAI.transcripts().transcribe(params);


        log.info("Đang gửi yêu cầu chuyển đổi với transcript id: {}", transcript.getId());

        while (transcript.getStatus() != TranscriptStatus.COMPLETED &&
                transcript.getStatus() != TranscriptStatus.ERROR) {
            Thread.sleep(5000); // đợi 5 giây giữa các lần kiểm tra
            transcript = assemblyAI.transcripts().get(transcript.getId());
            log.info("Trạng thái transcript: {}", transcript.getStatus());
        }

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            log.error("Lỗi khi chuyển đổi audio: {}", transcript.getError().orElse("Unknown error"));
            return null; // Bạn có thể ném exception hoặc xử lý theo ý muốn
        }

        return transcript.getText().get();
    }
}
