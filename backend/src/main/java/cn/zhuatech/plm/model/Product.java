/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
@Entity @Table(name="plm_product") public class Product extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String productCode;
    @Column(nullable=false,length=100) private String productName;
    @Column(nullable=false,length=60) private String model;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=24) private String lifecycleStage;
    @Column(nullable=false,length=40) private String owner;
    @Column(nullable=false,length=24) private String status;
    protected Product(){}
    public Product(String code,String name,String model,String version,String stage,String owner,String status){this.productCode=code;this.productName=name;this.model=model;this.version=version;this.lifecycleStage=stage;this.owner=owner;this.status=status;}
    public void release(String version){this.version=version;this.lifecycleStage="量产";this.status="已发布";}
    public String getProductCode(){return productCode;} public String getProductName(){return productName;} public String getModel(){return model;} public String getVersion(){return version;} public String getLifecycleStage(){return lifecycleStage;} public String getOwner(){return owner;} public String getStatus(){return status;}
}
