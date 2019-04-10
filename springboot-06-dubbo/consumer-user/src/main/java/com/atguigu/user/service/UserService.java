package com.atguigu.user.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ticket.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Reference(
            version = "${consumer.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880"
    )
    TicketService ticketService;

    public void hello() {
        String ticket = ticketService.getTicket();
        System.out.println("买到票了：" + ticket);
    }


}
