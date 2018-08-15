package com.xiaofo1022.b5235.mq.core;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaofo1022.b5235.mq.config.MQConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yuchao
 * @since 2018-08-15 上午10:53
 */
public class MQCore {

  private static Connection connection;

  public static Connection getConnection() throws IOException, TimeoutException {
    if (connection == null) {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(MQConfig.IP_ADDRESS);
      factory.setPort(MQConfig.PORT);
      factory.setUsername(MQConfig.USERNAME);
      factory.setPassword(MQConfig.PASSWORD);
      connection = factory.newConnection();
    }
    return connection;
  }
}
