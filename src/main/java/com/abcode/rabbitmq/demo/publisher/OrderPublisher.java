package com.abcode.rabbitmq.demo.publisher;

import com.abcode.rabbitmq.demo.config.MessagingConfig;
import com.abcode.rabbitmq.demo.dto.Order;
import com.abcode.rabbitmq.demo.dto.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderPublisher {

    private final RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
        // restaurantService
        // paymentService
        OrderStatus orderStatus = new OrderStatus(order, "PROCCESS", " order placed successfully in " + restaurantName);
        template.convertAndSend(MessagingConfig.ABCODE_EXCHANGE, MessagingConfig.ABCODE_ROUTING_KEY, orderStatus);
        return "Success !!";
    }
}
