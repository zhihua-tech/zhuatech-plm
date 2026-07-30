/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.ChangeRequest; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ChangeRequestRepository extends JpaRepository<ChangeRequest,Long>{List<ChangeRequest> findAllByOrderByDueDateAsc(); long countByStatusNot(String status); long countByRiskLevelAndStatusNot(String risk,String status);}
