/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.service;import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class BomReleaseReadinessService{/**
                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                  */
public Result evaluate(Request r){double approved=r.approvedParts()*100.0/r.requiredParts();int score=(int)Math.round(approved);List<String> gaps=new ArrayList<>();if(r.obsoleteParts()>0){score-=30;gaps.add("替换失效或停产物料");}if(r.openChangeOrders()>0){score-=Math.min(25,r.openChangeOrders()*5);gaps.add("关闭未完成工程变更");}if(!r.complianceDocumentsComplete()){score-=25;gaps.add("补齐法规与材料合规文件");}if(r.costVarianceRate()>10){score-=15;gaps.add("复核 BOM 成本偏差");}if(r.obsoleteParts()>0&&r.approvedAlternates()==0)gaps.add("为失效物料批准替代料");score=Math.max(0,score);String status=score<60||r.obsoleteParts()>0&&r.approvedAlternates()==0?"BLOCK":score<90?"REVIEW":"READY";if(gaps.isEmpty())gaps.add("BOM 满足发布和采购条件");return new Result(Math.round(approved*10)/10.0,score,status,gaps);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Request(@Min(1) int requiredParts,@Min(0) int approvedParts,@Min(0) int obsoleteParts,@Min(0) int approvedAlternates,@Min(0) int openChangeOrders,@DecimalMin("0") double costVarianceRate,@NotNull Boolean complianceDocumentsComplete){}/**
                                                                                                                                                                                                                                                          * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                          */
public record Result(double partApprovalRate,int readinessScore,String status,List<String> gaps){} }
