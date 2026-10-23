# BOM用量与自引用预检

上海如静知华信息科技有限公司 · https://www.zhuatech.cn/

商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。

## 使用方式

启动原有前后端后，点击“批量数据预检 · 只读”进入 `/preflight.html`。先在原系统登录，再加载规则；读取当前登录存储，不创建演示账号。停用账号不可继续访问新预检接口。下载空模板或载入虚构示例，填写数据并选择核对日期后执行预检。表格每页50条，错误行均保留在报告中，不静默丢失。

## 规则与字段

| 字段 | 中文 | 类型 | 允许值 |
| --- | --- | --- | --- |
| line_no | BOM行号 | ID | 必填 |
| parent | 父项 | ID | 必填 |
| component | 子项 | ID | 必填 |
| quantity | 用量 | DECIMAL | 必填 |
| scrap_pct | 损耗百分比 | DECIMAL | 必填 |
| revision | 版本 | ID | 必填 |

- 父项与子项不能相同（不代表已做完整多层环检测）。

完整数值范围、小数位和联合唯一键见 `backend/src/main/resources/preflight/schema.json`。这是一份专用业务模板，不是任意CSV通用导入器。所有字段必填，表头可调整顺序但不能缺失、重复或新增。重复联合键会标记所有冲突行，而非仅最后一行。日期严格为1900–2200年的ISO日期。金额使用BigDecimal，不接受指数、负数、千分位、超出字段小数位的数值。所有数值以原始十进制字符串返回，避免浏览器精度损失。

## API

- `GET /api/preflight/schema`：已认证用户获取当前模板、规则及虚构样例。
- `POST /api/preflight/preview`：沿用原认证，请求JSON仅接受`csv`和`asOf`两项文本。示例：

```json
{
  "csv": "line_no,parent,component,quantity,scrap_pct,revision\nBOM-001,PRODUCT-01,PART-01,2,2,R1\n",
  "asOf": "2026-10-23"
}
```

返回`data.valid`、`totalRows`、`invalidRows`、`issues`（物理行、列、规则代码、级别）、`rows`（全部数据）、`safeCsv`和`issuesCsv`；`persisted`始终为false。没有问题的行导出与错误清单分开，界面明确提示导出范围。无身份返回401；请求JSON、CSV语法、表头或资源上限错误返回400；业务行错误返回200和`valid=false`，便于一次定位全批问题。报告响应设置`Cache-Control: no-store`。不会在日志中主动记录CSV内容。

## 安全与限制

- 请求最多1 MiB，CSV最多256 KiB / 500数据行 / 32列 / 单元格4000字符；支持UTF-8 BOM、CRLF、引号内逗号、双引号和换行。文件必须另存为UTF-8 CSV，不解析XLSX。
- 前端使用textContent呈现数据，导出所有字段统一进行CSV引号及`= + - @`等公式前缀转义。导出仅包含无错误行，不能把此子集误认成完整批次已导入。
- 本功能仅处理请求者提交的数据，不查询他人记录，不产生业务变更，因此没有新增业务审计事件。依赖现有登录权限体系。它不绕过原模块的写权限。
- 不查验主数据是否存在、不调用外部系统、不锁定库存/资金、不做跨批次唯一性校验。按物料配额合计、期间重叠仅在本批次内检查；正式入库仍须调用现有业务服务，在事务内重新校验。不能将此页当作完整业务流程或生产验收凭证。
- 不变更数据库或已发布Flyway脚本，不增加默认账号或固定新密码。原系统部署与历史模块限制仍需按各项目文档评估。

## 验证

后端：在`backend`执行`mvn test`，覆盖原有回归、新增CSV语法/业务规则及真实HTTP认证测试。前端：`node --test --test-reporter=tap test/*.test.*`及`npm run build`。本次批量执行结果另记录于工作区交付报告，不以本说明代替实际运行结果。
