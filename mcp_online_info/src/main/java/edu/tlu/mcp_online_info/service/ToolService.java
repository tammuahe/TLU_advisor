package edu.tlu.mcp_online_info.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RemainingFeeResponse;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ObjectMapper objectMapper;

    @McpTool(name = "getRemainingFee", description = "get student remaining fee that has to be paid to the university.")
    //TODO: add exeption handling, add input validation
    public McpSchema.CallToolResult getRemainingFee(@McpToolParam(description = "Student ID. Format: aXXXXX.") String studentId) {
        try {
            Document document = Jsoup.connect("https://e-bills.vn/pay/thanglong?customer=" + studentId).get();
            String name = document.select("#hoten").attr("value");

            if (name.isBlank()) {
                return McpSchema.CallToolResult.builder().isError(true).addTextContent("Student " + studentId + " not found").build();
            }
            Double amount = Double.valueOf(document.select("#totalAmount").text());
            System.out.println("amount: " + amount + "\n student ID: " + studentId);
            RemainingFeeResponse remainingFeeResponse = RemainingFeeResponse.of(studentId, name, amount);
            return McpSchema.CallToolResult.builder().addTextContent(objectMapper.writeValueAsString(remainingFeeResponse)).structuredContent(remainingFeeResponse).build();
        } catch (IOException e) {
            return McpSchema.CallToolResult.builder().isError(true).addTextContent("Error getting remaining fee: " + e.getMessage()).build();
        }
    }
}
