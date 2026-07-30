# PLM API

Copyright 2026 上海如静知华信息科技有限公司。

所有业务接口使用 `/api/plm` 前缀，并通过 `Authorization: Bearer <token>` 传递登录令牌。

| 方法 | 路径 | 用途 |
|---|---|---|
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/plm/dashboard` | 产品、物料、变更和文档概览 |
| GET/POST | `/api/plm/products` | 查询或创建产品 |
| GET | `/api/plm/parts` | 查询受控物料 |
| GET | `/api/plm/bom/{productCode}` | 查询指定产品的多层级 BOM |
| GET | `/api/plm/documents` | 查询技术文档 |
| GET/POST | `/api/plm/changes` | 查询或创建工程变更 |
| PATCH | `/api/plm/changes/{id}/advance` | 推进变更状态 |

统一响应格式：`{"success":true,"message":"操作成功","data":{...}}`。接口参数由 Jakarta Validation 校验，业务异常由全局异常处理器转换为可读错误响应。
