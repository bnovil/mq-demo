package com.my;

import com.my.constant.ServerAddr;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * Created by lzq on 2018/3/5.
 *
 * @Description
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {


        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("one");

        /*
         * set name server
         * consumer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         */
        consumer.setNamesrvAddr(ServerAddr.NAMESERVERADDR);

        consumer.setVipChannelEnabled(false);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest", "*");

        consumer.registerMessageListener(
                (MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
