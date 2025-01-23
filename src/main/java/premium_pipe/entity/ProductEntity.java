package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import premium_pipe.util.HashMapConverter;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> name;

    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    @Column(columnDefinition = "TEXT")
    private Map<String, String> description;
    private Integer code;
    private String diameter;
    private String packages;
    private String unit;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
