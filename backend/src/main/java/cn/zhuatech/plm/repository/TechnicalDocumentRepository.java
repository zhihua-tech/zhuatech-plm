/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.TechnicalDocument; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface TechnicalDocumentRepository extends JpaRepository<TechnicalDocument,Long>{List<TechnicalDocument> findAllByOrderByDocumentNoAsc(); long countByStatus(String status);}
