/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.controller;
import cn.zhuatech.plm.common.ApiResponse; import cn.zhuatech.plm.dto.PlmDto.*; import cn.zhuatech.plm.service.PlmService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/plm") public class PlmController {
    private final PlmService service; public PlmController(PlmService service){this.service=service;}
    @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.dashboard());}
    @GetMapping("/products") public ApiResponse<List<ProductView>> products(){return ApiResponse.ok(service.products());}
    @GetMapping("/parts") public ApiResponse<List<PartView>> parts(){return ApiResponse.ok(service.parts());}
    @GetMapping("/bom/{productCode}") public ApiResponse<List<BomView>> bom(@PathVariable String productCode){return ApiResponse.ok(service.bom(productCode));}
    @GetMapping("/changes") public ApiResponse<List<ChangeView>> changes(){return ApiResponse.ok(service.changes());}
    @GetMapping("/documents") public ApiResponse<List<DocumentView>> documents(){return ApiResponse.ok(service.documents());}
    @PostMapping("/products") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER')") public ApiResponse<ProductView> createProduct(@Valid @RequestBody CreateProductRequest r){return ApiResponse.ok("产品创建成功",service.createProduct(r));}
    @PostMapping("/changes") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER')") public ApiResponse<ChangeView> createChange(@Valid @RequestBody CreateChangeRequest r){return ApiResponse.ok("工程变更已创建",service.createChange(r));}
    @PatchMapping("/changes/{id}/advance") @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_ENGINEER','REVIEWER')") public ApiResponse<ChangeView> advance(@PathVariable Long id){return ApiResponse.ok("工程变更状态已推进",service.advanceChange(id));}
}
