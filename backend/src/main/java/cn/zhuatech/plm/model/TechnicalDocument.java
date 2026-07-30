/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
@Entity @Table(name="plm_technical_document") public class TechnicalDocument extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String documentNo;
    @Column(nullable=false,length=140) private String title;
    @Column(nullable=false,length=32) private String category;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=32) private String productCode;
    @Column(nullable=false,length=40) private String owner;
    @Column(nullable=false,length=24) private String status;
    protected TechnicalDocument(){}
    public TechnicalDocument(String no,String title,String category,String version,String productCode,String owner,String status){this.documentNo=no;this.title=title;this.category=category;this.version=version;this.productCode=productCode;this.owner=owner;this.status=status;}
    public String getDocumentNo(){return documentNo;} public String getTitle(){return title;} public String getCategory(){return category;} public String getVersion(){return version;} public String getProductCode(){return productCode;} public String getOwner(){return owner;} public String getStatus(){return status;}
}
