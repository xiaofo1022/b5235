package com.xiaofo1022.b5235;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaofo1022.b5235.service.HttpBaseService;
import com.xiaofo1022.b5235.util.AppProperties;
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
  
  @Test
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
