package org.bgs.mapper;

import org.bgs.entity.Producer;
import org.bgs.dto.ProducerCreateEditDto;
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
