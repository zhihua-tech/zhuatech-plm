/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.controller;import cn.zhuatech.plm.common.ApiResponse;import cn.zhuatech.plm.service.BomReleaseReadinessService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/plm/insights/bom-release") public class BomReleaseReadinessController{private final BomReleaseReadinessService service;/**
                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                              */
public BomReleaseReadinessController(BomReleaseReadinessService service){this.service=service;}/**
                                                                                                                                                                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                             */
@PostMapping ApiResponse<BomReleaseReadinessService.Result> evaluate(@Valid @RequestBody BomReleaseReadinessService.Request r){return ApiResponse.ok(service.evaluate(r));}}
