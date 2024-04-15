package org.bgs.mapper;

import org.bgs.dto.ProducerReadDto;
import org.bgs.entity.Producer;
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
