package org.board_games_shop.mapper;

import org.board_games_shop.dto.ProducerCreateEditDto;
import org.board_games_shop.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerCreateEditMapper implements Mapper<ProducerCreateEditDto, Producer> {
    @Override
    public Producer map(ProducerCreateEditDto object) {
        Producer producer = new Producer();
        copy(object, producer);
        return producer;
    }

    @Override
    public Producer map(ProducerCreateEditDto fromObject, Producer toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ProducerCreateEditDto object, Producer producer) {
        producer.setName(object.getName());
        producer.setProducerInfo(object.getProducerInfo());
        producer.setLegalAddress(object.getLegalAddress());
    }
}
