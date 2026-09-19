/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.BomItem; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface BomItemRepository extends JpaRepository<BomItem,Long>{/**
                                                                        * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                        */
List<BomItem> findAllByProductCodeOrderByLevelNoAscIdAsc(String productCode);}
