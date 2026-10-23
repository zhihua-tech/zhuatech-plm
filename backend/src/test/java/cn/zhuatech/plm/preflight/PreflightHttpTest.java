/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.preflight;

import static org.junit.jupiter.api.Assertions.*;

import java.net.*;
import java.net.http.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tools.jackson.databind.ObjectMapper;

/**
 * 真实HTTP认证与校验集成测试。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PreflightHttpTest {
  @LocalServerPort int port;
  @Autowired CsvPreflight service;
  @Autowired cn.zhuatech.plm.security.JwtService jwt;
  private final HttpClient client = HttpClient.newHttpClient();
  private final ObjectMapper json = new ObjectMapper();

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private String authorization() {
    return "Bearer " + jwt.generate("admin");
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private String payload() {
    return json.writeValueAsString(
        Map.of("csv", service.schema().get("sampleCsv"), "asOf", "2026-10-23"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private HttpResponse<String> send(String path, String method, String body, String auth)
      throws Exception {
    var b =
        HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/api/preflight/" + path))
            .timeout(java.time.Duration.ofSeconds(15));
    if (auth != null) b.header("Authorization", auth);
    b.header("Content-Type", "application/json");
    b.method(
        method,
        body == null
            ? HttpRequest.BodyPublishers.noBody()
            : HttpRequest.BodyPublishers.ofString(body));
    return client.send(b.build(), HttpResponse.BodyHandlers.ofString());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void unauthenticatedPreviewDenied() throws Exception {
    assertEquals(401, send("preview", "POST", payload(), null).statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void invalidCredentialsDenied() throws Exception {
    assertEquals(401, send("preview", "POST", payload(), "Bearer invalid").statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void schemaIsNotPublic() throws Exception {
    assertEquals(401, send("schema", "GET", null, null).statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedSchemaWorks() throws Exception {
    var r = send("schema", "GET", null, authorization());
    assertEquals(200, r.statusCode(), r.body());
    assertEquals(service.schema().get("title"), json.readTree(r.body()).at("/data/title").asText());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void validBusinessPayloadWorksAndIsNotCached() throws Exception {
    var r = send("preview", "POST", payload(), authorization());
    assertEquals(200, r.statusCode(), r.body());
    assertTrue(json.readTree(r.body()).at("/data/valid").asBoolean());
    assertFalse(json.readTree(r.body()).at("/data/persisted").asBoolean());
    assertTrue(r.headers().firstValue("Cache-Control").orElse("").contains("no-store"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void malformedJsonIsBadRequest() throws Exception {
    assertEquals(400, send("preview", "POST", "{bad", authorization()).statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void duplicateFieldsAreBadRequest() throws Exception {
    var r =
        send(
            "preview",
            "POST",
            "{\"csv\":\"a\",\"csv\":\"b\",\"asOf\":\"2026-10-23\"}",
            authorization());
    assertEquals(400, r.statusCode());
    assertTrue(r.body().contains("JSON"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void rootArrayIsBadRequest() throws Exception {
    assertEquals(400, send("preview", "POST", "[]", authorization()).statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void unknownFieldsAreBadRequest() throws Exception {
    assertEquals(
        400,
        send(
                "preview",
                "POST",
                "{\"csv\":\"a\",\"asOf\":\"2026-10-23\",\"force\":true}",
                authorization())
            .statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void invalidCsvHeaderIsBadRequest() throws Exception {
    assertEquals(
        400,
        send(
                "preview",
                "POST",
                "{\"csv\":\"bad\\nvalue\",\"asOf\":\"2026-10-23\"}",
                authorization())
            .statusCode());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void rowErrorsReturnCompleteNonPersistedReport() throws Exception {
    var p = CsvPreflight.parse(service.schema().get("sampleCsv").toString());
    var raw =
        json.writeValueAsString(
            Map.of(
                "csv",
                CsvPreflight.encode(List.of(p.get(0).cells(), p.get(1).cells(), p.get(1).cells())),
                "asOf",
                "2026-10-23"));
    var r = send("preview", "POST", raw, authorization());
    assertEquals(200, r.statusCode());
    assertFalse(json.readTree(r.body()).at("/data/valid").asBoolean());
    assertEquals(2, json.readTree(r.body()).at("/data/invalidRows").asInt());
  }
}
