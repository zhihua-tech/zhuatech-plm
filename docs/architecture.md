# 架构说明

Copyright 2026 上海如静知华信息科技有限公司。

ZhuaTech PLM 采用前后端分离的模块化单体架构。Vue 3 同时承载桌面管理端和工程师 H5；Spring Boot 暴露认证、产品、物料、BOM、文档和工程变更 API；MySQL 保存业务数据，Flyway 管理数据库演进。

```text
Vue 管理端 / 工程师 H5
          │ HTTPS + JWT
          ▼
Spring Security → Controller → Domain Service → JPA Repository
                                               │
                                               ▼
                                      MySQL 8 + Flyway
```

核心边界：

- 产品域：产品主数据、型号、版本、生命周期阶段与发布状态。
- 物料/BOM 域：物料分类、规格、单位、多层级结构与版本有效性。
- 文档域：规格、图纸、测试及验证文件的受控版本。
- 变更域：ECR/ECN、风险、评审、实施、验证与关闭状态机。
- 身份域：管理员、产品工程师、技术评审员三级角色。

社区版以清晰、易于研究为目标。生产环境可在不改变 REST 边界的前提下，进一步拆分对象存储、全文检索、消息通知、工作流和外部系统集成。
