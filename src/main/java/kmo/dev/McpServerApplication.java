package kmo.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MCP Server Anwendung die Tools für Studenten-Verwaltung bereitstellt.
 * 
 * Diese Anwendung stellt über das Model Context Protocol (MCP) Tools zur Verfügung,
 * die von MCP Clients verwendet werden können.
 */
@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }
}