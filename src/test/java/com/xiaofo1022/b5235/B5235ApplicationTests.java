package com.xiaofo1022.b5235;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaofo1022.b5235.dao.SReportDao;
import com.xiaofo1022.b5235.entity.SReport;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = B5235Application.class)
public class B5235ApplicationTests {

  @Autowired
  private SReportDao sReportDao;
  
  @Test
  public void daoTest() {
    SReport report = sReportDao.findOne(1L);
    Assert.assertNotNull(report);
  }
}
