package com.xiaofo1022.b5235.mq.config;

/**
 * @author yuchao
 * @since 2018-08-15 上午10:43
 */
public class MQConfig {

  public static final String IP_ADDRESS = "127.0.0.1";
  public static final String USERNAME = "root";
  public static final String PASSWORD = "root123";
  public static final int PORT = 5672;

  public static final String WX_EXCHANGE_NAME = "exchange_wx";
  public static final String WX_ROUTING_KEY = "routing_key_wx";
  public static final String WX_QUEUE_NAME = "queue_wx";
}
