/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.Part; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface PartRepository extends JpaRepository<Part,Long>{/**
                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                  */
List<Part> findAllByOrderByPartNoAsc(); /**
                                                                                                          * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                          */
Optional<Part> findByPartNo(String partNo);}
