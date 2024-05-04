package org.bgs.entity.goods;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.bgs.entity.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name", callSuper = false)
@ToString(of = "name")
@Entity
@Table(name = "goods")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Goods extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;
    private String description;
    @Min(0)
    private Integer quantity;
    @Min(0)
    private Integer price;

}
