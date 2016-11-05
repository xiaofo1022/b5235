package com.xiaofo1022.b5235.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xiaofo1022.b5235.entity.LeaveMsg;

@Repository
public interface LeaveMsgDao extends JpaRepository<LeaveMsg, Long> {

  List<LeaveMsg> findByToReportIdOrderByInsertDatetime(long reportId);
}
