package com.xiaofo1022.b5235.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xiaofo1022.b5235.entity.SReport;

@Repository
public interface SReportDao extends JpaRepository<SReport, Long> {

  @Query("from SReport where wxUserId = ?1 and (reportDate between ?2 and ?3) order by reportDate")
  List<SReport> findByOndDay(String wxUserId, Date startDate, Date endDate);
}
