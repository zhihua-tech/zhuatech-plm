# 企业级工程变更发布控制

[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)为 PLM 开源版增加工程变更发布阶段门。

`POST /api/enterprise/plm/engineering-change-release` 对影响分析、BOM 一致性、图纸基线、供应商确认、法规审批、回退计划和严重偏差进行联合判断，输出 `RELEASE / REVIEW / BLOCKED`。

该能力可作为 ECO/ECN 工作流的发布节点。企业落地时建议接入物料主数据、电子签审、供应商门户和配置基线仓库，确保生效日期与下游执行一致。
