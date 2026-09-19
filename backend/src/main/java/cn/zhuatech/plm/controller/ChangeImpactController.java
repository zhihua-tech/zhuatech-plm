/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.controller;

import cn.zhuatech.plm.common.ApiResponse;
import cn.zhuatech.plm.service.ChangeImpactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/plm")
public class ChangeImpactController {
    private final ChangeImpactService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ChangeImpactController(ChangeImpactService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/change-impact")
    public ApiResponse<ChangeImpactService.Result> assess(@Valid @RequestBody ChangeImpactService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
