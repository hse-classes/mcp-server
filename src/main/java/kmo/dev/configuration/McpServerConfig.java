package kmo.dev.configuration;

import kmo.dev.service.StudentService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Konfiguration für den MCP Server - registriert die Tools
 */
@Configuration
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider studentTools(StudentService studentService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(studentService)
                .build();
    }
}