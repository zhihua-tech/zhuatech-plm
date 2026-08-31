/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EngineeringChangeReleaseServiceTest {
    private final EngineeringChangeReleaseService service = new EngineeringChangeReleaseService();

    @Test void releasesACompleteEngineeringChange() {
        var result = service.assess(new EngineeringChangeReleaseService.Request(
            "ECO-2026-001", 12, true, true, true, true, true, true, true, 0));
        assertThat(result.decision()).isEqualTo(EngineeringChangeReleaseService.Decision.RELEASE);
    }

    @Test void blocksInvalidBaselinesAndDeviations() {
        var result = service.assess(new EngineeringChangeReleaseService.Request(
            "ECO-2026-002", 4, false, false, false, true, true, false, true, 2));
        assertThat(result.decision()).isEqualTo(EngineeringChangeReleaseService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(5);
    }

    @Test void reviewsSupplierAndRollbackGaps() {
        var result = service.assess(new EngineeringChangeReleaseService.Request(
            "ECO-2026-003", 2, true, true, true, false, false, false, false, 0));
        assertThat(result.decision()).isEqualTo(EngineeringChangeReleaseService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(2);
    }
}
