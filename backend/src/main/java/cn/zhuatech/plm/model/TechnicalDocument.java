/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="plm_technical_document") public class TechnicalDocument extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String documentNo;
    @Column(nullable=false,length=140) private String title;
    @Column(nullable=false,length=32) private String category;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=32) private String productCode;
    @Column(nullable=false,length=40) private String owner;
    @Column(nullable=false,length=24) private String status;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected TechnicalDocument(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public TechnicalDocument(String no,String title,String category,String version,String productCode,String owner,String status){this.documentNo=no;this.title=title;this.category=category;this.version=version;this.productCode=productCode;this.owner=owner;this.status=status;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getDocumentNo(){return documentNo;} /**
                                                       * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                       */
public String getTitle(){return title;} /**
                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                               */
public String getCategory(){return category;} /**
                                                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                             */
public String getVersion(){return version;} /**
                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                         */
public String getProductCode(){return productCode;} /**
                                                                                                                                                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                             */
public String getOwner(){return owner;} /**
                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                     */
public String getStatus(){return status;}
}
