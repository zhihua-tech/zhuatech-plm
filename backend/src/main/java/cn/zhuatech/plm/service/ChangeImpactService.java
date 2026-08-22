/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChangeImpactService {
    public Result assess(Request request) {
        int score = Math.min(100, Math.min(30, request.affectedParts() * 2)
            + Math.min(24, request.affectedSuppliers() * 8)
            + Math.min(20, request.openOrders() * 2)
            + (request.safetyCritical() ? 30 : 0) + (request.rollbackReady() ? 0 : 20));
        String decision = score >= 75 ? "BLOCK" : score >= 40 ? "REVIEW" : "PASS";
        List<String> reviews = new ArrayList<>();
        reviews.add("工程变更委员会");
        if (request.affectedSuppliers() > 0) reviews.add("供应链评审");
        if (request.openOrders() > 0) reviews.add("生产与交付评审");
        if (request.safetyCritical()) reviews.add("质量与安全评审");
        if (!request.rollbackReady()) reviews.add("回退方案补充");
        return new Result(request.changeNo(), score, decision, reviews);
    }

    public record Request(@NotBlank String changeNo, @Min(0) int affectedParts,
                          @Min(0) int affectedSuppliers, @Min(0) int openOrders,
                          boolean safetyCritical, boolean rollbackReady) {}
    public record Result(String changeNo, int riskScore, String decision,
                         List<String> requiredReviews) {}
}
