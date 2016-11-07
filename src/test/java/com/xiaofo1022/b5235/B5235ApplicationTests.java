package com.xiaofo1022.b5235;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaofo1022.b5235.dao.LeaveMsgDao;
import com.xiaofo1022.b5235.dao.SReportDao;
import com.xiaofo1022.b5235.entity.LeaveMsg;
import com.xiaofo1022.b5235.entity.SReport;
import com.xiaofo1022.b5235.model.ReportDay;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.service.HttpBaseService;
import com.xiaofo1022.b5235.service.ReportService;
import com.xiaofo1022.b5235.service.WeixinApiService;
import com.xiaofo1022.b5235.util.AppProperties;
import com.xiaofo1022.b5235.util.DateUtil;
import com.xiaofo1022.b5235.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = B5235Application.class)
public class B5235ApplicationTests {

  @Autowired
  private HttpBaseService http;
  @Autowired
  private FileUtil fileUtil;
  @Autowired
  private AppProperties appProperties;
  @Autowired
  private SReportDao reportDao;
  @Autowired
  private LeaveMsgDao leaveMsgDao;
  @Autowired
  private ReportService reportService;
  @Autowired
  private WeixinApiService weixinApi;
  
  @Test
  public void leaveMsgTest() {
    List<LeaveMsg> leaveMsgs = leaveMsgDao.findAll();
    for (LeaveMsg leaveMsg : leaveMsgs) {
      WeixinUser weixinUser = weixinApi.getWeixinUser(leaveMsg.getToUserId());
      if (weixinUser != null && leaveMsg.getToUserName() == null) {
        leaveMsg.setToUserName(weixinUser.getName());
        leaveMsgDao.save(leaveMsg);
      }
    }
  }
  
  public void utilTest() {
    Date now = new Date();
    String timeInDay = DateUtil.getTimeInDay(now);
    Assert.assertNotNull(timeInDay);
    String day = DateUtil.getDay(now);
    Assert.assertNotNull(day);
  }
  
  public void serviceTest() {
    String wxUserId = "yc@nbugs.com";
    List<ReportDay> reportDays = reportService.getReportDays(wxUserId);
    Assert.assertNotNull(reportDays);
  }
  
  public void reportTest() {
    String wxUserId = "yc@nbugs.com";
    Date today = new Date();
    List<SReport> reports = reportDao.findByOneDay(wxUserId, DateUtil.getOneDayStart(today), DateUtil.getOneDayEnd(today));
    Assert.assertNotNull(reports);
  }
  
  public void propTest() {
    System.out.println(appProperties.getAgentid_sreport());
  }
  
  public void commonTest() throws IOException {
    String downloadImageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
    String accessToken = "GxyHnnu19YY3n8p36o73C8CtUAuPwGkPhi5l0I9ceaqmhwn2fHt-6WNbrCX_4SFR";
    String imageServerId = "Vgx-2KwVW7qoBuLmTcHbuvPLfzHzVwdMzE01BHAhcp4SAcv3eYdqNRXxeCQBFhBL";
    String requestUrl = http.getRequestUrl(downloadImageUrl, accessToken, imageServerId);
    byte[] imageBytes = http.download(requestUrl);
    Assert.assertNotNull(imageBytes);
    fileUtil.saveImageFileToClasspath(imageServerId + ".jpg", imageBytes);
  }
}
