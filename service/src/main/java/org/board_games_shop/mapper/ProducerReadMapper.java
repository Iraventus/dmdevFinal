package org.board_games_shop.mapper;

import org.board_games_shop.dto.ProducerReadDto;
import org.board_games_shop.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerReadMapper implements Mapper<Producer, ProducerReadDto>{
    @Override
    public ProducerReadDto map(Producer object) {
        return new ProducerReadDto(
                object.getId(),
                object.getName(),
                object.getProducerInfo(),
                object.getLegalAddress()
        );
    }
}
