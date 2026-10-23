/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.preflight;

import java.math.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

/**
 * 只读业务数据预检，不写库、不调用模型。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CsvPreflight {
  private final Map<String, Object> schema;
  private static final JsonMapper JSON =
      JsonMapper.builder()
          .enable(tools.jackson.databind.DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
          .enable(tools.jackson.core.StreamReadFeature.STRICT_DUPLICATE_DETECTION)
          .build();

  public record Issue(int row, String column, String code, String severity, String message) {}

  public record Record(int row, List<String> cells) {}

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @SuppressWarnings("unchecked")
  public CsvPreflight() {
    try (var in = getClass().getResourceAsStream("/preflight/schema.json")) {
      if (in == null) throw new IllegalStateException("缺少预检规则");
      schema = JSON.readValue(in, Map.class);
    } catch (java.io.IOException ex) {
      throw new IllegalStateException("读取预检规则失败", ex);
    }
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @SuppressWarnings("unchecked")
  public Map<String, Object> request(String raw) {
    if (raw == null || raw.getBytes(StandardCharsets.UTF_8).length > 1048576)
      throw new IllegalArgumentException("请求体最多1 MiB");
    Map<String, Object> input;
    try {
      input = JSON.readValue(raw, Map.class);
    } catch (tools.jackson.core.JacksonException ex) {
      throw new IllegalArgumentException("JSON格式错误、重复字段或尾随数据");
    }
    if (input == null
        || !Set.of("csv", "asOf").containsAll(input.keySet())
        || input.size() != 2
        || !(input.get("csv") instanceof String csv)
        || !(input.get("asOf") instanceof String date))
      throw new IllegalArgumentException("仅接受csv、asOf两个文本字段");
    return preview(csv, date);
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> schema() {
    return schema;
  }

  /**
   * RFC4180 风格解析，保留物理行起始位置；拒绝不闭合引号和引号后杂文。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
   */
  public static List<Record> parse(String source) {
    if (source == null
        || source.isBlank()
        || source.getBytes(StandardCharsets.UTF_8).length > 262144)
      throw new IllegalArgumentException("CSV不能为空且最多256 KiB");
    String s = source.startsWith("\uFEFF") ? source.substring(1) : source;
    List<Record> rows = new ArrayList<>();
    List<String> cells = new ArrayList<>();
    StringBuilder cell = new StringBuilder();
    boolean quoted = false, closed = false;
    int line = 1, start = 1;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '\u0000' || (Character.isISOControl(c) && c != '\t' && c != '\r' && c != '\n'))
        throw new IllegalArgumentException("CSV含不允许的控制字符");
      if (quoted) {
        if (c == '"') {
          if (i + 1 < s.length() && s.charAt(i + 1) == '"') {
            cell.append('"');
            i++;
          } else {
            quoted = false;
            closed = true;
          }
        } else if (c == '\r' || c == '\n') {
          if (c == '\r' && i + 1 < s.length() && s.charAt(i + 1) == '\n') i++;
          cell.append('\n');
          line++;
        } else cell.append(c);
      } else if (c == ',') {
        cells.add(cell.toString());
        cell.setLength(0);
        closed = false;
      } else if (c == '\r' || c == '\n') {
        if (c == '\r' && i + 1 < s.length() && s.charAt(i + 1) == '\n') i++;
        cells.add(cell.toString());
        rows.add(new Record(start, List.copyOf(cells)));
        cells.clear();
        cell.setLength(0);
        closed = false;
        start = ++line;
        if (rows.size() > 501) throw new IllegalArgumentException("CSV最多500条数据");
      } else if (c == '"') {
        if (closed || cell.length() != 0)
          throw new IllegalArgumentException("第" + line + "行引号位置无效");
        quoted = true;
      } else {
        if (closed) throw new IllegalArgumentException("第" + line + "行闭合引号后只能分隔或换行");
        cell.append(c);
      }
      if (cell.length() > 4000 || cells.size() > 32)
        throw new IllegalArgumentException("单元格最多4000字符、列数最多32");
    }
    if (quoted) throw new IllegalArgumentException("CSV引号未闭合");
    if (cell.length() > 0 || !cells.isEmpty() || closed) {
      cells.add(cell.toString());
      rows.add(new Record(start, List.copyOf(cells)));
    }
    if (rows.size() < 2 || rows.size() > 501) throw new IllegalArgumentException("需要表头和1至500条数据");
    if (rows.stream().anyMatch(r -> r.cells().size() > 32))
      throw new IllegalArgumentException("列数最多32");
    return List.copyOf(rows);
  }

  /**
   * ISO 日期严格验证，避免宽松解析日期纠正。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
   */
  static LocalDate date(String s) {
    if (!s.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
      throw new IllegalArgumentException("日期须为YYYY-MM-DD");
    try {
      var d = LocalDate.parse(s);
      if (d.getYear() < 1900 || d.getYear() > 2200)
        throw new IllegalArgumentException("日期年份1900至2200");
      return d;
    } catch (java.time.DateTimeException ex) {
      throw new IllegalArgumentException("日期不存在");
    }
  }

  /**
   * 统一十进制定点计算，拒绝指数、NaN、精度溢出。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
   */
  static BigDecimal decimal(String s, Map<String, Object> field) {
    if (!s.matches("[0-9]+(?:\\.[0-9]+)?") || s.length() > 24)
      throw new IllegalArgumentException("须为非负十进制数字，不接受指数或千分位");
    var n = new BigDecimal(s);
    int scale = ((Number) field.getOrDefault("scale", 3)).intValue();
    if (n.scale() > scale
        || n.compareTo(new BigDecimal(field.getOrDefault("max", "1000000000000").toString())) > 0
        || n.compareTo(new BigDecimal(field.getOrDefault("min", "0").toString())) < 0)
      throw new IllegalArgumentException("数值范围或小数位不符合规则");
    return n;
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @SuppressWarnings("unchecked")
  public Map<String, Object> preview(String csv, String asOf) {
    LocalDate now = date(asOf);
    var parsed = parse(csv);
    List<Map<String, Object>> fields = (List<Map<String, Object>>) schema.get("fields");
    var headers = parsed.getFirst().cells().stream().map(String::strip).toList();
    var keys = fields.stream().map(f -> f.get("key").toString()).toList();
    if (headers.size() != keys.size()
        || new HashSet<>(headers).size() != headers.size()
        || !new HashSet<>(headers).equals(new HashSet<>(keys)))
      throw new IllegalArgumentException("表头必须完整匹配模板且不能重复");
    List<Issue> issues = new ArrayList<>();
    List<Map<String, String>> data = new ArrayList<>();
    List<Integer> numbers = new ArrayList<>();
    Set<Integer> structurallyInvalid = new HashSet<>();
    for (Record record : parsed.subList(1, parsed.size())) {
      Map<String, String> row = new LinkedHashMap<>();
      numbers.add(record.row());
      data.add(row);
      if (record.cells().size() != headers.size()) {
        issues.add(new Issue(record.row(), "", "COLUMN_COUNT", "ERROR", "列数与表头不一致"));
        structurallyInvalid.add(record.row());
        continue;
      }
      for (var field : fields) {
        String key = field.get("key").toString();
        String value = record.cells().get(headers.indexOf(key)).strip();
        row.put(key, value);
        try {
          if (value.isEmpty()
              || value.length() > ((Number) field.getOrDefault("length", 200)).intValue())
            throw new IllegalArgumentException("必填项为空或长度超过限制");
          String type = field.get("type").toString();
          switch (type) {
            case "ID" -> {
              if (!value.matches("[A-Za-z0-9][A-Za-z0-9_.:/-]{0,79}"))
                throw new IllegalArgumentException("标识格式不正确（最多80位）");
            }
            case "EMAIL" -> {
              if (!value.matches("[^\\s@,]+@[^\\s@,]+\\.[^\\s@,]+"))
                throw new IllegalArgumentException("邮箱格式错误");
              row.put(key, value.toLowerCase(Locale.ROOT));
            }
            case "DATE" -> date(value);
            case "DECIMAL", "INTEGER" -> row.put(key, decimal(value, field).toPlainString());
            case "ENUM" -> {
              if (!((List<?>) field.get("options")).contains(value))
                throw new IllegalArgumentException("不在允许值内：" + field.get("options"));
            }
            case "TEXT" -> {}
            default -> throw new IllegalStateException("不支持的字段规则");
          }
          if (value.matches("(?s)^[=+@\\-\\t\\r].*"))
            issues.add(new Issue(record.row(), key, "FORMULA_ESCAPED", "WARNING", "导出时将按文本转义公式前缀"));
        } catch (IllegalArgumentException ex) {
          issues.add(new Issue(record.row(), key, "FIELD_INVALID", "ERROR", ex.getMessage()));
          structurallyInvalid.add(record.row());
        }
      }
    }
    List<List<String>> uniques = (List<List<String>>) schema.get("unique");
    for (var unique : uniques) {
      Map<List<String>, List<Integer>> seen = new HashMap<>();
      for (int i = 0; i < data.size(); i++)
        if (!structurallyInvalid.contains(numbers.get(i))) {
          var row = data.get(i);
          var value = unique.stream().map(row::get).toList();
          seen.computeIfAbsent(value, k -> new ArrayList<>()).add(numbers.get(i));
        }
      for (var group : seen.values())
        if (group.size() > 1)
          for (int row : group)
            issues.add(
                new Issue(
                    row, String.join("+", unique), "DUPLICATE", "ERROR", "批次内业务键重复，涉及行：" + group));
    }
    List<Map<String, Object>> rules = (List<Map<String, Object>>) schema.get("rules");
    for (var rule : rules) {
      String op = rule.get("op").toString();
      List<String> args = (List<String>) rule.get("args");
      if (op.equals("GROUP_SUM")) {
        Map<String, List<Integer>> groups = new LinkedHashMap<>();
        for (int i = 0; i < data.size(); i++)
          if (!structurallyInvalid.contains(numbers.get(i)))
            groups.computeIfAbsent(data.get(i).get(args.get(0)), k -> new ArrayList<>()).add(i);
        for (var group : groups.values()) {
          BigDecimal sum = BigDecimal.ZERO;
          for (int i : group) sum = sum.add(new BigDecimal(data.get(i).get(args.get(1))));
          if (sum.compareTo(new BigDecimal(args.get(2))) != 0)
            for (int i : group) addRule(issues, numbers.get(i), rule);
        }
      } else if (op.equals("NO_OVERLAP")) {
        Set<Integer> overlaps = new TreeSet<>();
        for (int i = 0; i < data.size(); i++)
          for (int j = i + 1; j < data.size(); j++) {
            if (structurallyInvalid.contains(numbers.get(i))
                || structurallyInvalid.contains(numbers.get(j))) continue;
            var a = data.get(i);
            var b = data.get(j);
            if (a.get(args.get(0)).equals(b.get(args.get(0)))
                && !date(a.get(args.get(1))).isAfter(date(b.get(args.get(2))))
                && !date(b.get(args.get(1))).isAfter(date(a.get(args.get(2))))) {
              overlaps.add(i);
              overlaps.add(j);
            }
          }
        for (int i : overlaps) addRule(issues, numbers.get(i), rule);
      } else
        for (int i = 0; i < data.size(); i++) {
          if (structurallyInvalid.contains(numbers.get(i))) continue;
          var row = data.get(i);
          boolean ok;
          switch (op) {
            case "LE" -> ok = num(row, args, 0).compareTo(num(row, args, 1)) <= 0;
            case "SUM_LE" ->
                ok = num(row, args, 0).add(num(row, args, 1)).compareTo(num(row, args, 2)) <= 0;
            case "SUM_EQ" ->
                ok = num(row, args, 0).add(num(row, args, 1)).compareTo(num(row, args, 2)) == 0;
            case "PRODUCT" ->
                ok =
                    num(row, args, 0)
                            .multiply(num(row, args, 1))
                            .setScale(2, RoundingMode.HALF_UP)
                            .compareTo(num(row, args, 2))
                        == 0;
            case "TAX_TOTAL" ->
                ok =
                    num(row, args, 0)
                            .multiply(num(row, args, 1))
                            .multiply(BigDecimal.ONE.add(num(row, args, 2).movePointLeft(2)))
                            .setScale(2, RoundingMode.HALF_UP)
                            .compareTo(num(row, args, 3))
                        == 0;
            case "DATE_LE" -> ok = !date(row.get(args.get(0))).isAfter(date(row.get(args.get(1))));
            case "IF_EQ" ->
                ok =
                    !row.get(args.get(0)).equals(args.get(1))
                        || row.get(args.get(2)).equals(args.get(3));
            case "DIFFERENT" -> ok = !row.get(args.get(0)).equals(row.get(args.get(1)));
            case "EXPIRY" -> {
              var d = date(row.get(args.get(0)));
              if (!d.isAfter(now.plusDays(30)))
                issues.add(
                    new Issue(
                        numbers.get(i),
                        args.get(0),
                        "EXPIRY",
                        "WARNING",
                        d.isBefore(now) ? "已经到期" : "将在30日内到期"));
              ok = true;
            }
            default -> throw new IllegalStateException("未知业务规则：" + op);
          }
          if (!ok) addRule(issues, numbers.get(i), rule);
        }
    }
    Set<Integer> bad = new HashSet<>();
    for (var issue : issues) if (issue.severity().equals("ERROR")) bad.add(issue.row());
    List<Map<String, Object>> rows = new ArrayList<>();
    for (int i = 0; i < data.size(); i++)
      rows.add(
          Map.of(
              "row",
              numbers.get(i),
              "valid",
              !bad.contains(numbers.get(i)),
              "values",
              data.get(i)));
    List<List<String>> exported = new ArrayList<>();
    exported.add(keys);
    for (int i = 0; i < data.size(); i++)
      if (!bad.contains(numbers.get(i))) {
        var row = data.get(i);
        exported.add(keys.stream().map(row::get).toList());
      }
    List<List<String>> errors = new ArrayList<>();
    errors.add(List.of("row", "column", "severity", "code", "message"));
    for (var e : issues)
      errors.add(List.of("" + e.row(), e.column(), e.severity(), e.code(), e.message()));
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("valid", bad.isEmpty());
    result.put("totalRows", data.size());
    result.put("validRows", data.size() - bad.size());
    result.put("invalidRows", bad.size());
    result.put("errorCount", issues.stream().filter(i -> i.severity().equals("ERROR")).count());
    result.put("warningCount", issues.stream().filter(i -> i.severity().equals("WARNING")).count());
    result.put("issues", issues);
    result.put("rows", rows);
    result.put("safeCsv", encode(exported));
    result.put("issuesCsv", encode(errors));
    result.put("asOf", asOf);
    result.put("persisted", false);
    result.put("exportScope", "仅包含无错误行；完整原始行仍在rows中，未自动入库");
    return result;
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private static BigDecimal num(Map<String, String> row, List<String> args, int i) {
    return new BigDecimal(row.get(args.get(i)));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  private static void addRule(List<Issue> issues, int row, Map<String, Object> rule) {
    issues.add(
        new Issue(
            row,
            ((List<?>) rule.get("args")).getFirst().toString(),
            rule.get("code").toString(),
            "ERROR",
            rule.get("message").toString()));
  }

  /**
   * CSV 公式注入防护：所有输出列均转义，字段转义后再进行RFC4180编码。上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
   */
  public static String encode(List<List<String>> records) {
    StringBuilder out = new StringBuilder("\uFEFF");
    for (var row : records) {
      for (int i = 0; i < row.size(); i++) {
        if (i > 0) out.append(',');
        String v = row.get(i);
        String t = v.stripLeading();
        if (!t.isEmpty()
            && ("=+-@".indexOf(t.charAt(0)) >= 0 || v.charAt(0) == '\t' || v.charAt(0) == '\r'))
          v = "'" + v;
        out.append('"').append(v.replace("\"", "\"\"")).append('"');
      }
      out.append("\r\n");
    }
    return out.toString();
  }
}
