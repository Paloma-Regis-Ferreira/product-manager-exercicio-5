package com.paloma.product_manager.adapters.messaging;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateProducConsumer {

    @RabbitListener(queues = "${broker.queue.create-product}")
    public void listener(@Payload ProductDTO product){
        log.info("Product with name={} create. Message by listener", product.getName());
    }
}
