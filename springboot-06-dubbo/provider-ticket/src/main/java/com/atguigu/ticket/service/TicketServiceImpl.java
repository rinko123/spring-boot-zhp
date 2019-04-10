package com.atguigu.ticket.service;

import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements TicketService {

    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}
