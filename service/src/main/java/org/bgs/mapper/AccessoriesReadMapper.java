package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.ProducerReadDto;
import org.bgs.entity.goods.Accessories;
import org.bgs.dto.AccessoriesReadDto;
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
