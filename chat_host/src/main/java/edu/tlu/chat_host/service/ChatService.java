package edu.tlu.chat_host.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import edu.tlu.chat_host.dto.ChatResponse;
import edu.tlu.chat_host.enums.ChatRole;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final CurrentUserService currentUserService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatResponse chat(@NonNull String message) throws BadCredentialsException {
        log.debug("Chat request received, message length={}", message.length());
        String content = chatClient.prompt()
                .advisors(
                // a -> a.param(ChatMemory.CONVERSATION_ID,
                // currentUserService.getCurrentUserId())
                )
                .user(message)
                .call()
                .content();
        log.debug("Chat response received, response length={}", content != null ? content.length() : 0);
        logRagasEntry(message, content);
        return new ChatResponse(ChatRole.ASSISTANT, content);
    }

    /**
     * Appends a RAGAS-format JSON line to ragas_eval.jsonl.
     * Matches the SingleTurnSample schema from the ragas library:
     *   - user_input: the original user message
     *   - response: the LLM's answer
     *   - reference: ground truth answer (empty — fill in manually for evaluation)
     * The `retrieved_contexts` field is logged separately by the mcp_document service.
     */
    private void logRagasEntry(String question, String answer) {
        try {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("user_input", question);                 // RAGAS SingleTurnSample field
            entry.put("response", answer != null ? answer : ""); // RAGAS SingleTurnSample field
            entry.put("reference", ""); // fill in manually for RAGAS evaluation

            String json = objectMapper.writeValueAsString(entry);
            log.info("RAGAS_EVAL {}", json);

            try (FileWriter fw = new FileWriter("ragas_eval.jsonl", true)) {
                fw.write(json + "\n");
            }
        } catch (IOException e) {
            log.error("Failed to write RAGAS eval entry", e);
        } catch (Exception e) {
            log.error("Failed to serialize RAGAS eval entry", e);
        }
    }
}
