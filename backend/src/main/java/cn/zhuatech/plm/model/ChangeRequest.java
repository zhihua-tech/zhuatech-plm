/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.model;
import jakarta.persistence.*; import java.time.LocalDate;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
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
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected ChangeRequest(){}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ChangeRequest(String no,String title,String productCode,String type,String reason,String owner,LocalDate dueDate,String risk,String status){this.changeNo=no;this.title=title;this.productCode=productCode;this.changeType=type;this.reason=reason;this.owner=owner;this.dueDate=dueDate;this.riskLevel=risk;this.status=status;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void advance(){this.status=switch(status){case "草稿"->"评审中";case "评审中"->"待实施";case "待实施"->"验证中";case "验证中"->"已关闭";default->status;};}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getChangeNo(){return changeNo;} /**
                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                   */
public String getTitle(){return title;} /**
                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                           */
public String getProductCode(){return productCode;} /**
                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                               */
public String getChangeType(){return changeType;} /**
                                                                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                 */
public String getReason(){return reason;} /**
                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                           */
public String getOwner(){return owner;} /**
                                                                                                                                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                   */
public LocalDate getDueDate(){return dueDate;} /**
                                                                                                                                                                                                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                  */
public String getRiskLevel(){return riskLevel;} /**
                                                                                                                                                                                                                                                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                  */
public String getStatus(){return status;}
}
