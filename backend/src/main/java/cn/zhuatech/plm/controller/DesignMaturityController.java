/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.plm.controller;

import cn.zhuatech.plm.common.ApiResponse;
import cn.zhuatech.plm.service.DesignMaturityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plm/insights/design-maturity")
public class DesignMaturityController {
    private final DesignMaturityService service;

    public DesignMaturityController(DesignMaturityService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<DesignMaturityService.Result> assess(
            @Valid @RequestBody DesignMaturityService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
