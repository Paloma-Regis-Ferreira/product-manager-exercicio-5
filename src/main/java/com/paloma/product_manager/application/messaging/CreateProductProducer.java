package com.paloma.product_manager.application.messaging;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.model.ProductEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.paloma.product_manager.adapters.mapper.ProductMapper.convertEntityToDto;

@Component
public class CreateProductProducer {

    final RabbitTemplate template;

    public CreateProductProducer(RabbitTemplate template) {
        this.template = template;
    }

    @Value(value = "${broker.queue.create-product}")
    private String routingKey;

    public void publishProductMessage(ProductEntity entity){
        ProductDTO dto = convertEntityToDto(entity);

        template.convertAndSend("", routingKey, dto);
    }
}
