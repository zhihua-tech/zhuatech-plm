/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.preflight;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

/**
 * CSV语法、业务规则、安全边界回归。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class PreflightServiceTest {
  private final CsvPreflight service = new CsvPreflight();
  private final ObjectMapper json = new ObjectMapper();
  private final String asOf = "2026-10-23";

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @SuppressWarnings("unchecked")
  private String sample() {
    return service.schema().get("sampleCsv").toString();
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private Map<String, Object> run(String csv) {
    return service.preview(csv, asOf);
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @SuppressWarnings("unchecked")
  private List<CsvPreflight.Issue> issues(Map<String, Object> result) {
    return (List<CsvPreflight.Issue>) result.get("issues");
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void validDomainBatchDoesNotPersist() {
    var r = run(sample());
    assertEquals(true, r.get("valid"));
    assertEquals(false, r.get("persisted"));
    assertEquals(1, r.get("validRows"));
    assertEquals(0, r.get("invalidRows"));
    assertTrue(issues(r).stream().noneMatch(i -> i.severity().equals("ERROR")));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  @SuppressWarnings("unchecked")
  void domainViolationsArePreciselyIdentified() {
    for (var rule : (List<Map<String, Object>>) service.schema().get("rules"))
      if (rule.get("bad") instanceof Map<?, ?> bad) {
        var records = CsvPreflight.parse(sample());
        var h = records.getFirst().cells();
        var row = new ArrayList<>(records.get(1).cells());
        bad.forEach((k, v) -> row.set(h.indexOf(k.toString()), v.toString()));
        var r = run(CsvPreflight.encode(List.of(h, row)));
        assertEquals(false, r.get("valid"), rule.toString());
        assertTrue(
            issues(r).stream().anyMatch(i -> i.code().equals(rule.get("code")) && i.row() == 2),
            rule.toString());
        assertEquals(0, r.get("validRows"));
      }
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void duplicateBusinessKeysMarkEveryAffectedRow() {
    var r = CsvPreflight.parse(sample());
    var result =
        run(CsvPreflight.encode(List.of(r.get(0).cells(), r.get(1).cells(), r.get(1).cells())));
    assertEquals(2, result.get("invalidRows"));
    assertTrue(issues(result).stream().anyMatch(i -> i.code().equals("DUPLICATE") && i.row() == 2));
    assertTrue(issues(result).stream().anyMatch(i -> i.code().equals("DUPLICATE") && i.row() == 3));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void reorderedHeadersAreSupported() {
    var r = CsvPreflight.parse(sample());
    var h = new ArrayList<>(r.get(0).cells());
    var v = new ArrayList<>(r.get(1).cells());
    Collections.reverse(h);
    Collections.reverse(v);
    assertEquals(true, run(CsvPreflight.encode(List.of(h, v))).get("valid"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void utf8BomAndCrLfAreSupported() {
    assertEquals(true, run("\uFEFF" + sample().replace("\n", "\r\n")).get("valid"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void wrongColumnCountPreservesInvalidRow() {
    var p = CsvPreflight.parse(sample());
    var r = run(CsvPreflight.encode(List.of(p.get(0).cells(), List.of("missing"))));
    assertEquals(1, r.get("totalRows"));
    assertEquals(1, r.get("invalidRows"));
    assertEquals("COLUMN_COUNT", issues(r).getFirst().code());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void emptyRequiredFieldIsReported() {
    var p = CsvPreflight.parse(sample());
    var v = new ArrayList<>(p.get(1).cells());
    v.set(0, "");
    var r = run(CsvPreflight.encode(List.of(p.get(0).cells(), v)));
    assertEquals(1, r.get("invalidRows"));
    assertEquals("FIELD_INVALID", issues(r).getFirst().code());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void headerMismatchFailsWholeRequest() {
    assertThrows(IllegalArgumentException.class, () -> run("wrong\nvalue"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void duplicateHeadersFailWholeRequest() {
    var p = CsvPreflight.parse(sample());
    var h = new ArrayList<>(p.get(0).cells());
    h.set(1, h.getFirst());
    assertThrows(
        IllegalArgumentException.class,
        () -> run(CsvPreflight.encode(List.of(h, p.get(1).cells()))));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void parserSupportsQuotedCommaEscapedQuoteAndNewline() {
    var p = CsvPreflight.parse("a,b\r\n\"x,\"\"y\"\"\nnext\",z\r\n");
    assertEquals("x,\"y\"\nnext", p.get(1).cells().getFirst());
    assertEquals(2, p.get(1).row());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void physicalLineNumbersSurviveMultilineCells() {
    var p = CsvPreflight.parse("a\n\"one\ntwo\"\nthree");
    assertEquals(4, p.get(2).row());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void unclosedQuoteRejected() {
    assertThrows(IllegalArgumentException.class, () -> CsvPreflight.parse("a\n\"bad"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void strayQuoteRejected() {
    assertThrows(IllegalArgumentException.class, () -> CsvPreflight.parse("a\nva\"lue"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void garbageAfterClosedQuoteRejected() {
    assertThrows(IllegalArgumentException.class, () -> CsvPreflight.parse("a\n\"v\"x"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void controlCharactersRejected() {
    assertThrows(IllegalArgumentException.class, () -> CsvPreflight.parse("a\nhello\u0000"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void rowLimitRejects501() {
    assertThrows(
        IllegalArgumentException.class, () -> CsvPreflight.parse("a\n" + "value\n".repeat(501)));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void rowLimitAllows500() {
    assertEquals(501, CsvPreflight.parse("a\n" + "value\n".repeat(500)).size());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void byteBudgetCountsUtf8() {
    assertThrows(
        IllegalArgumentException.class, () -> CsvPreflight.parse("a\n" + "中".repeat(90000)));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void cellBudgetIsBounded() {
    assertThrows(
        IllegalArgumentException.class, () -> CsvPreflight.parse("a\n" + "x".repeat(4001)));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void formulasAndWhitespacePrefixesAreExportedAsText() {
    String r =
        CsvPreflight.encode(
            List.of(List.of("=1+1", " +SUM(1)", "-1", "@cmd", "\t=1", "normal", "a,\"b")));
    assertTrue(r.contains("\"'=1+1\""));
    assertTrue(r.contains("\"' +SUM(1)\""));
    assertTrue(r.contains("\"'-1\""));
    assertTrue(r.contains("\"'@cmd\""));
    assertTrue(r.contains("\"'\t=1\""));
    assertTrue(r.contains("\"normal\""));
    assertTrue(r.contains("\"a,\"\"b\""));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void monetaryArithmeticIsDecimalExact() {
    assertEquals("0.10", CsvPreflight.decimal("0.10", Map.of("scale", 2)).toPlainString());
    assertEquals(
        "900719925474.01",
        CsvPreflight.decimal("900719925474.01", Map.of("scale", 2)).toPlainString());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void exponentNegativeNaNAndExcessPrecisionRejected() {
    for (String s : List.of("1e3", "-1", "NaN", "1,000", "1.001", "1000000000001"))
      assertThrows(
          IllegalArgumentException.class, () -> CsvPreflight.decimal(s, Map.of("scale", 2)), s);
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void impossibleAndOutOfRangeDatesRejected() {
    for (String d : List.of("2026-02-30", "2026-2-01", "1899-01-01", "2201-01-01"))
      assertThrows(IllegalArgumentException.class, () -> CsvPreflight.date(d), d);
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void missingAsOfRejected() {
    assertThrows(IllegalArgumentException.class, () -> service.request("{\"csv\":\"a\"}"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void unknownJsonFieldsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.request("{\"csv\":\"a\",\"asOf\":\"2026-10-23\",\"admin\":true}"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void duplicateJsonFieldsRejected() {
    var ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.request("{\"csv\":\"a\",\"csv\":\"b\",\"asOf\":\"2026-10-23\"}"));
    assertTrue(ex.getMessage().contains("JSON"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void trailingJsonRejected() {
    assertThrows(IllegalArgumentException.class, () -> service.request("{} {}"));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void nonObjectJsonRejected() {
    for (String s : List.of("null", "[]", "123"))
      assertThrows(IllegalArgumentException.class, () -> service.request(s));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void jsonAndDirectPreviewAgree() {
    assertEquals(
        run(sample()),
        service.request(json.writeValueAsString(Map.of("csv", sample(), "asOf", asOf))));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void noDataIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> run("header\n"));
  }
}
