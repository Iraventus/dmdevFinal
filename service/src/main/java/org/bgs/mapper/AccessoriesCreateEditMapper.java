package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.entity.Producer;
import org.bgs.entity.goods.Accessories;
import org.bgs.repository.ProducerRepository;
import org.bgs.dto.AccessoriesCreateEditDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccessoriesCreateEditMapper implements Mapper<AccessoriesCreateEditDto, Accessories> {

    private final ProducerRepository producerRepository;

    @Override
    public Accessories map(AccessoriesCreateEditDto object) {
        Accessories accessories = new Accessories();
        copy(object, accessories);
        return accessories;
    }

    @Override
    public Accessories map(AccessoriesCreateEditDto fromObject, Accessories toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(AccessoriesCreateEditDto object, Accessories accessories) {
        accessories.setProducer(getProducer(object.getProducerId()));
        accessories.setName(object.getName());
        accessories.setPrice(object.getPrice());
        accessories.setDescription(object.getDescription());
        accessories.setQuantity(object.getQuantity());
    }

    public Producer getProducer(Long producerId) {
        return Optional.ofNullable(producerId)
                .flatMap(producerRepository::findById)
                .orElseThrow();
    }
}
