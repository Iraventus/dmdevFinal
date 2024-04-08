package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.AccessoriesCreateEditDto;
import org.board_games_shop.entity.Producer;
import org.board_games_shop.entity.goods.Accessories;
import org.board_games_shop.repository.ProducerRepository;
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
                .orElse(null);
    }
}
