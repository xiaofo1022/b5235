package com.xiaofo1022.b5235.cache;

import com.xiaofo1022.b5235.dao.SReportDao;
import com.xiaofo1022.b5235.model.WeixinDepartment;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.service.WeixinApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Cache for Weixin
 *
 * @author yuchao
 * @since 2017-01-24 下午5:59
 */
@Component
public class WeixinCache {

  private static final Logger logger = LoggerFactory.getLogger(WeixinCache.class);

  @Autowired
  private WeixinApiService weixinApi;
  @Autowired
  private SReportDao reportDao;

  @Cacheable(value = "weixinuser",keyGenerator = "redisKeyGenerator")
  public WeixinUser getWeixinUser(String wxUserId) {
    WeixinUser weixinUser = weixinApi.getWeixinUser(wxUserId);
    logger.info("getWeixinUser not hit in cache.");
    return weixinUser;
  }

  @Cacheable(value = "weixindepartment",keyGenerator = "redisKeyGenerator")
  public WeixinDepartment getWeixinDepartment(Long departmentId) {
    WeixinDepartment department = weixinApi.getWeixinDepartment(departmentId);
    logger.info("getWeixinDepartment not hit in cache.");
    return department;
  }

  @Cacheable(value = "reportcount",keyGenerator = "redisKeyGenerator")
  public int getReportCount(String wxUserId) {
    int reportCount = reportDao.findByWxUserId(wxUserId).size();
    logger.info("getReportCount not hit in cache.");
    return reportCount;
  }
}
