/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class DesignMaturityService {

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result assess(Request request) {
        double approvalRate = request.approvedArtifacts() * 100.0 / request.requiredArtifacts();
        double base = approvalRate * 0.4
                + request.prototypePassRate() * 0.3
                + request.requirementsCoverageRate() * 0.3;
        int score = (int) Math.round(base);
        List<String> actions = new ArrayList<>();

        if (request.unresolvedCriticalFindings() > 0) {
            score -= Math.min(35, request.unresolvedCriticalFindings() * 12);
            actions.add("关闭关键设计评审问题并补充验证证据");
        }
        if (!request.supplierDesignFrozen()) {
            score -= 20;
            actions.add("完成供应商设计冻结和接口基线签署");
        }
        if (request.prototypePassRate() < 90) {
            actions.add("针对原型失败项建立整改与回归测试计划");
        }
        if (request.requirementsCoverageRate() < 95) {
            actions.add("补齐需求到设计与测试用例的追溯关系");
        }

        score = Math.max(0, score);
        String status = request.unresolvedCriticalFindings() > 0 || !request.supplierDesignFrozen()
                ? "BLOCK"
                : score < 85 ? "REVIEW" : "READY";
        if (actions.isEmpty()) {
            actions.add("设计成熟度满足阶段门评审要求");
        }
        return new Result(round(approvalRate), score, status, actions);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(
            @Min(1) int requiredArtifacts,
            @Min(0) int approvedArtifacts,
            @Min(0) int unresolvedCriticalFindings,
            @DecimalMin("0") @DecimalMax("100") double prototypePassRate,
            @DecimalMin("0") @DecimalMax("100") double requirementsCoverageRate,
            @NotNull Boolean supplierDesignFrozen
    ) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(double artifactApprovalRate, int maturityScore, String status, List<String> actions) {}
}
