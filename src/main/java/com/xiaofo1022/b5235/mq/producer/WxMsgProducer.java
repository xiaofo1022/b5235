package com.xiaofo1022.b5235.mq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.xiaofo1022.b5235.mq.config.MQConfig;
import com.xiaofo1022.b5235.mq.core.MQCore;

import java.util.concurrent.TimeUnit;

/**
 * @author yuchao
 * @since 2018-08-15 上午11:07
 */
public class WxMsgProducer {

  public void initMQBaseConfig() throws Exception {
    try (
        Connection connection = MQCore.getConnection();
        Channel channel = connection.createChannel()
    ) {
      channel.exchangeDeclare(MQConfig.WX_EXCHANGE_NAME, "direct", true, false, null);
      channel.queueBind(MQConfig.WX_QUEUE_NAME, MQConfig.WX_EXCHANGE_NAME, MQConfig.WX_ROUTING_KEY);
    }
  }

  public boolean sendSimpleMsg(String msgBody) throws Exception {
    try (
        Connection connection = MQCore.getConnection();
        Channel channel = connection.createChannel()
    ) {
      channel.basicPublish(MQConfig.WX_EXCHANGE_NAME, MQConfig.WX_ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msgBody.getBytes());
    }
    return true;
  }

  public static void main(String[] args) throws Exception {
    WxMsgProducer msgProducer = new WxMsgProducer();
    msgProducer.initMQBaseConfig();
    int msgCount = 0;
    while (true) {
      msgProducer.sendSimpleMsg("Msg from b5235 [" + (msgCount++) + "]");
      TimeUnit.SECONDS.sleep(1);
    }
  }
}
