/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="plm_product") public class Product extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String productCode;
    @Column(nullable=false,length=100) private String productName;
    @Column(nullable=false,length=60) private String model;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=24) private String lifecycleStage;
    @Column(nullable=false,length=40) private String owner;
    @Column(nullable=false,length=24) private String status;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected Product(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Product(String code,String name,String model,String version,String stage,String owner,String status){this.productCode=code;this.productName=name;this.model=model;this.version=version;this.lifecycleStage=stage;this.owner=owner;this.status=status;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void release(String version){this.version=version;this.lifecycleStage="量产";this.status="已发布";}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getProductCode(){return productCode;} /**
                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                         */
public String getProductName(){return productName;} /**
                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                             */
public String getModel(){return model;} /**
                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                     */
public String getVersion(){return version;} /**
                                                                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                 */
public String getLifecycleStage(){return lifecycleStage;} /**
                                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                           */
public String getOwner(){return owner;} /**
                                                                                                                                                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                   */
public String getStatus(){return status;}
}
