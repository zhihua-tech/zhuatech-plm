/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="plm_bom_item") public class BomItem extends BaseEntity {
    @Column(nullable=false,length=32) private String productCode;
    @Column(length=32) private String parentPartNo;
    @Column(nullable=false,length=32) private String childPartNo;
    @Column(nullable=false) private Integer levelNo;
    @Column(nullable=false) private Double quantity;
    @Column(nullable=false,length=12) private String unit;
    @Column(nullable=false,length=20) private String effectivity;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected BomItem(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BomItem(String productCode,String parentPartNo,String childPartNo,Integer levelNo,Double quantity,String unit,String effectivity){this.productCode=productCode;this.parentPartNo=parentPartNo;this.childPartNo=childPartNo;this.levelNo=levelNo;this.quantity=quantity;this.unit=unit;this.effectivity=effectivity;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getProductCode(){return productCode;} /**
                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                         */
public String getParentPartNo(){return parentPartNo;} /**
                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                               */
public String getChildPartNo(){return childPartNo;} /**
                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                   */
public Integer getLevelNo(){return levelNo;} /**
                                                                                                                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                */
public Double getQuantity(){return quantity;} /**
                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                              */
public String getUnit(){return unit;} /**
                                                                                                                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                    */
public String getEffectivity(){return effectivity;}
}
