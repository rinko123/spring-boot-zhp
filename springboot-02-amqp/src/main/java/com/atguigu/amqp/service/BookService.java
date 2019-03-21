package com.atguigu.amqp.service;

import com.atguigu.amqp.bean.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    /**
     * 接收对象
     */
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book) {
        System.out.println("收到消息：" + book);
    }

    /**
     * 接收Message
     */
    @RabbitListener(queues = "atguigu")
    public void receive02(Message message) throws Exception {
        String json = new String(message.getBody());  //{"bookName":"红楼梦","author":"曹雪芹"}

        Book book = new ObjectMapper().readValue((json), Book.class);

        System.out.println(message.getBody());  //byte数组
        System.out.println(message.getMessageProperties());
    }

    /**
     * 都接收
     */
    @RabbitListener(queues = "atguigu")
    public void receive03(Message message, Book book) throws Exception {

    }

}
