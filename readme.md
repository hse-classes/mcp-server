# mcp-server

Kleiner MCP Server (Spring Boot 4.1 / Spring AI 2.0 / Java 25), der eine
H2-Studentendatenbank als Tools anbietet. Gedacht als Demo für die Vorlesung:
Der Server weiß nichts von einem LLM, er stellt nur Tools bereit.

## Starten

```bash
./mvnw spring-boot:run
```

Der Server läuft auf `http://localhost:8080`, Transport ist Streamable HTTP
unter `/mcp`. Die H2-Console ist standardmäßig aus; mit `H2_CONSOLE_ENABLED=true` ist sie unter `/h2-console` erreichbar
(JDBC-URL `jdbc:h2:mem:studentdb`, User `sa`).

## Tools

Die Tools stehen als `@Tool`-Methoden in `StudentService` und werden in
`McpServerConfig` als `ToolCallbackProvider` veröffentlicht:

- `alleStudenten`
- `findeStudentenNachStudiengang`
- `findeStudentNachMatrikelnummer`
- `sucheStudentenNachNachname`
- `studentenStatistik`

## Ausprobieren

- Mit dem [mcp-client](https://github.com/hse-classes/mcp-client) (Chat mit Gemini).
- Mit dem MCP Inspector: `npx @modelcontextprotocol/inspector`, Transport
  "Streamable HTTP", URL `http://localhost:8080/mcp`.
- Mit jedem anderen MCP-fähigen Client (Claude Desktop, VS Code, ...) über
  dieselbe URL.

## Struktur

```
src/main/java/kmo/dev/
├── McpServerApplication.java
├── configuration/   McpServerConfig, DataInitializer (Testdaten)
├── model/           Student (JPA-Entity)
├── repository/      StudentRepository
└── service/         StudentService (die @Tool-Methoden)
```
