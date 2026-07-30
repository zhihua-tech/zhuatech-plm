/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.BomItem; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BomItemRepository extends JpaRepository<BomItem,Long>{List<BomItem> findAllByProductCodeOrderByLevelNoAscIdAsc(String productCode);}
