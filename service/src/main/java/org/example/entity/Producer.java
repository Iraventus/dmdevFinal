package org.example.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.goods.Accessories;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "accessories")
@EqualsAndHashCode(callSuper = false, exclude = "accessories")
@Builder
@Entity
public class Producer extends BaseEntity<Long>{

    @Column(nullable = false)
    private String name;
    private String producerInfo;
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode legalAddress;
    @OneToMany(mappedBy = "producer", cascade = CascadeType.PERSIST)
    private List<Accessories> accessories;
}
