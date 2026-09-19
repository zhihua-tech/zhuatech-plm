/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.controller;
import cn.zhuatech.plm.common.ApiResponse; import cn.zhuatech.plm.dto.PlmDto.*; import cn.zhuatech.plm.service.PlmService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/plm") public class PlmController {
    private final PlmService service; /**
                                       * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                       */
public PlmController(PlmService service){this.service=service;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.dashboard());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/products") public ApiResponse<List<ProductView>> products(){return ApiResponse.ok(service.products());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/parts") public ApiResponse<List<PartView>> parts(){return ApiResponse.ok(service.parts());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/bom/{productCode}") public ApiResponse<List<BomView>> bom(@PathVariable String productCode){return ApiResponse.ok(service.bom(productCode));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/changes") public ApiResponse<List<ChangeView>> changes(){return ApiResponse.ok(service.changes());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/documents") public ApiResponse<List<DocumentView>> documents(){return ApiResponse.ok(service.documents());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/products") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER')") public ApiResponse<ProductView> createProduct(@Valid @RequestBody CreateProductRequest r){return ApiResponse.ok("产品创建成功",service.createProduct(r));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/changes") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER')") public ApiResponse<ChangeView> createChange(@Valid @RequestBody CreateChangeRequest r){return ApiResponse.ok("工程变更已创建",service.createChange(r));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PatchMapping("/changes/{id}/advance") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER','REVIEWER')") public ApiResponse<ChangeView> advance(@PathVariable Long id){return ApiResponse.ok("工程变更状态已推进",service.advanceChange(id));}
}
