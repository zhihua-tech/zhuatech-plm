/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.dto;
import cn.zhuatech.plm.model.*; import jakarta.validation.constraints.*; import java.time.LocalDate; import java.util.List;
public final class PlmDto {
    private PlmDto(){}
    public record ProductView(Long id,String productCode,String productName,String model,String version,String lifecycleStage,String owner,String status){public static ProductView from(Product p){return new ProductView(p.getId(),p.getProductCode(),p.getProductName(),p.getModel(),p.getVersion(),p.getLifecycleStage(),p.getOwner(),p.getStatus());}}
    public record PartView(Long id,String partNo,String partName,String category,String version,String specification,String unit,String status){public static PartView from(Part p){return new PartView(p.getId(),p.getPartNo(),p.getPartName(),p.getCategory(),p.getVersion(),p.getSpecification(),p.getUnit(),p.getStatus());}}
    public record BomView(Long id,String productCode,String parentPartNo,String childPartNo,String childPartName,Integer levelNo,Double quantity,String unit,String effectivity){}
    public record ChangeView(Long id,String changeNo,String title,String productCode,String changeType,String reason,String owner,LocalDate dueDate,String riskLevel,String status){public static ChangeView from(ChangeRequest c){return new ChangeView(c.getId(),c.getChangeNo(),c.getTitle(),c.getProductCode(),c.getChangeType(),c.getReason(),c.getOwner(),c.getDueDate(),c.getRiskLevel(),c.getStatus());}}
    public record DocumentView(Long id,String documentNo,String title,String category,String version,String productCode,String owner,String status){public static DocumentView from(TechnicalDocument d){return new DocumentView(d.getId(),d.getDocumentNo(),d.getTitle(),d.getCategory(),d.getVersion(),d.getProductCode(),d.getOwner(),d.getStatus());}}
    public record Dashboard(long productCount,long partCount,long activeChanges,long highRiskChanges,long releasedDocuments,long massProductionProducts,List<ProductView> products,List<ChangeView> urgentChanges,List<DocumentView> recentDocuments){}
    public record CreateProductRequest(@NotBlank String productCode,@NotBlank String productName,@NotBlank String model,@NotBlank String version,@NotBlank String lifecycleStage,@NotBlank String owner){}
    public record CreateChangeRequest(@NotBlank String title,@NotBlank String productCode,@NotBlank String changeType,@NotBlank @Size(max=300) String reason,@NotBlank String owner,@NotNull @FutureOrPresent LocalDate dueDate,@Pattern(regexp="低|中|高") String riskLevel){}
}
