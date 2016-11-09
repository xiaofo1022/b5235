package com.xiaofo1022.b5235.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xiaofo1022.b5235.entity.DepartmentManage;

@Repository
public interface DepartmentManageDao extends JpaRepository<DepartmentManage, Long> {

}
