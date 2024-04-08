package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.AccessoriesReadDto;
import org.board_games_shop.dto.ProducerReadDto;
import org.board_games_shop.entity.goods.Accessories;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccessoriesReadMapper implements Mapper<Accessories, AccessoriesReadDto> {

    private final ProducerReadMapper producerReadMapper;

    @Override
    public AccessoriesReadDto map(Accessories object) {
        ProducerReadDto producer = Optional.ofNullable(object.getProducer())
                .map(producerReadMapper::map)
                .orElse(null);
        return new AccessoriesReadDto(
                object.getId(),
                object.getName(),
                object.getDescription(),
                object.getQuantity(),
                object.getPrice(),
                producer
        );
    }
}
