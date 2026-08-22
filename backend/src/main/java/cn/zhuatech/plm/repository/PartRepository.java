/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.plm.repository; import cn.zhuatech.plm.model.Part; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface PartRepository extends JpaRepository<Part,Long>{List<Part> findAllByOrderByPartNoAsc(); Optional<Part> findByPartNo(String partNo);}
