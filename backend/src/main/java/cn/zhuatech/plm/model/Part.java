/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="plm_part") public class Part extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String partNo;
    @Column(nullable=false,length=100) private String partName;
    @Column(nullable=false,length=32) private String category;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=120) private String specification;
    @Column(nullable=false,length=20) private String unit;
    @Column(nullable=false,length=24) private String status;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected Part(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Part(String no,String name,String category,String version,String specification,String unit,String status){this.partNo=no;this.partName=name;this.category=category;this.version=version;this.specification=specification;this.unit=unit;this.status=status;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPartNo(){return partNo;} /**
                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                               */
public String getPartName(){return partName;} /**
                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                             */
public String getCategory(){return category;} /**
                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                           */
public String getVersion(){return version;} /**
                                                                                                                                                                                       * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                       */
public String getSpecification(){return specification;} /**
                                                                                                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                               */
public String getUnit(){return unit;} /**
                                                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                     */
public String getStatus(){return status;}
}
