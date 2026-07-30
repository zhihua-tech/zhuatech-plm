/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.model;
import jakarta.persistence.*;
@Entity @Table(name="plm_part") public class Part extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String partNo;
    @Column(nullable=false,length=100) private String partName;
    @Column(nullable=false,length=32) private String category;
    @Column(nullable=false,length=20) private String version;
    @Column(nullable=false,length=120) private String specification;
    @Column(nullable=false,length=20) private String unit;
    @Column(nullable=false,length=24) private String status;
    protected Part(){}
    public Part(String no,String name,String category,String version,String specification,String unit,String status){this.partNo=no;this.partName=name;this.category=category;this.version=version;this.specification=specification;this.unit=unit;this.status=status;}
    public String getPartNo(){return partNo;} public String getPartName(){return partName;} public String getCategory(){return category;} public String getVersion(){return version;} public String getSpecification(){return specification;} public String getUnit(){return unit;} public String getStatus(){return status;}
}
