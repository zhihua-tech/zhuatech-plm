/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm;

import cn.zhuatech.plm.service.DesignMaturityService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class DesignMaturityServiceTests {
    private final DesignMaturityService service = new DesignMaturityService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksCriticalFindingAndUnfrozenSupplierDesign() {
        var result = service.assess(new DesignMaturityService.Request(20, 18, 2, 88, 92, false));
        assertEquals("BLOCK", result.status());
        assertTrue(result.maturityScore() < 70);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void marksCompleteDesignReady() {
        var result = service.assess(new DesignMaturityService.Request(20, 20, 0, 98, 100, true));
        assertEquals("READY", result.status());
        assertTrue(result.maturityScore() >= 95);
    }
}
