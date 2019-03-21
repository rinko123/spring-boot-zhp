package com.atguigu.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 自动配置
 * 1、RabbitAutoConfiguration
 * 2、有自动配置了连接工厂ConnectionFactory；
 * 3、RabbitProperties 封装了 RabbitMQ的配置
 * 4、 RabbitTemplate ：给RabbitMQ发送和接受消息；
 * 6、@EnableRabbit +  @RabbitListener 监听消息队列的内容
 */
@EnableCaching
@SpringBootApplication
public class Springboot02AmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot02AmqpApplication.class, args);
    }

}
