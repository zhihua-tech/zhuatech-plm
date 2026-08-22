/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.Product; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ProductRepository extends JpaRepository<Product,Long>{List<Product> findAllByOrderByProductCodeAsc(); long countByLifecycleStage(String stage);}
