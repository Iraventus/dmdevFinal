package org.example.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.goods.Accessories;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "accessories")
@Builder
@Entity
public class Producer extends BaseEntity<Long>{

    @Column(nullable = false, unique = true)
    private String name;
    private String producerInfo;
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode legalAddress;
    @OneToMany(mappedBy = "producer", cascade = CascadeType.PERSIST)
    private List<Accessories> accessories;
}
