/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.controller;

import cn.zhuatech.plm.common.ApiResponse;
import cn.zhuatech.plm.service.EngineeringChangeReleaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/plm")
public class EngineeringChangeReleaseController {
    private final EngineeringChangeReleaseService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public EngineeringChangeReleaseController(EngineeringChangeReleaseService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/engineering-change-release")
    public ApiResponse<EngineeringChangeReleaseService.Assessment> assess(
        @Valid @RequestBody EngineeringChangeReleaseService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
