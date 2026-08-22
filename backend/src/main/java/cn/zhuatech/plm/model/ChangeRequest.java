/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="plm_change_request") public class ChangeRequest extends BaseEntity {
    @Column(nullable=false,unique=true,length=32) private String changeNo;
    @Column(nullable=false,length=140) private String title;
    @Column(nullable=false,length=32) private String productCode;
    @Column(nullable=false,length=20) private String changeType;
    @Column(nullable=false,length=300) private String reason;
    @Column(nullable=false,length=40) private String owner;
    @Column(nullable=false) private LocalDate dueDate;
    @Column(nullable=false,length=16) private String riskLevel;
    @Column(nullable=false,length=24) private String status;
    protected ChangeRequest(){}
    public ChangeRequest(String no,String title,String productCode,String type,String reason,String owner,LocalDate dueDate,String risk,String status){this.changeNo=no;this.title=title;this.productCode=productCode;this.changeType=type;this.reason=reason;this.owner=owner;this.dueDate=dueDate;this.riskLevel=risk;this.status=status;}
    public void advance(){this.status=switch(status){case "草稿"->"评审中";case "评审中"->"待实施";case "待实施"->"验证中";case "验证中"->"已关闭";default->status;};}
    public String getChangeNo(){return changeNo;} public String getTitle(){return title;} public String getProductCode(){return productCode;} public String getChangeType(){return changeType;} public String getReason(){return reason;} public String getOwner(){return owner;} public LocalDate getDueDate(){return dueDate;} public String getRiskLevel(){return riskLevel;} public String getStatus(){return status;}
}
