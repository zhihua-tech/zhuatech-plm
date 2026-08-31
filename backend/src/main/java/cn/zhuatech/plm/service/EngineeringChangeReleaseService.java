/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EngineeringChangeReleaseService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.impactAssessmentComplete()) blockers.add("变更影响分析未完成");
        if (!request.bomValidated()) blockers.add("变更后 BOM 未通过一致性校验");
        if (!request.drawingBaselineFrozen()) blockers.add("图纸基线尚未冻结");
        if (request.regulatoryApprovalRequired() && !request.regulatoryApproved()) blockers.add("法规审批尚未通过");
        if (request.openCriticalDeviations() > 0) blockers.add("存在未关闭的严重偏差");
        if (!blockers.isEmpty()) {
            actions.add("暂停工程变更发布，关闭阻断项并重新建立发布基线");
            return new Assessment(Decision.BLOCKED, blockers, actions);
        }
        if (!request.supplierAcknowledged() || !request.rollbackPlanDefined()) {
            if (!request.supplierAcknowledged()) actions.add("取得受影响供应商书面确认");
            if (!request.rollbackPlanDefined()) actions.add("补充变更回退方案和责任人");
            return new Assessment(Decision.REVIEW, blockers, actions);
        }
        actions.add("发布工程变更并同步 BOM、图纸和供应商生效日期");
        return new Assessment(Decision.RELEASE, blockers, actions);
    }

    public record Request(@NotBlank String changeOrderNo, @Min(1) int affectedItemCount,
                          boolean impactAssessmentComplete, boolean bomValidated,
                          boolean drawingBaselineFrozen, boolean supplierAcknowledged,
                          boolean regulatoryApprovalRequired, boolean regulatoryApproved,
                          boolean rollbackPlanDefined, @Min(0) int openCriticalDeviations) {}
    public record Assessment(Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { RELEASE, REVIEW, BLOCKED }
}
