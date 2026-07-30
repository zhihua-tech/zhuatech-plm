/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
@Entity @Table(name="plm_bom_item") public class BomItem extends BaseEntity {
    @Column(nullable=false,length=32) private String productCode;
    @Column(length=32) private String parentPartNo;
    @Column(nullable=false,length=32) private String childPartNo;
    @Column(nullable=false) private Integer levelNo;
    @Column(nullable=false) private Double quantity;
    @Column(nullable=false,length=12) private String unit;
    @Column(nullable=false,length=20) private String effectivity;
    protected BomItem(){}
    public BomItem(String productCode,String parentPartNo,String childPartNo,Integer levelNo,Double quantity,String unit,String effectivity){this.productCode=productCode;this.parentPartNo=parentPartNo;this.childPartNo=childPartNo;this.levelNo=levelNo;this.quantity=quantity;this.unit=unit;this.effectivity=effectivity;}
    public String getProductCode(){return productCode;} public String getParentPartNo(){return parentPartNo;} public String getChildPartNo(){return childPartNo;} public Integer getLevelNo(){return levelNo;} public Double getQuantity(){return quantity;} public String getUnit(){return unit;} public String getEffectivity(){return effectivity;}
}
