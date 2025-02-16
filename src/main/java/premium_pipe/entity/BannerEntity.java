package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import premium_pipe.util.HashMapConverter;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "banner")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> title;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> description;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> success;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> experience;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> projects;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> customers;

    @Column(name = "image")
    private String image;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
