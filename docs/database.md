# 数据库设计

Copyright 2026 上海如静知华信息科技有限公司。

| 表 | 说明 | 关键约束 |
|---|---|---|
| `sys_user` | 系统用户与角色 | `username` 唯一 |
| `plm_product` | 产品主数据与生命周期 | `product_code` 唯一 |
| `plm_part` | 受控物料 | `part_no` 唯一 |
| `plm_bom_item` | 产品 BOM 父子关系 | 产品编码、父/子物料、层级、用量 |
| `plm_change_request` | ECR/ECN 工程变更 | `change_no` 唯一 |
| `plm_technical_document` | 产品图文档及版本 | `document_no` 唯一 |

首次启动由 `V1__init.sql` 建表。开发测试使用 H2 内存数据库并由 Hibernate 创建结构；生产环境使用 MySQL 8，并应为应用账号配置最小权限、备份和审计策略。
