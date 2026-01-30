package edu.tlu.mcp_online_info.controller;

import edu.tlu.mcp_online_info.service.ToolService;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ToolService toolService;

    @GetMapping("/test")
    McpSchema.CallToolResult test(@RequestParam String id){
        return toolService.getRemainingFee(id);
    }
}
