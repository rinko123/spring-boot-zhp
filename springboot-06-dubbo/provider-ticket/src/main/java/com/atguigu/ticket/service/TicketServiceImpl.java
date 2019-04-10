package com.atguigu.ticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(
        version = "${provider.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class TicketServiceImpl implements TicketService {

    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}
