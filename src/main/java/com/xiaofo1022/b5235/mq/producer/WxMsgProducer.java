package com.xiaofo1022.b5235.mq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.xiaofo1022.b5235.mq.config.MQConfig;
import com.xiaofo1022.b5235.mq.core.MQCore;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author yuchao
 * @since 2018-08-15 上午11:07
 */
@Slf4j
public class WxMsgProducer {

  public void initMQBaseConfig() throws Exception {
    Connection connection = MQCore.getConnection();
    try (Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(MQConfig.WX_EXCHANGE_NAME, "direct", true, false, null);
      channel.queueDeclare(MQConfig.WX_QUEUE_NAME, true, false, false, null);
      channel.queueBind(MQConfig.WX_QUEUE_NAME, MQConfig.WX_EXCHANGE_NAME, MQConfig.WX_ROUTING_KEY);
    }
  }

  public boolean sendSimpleMsg(String msgBody) throws Exception {
    Connection connection = MQCore.getConnection();
    try (Channel channel = connection.createChannel()) {
      channel.basicPublish(MQConfig.WX_EXCHANGE_NAME, MQConfig.WX_ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msgBody.getBytes());
    }
    return true;
  }

  public static void main(String[] args) throws Exception {
    WxMsgProducer msgProducer = new WxMsgProducer();
    msgProducer.initMQBaseConfig();
    int msgCount = 0;
    while (true) {
      String msgBody = "Msg from b5235 [" + (msgCount++) + "]";
      log.info("Send msg to mq: " + msgBody);
      msgProducer.sendSimpleMsg(msgBody);
      TimeUnit.SECONDS.sleep(1);
    }
  }
}
