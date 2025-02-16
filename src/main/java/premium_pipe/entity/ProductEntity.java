package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import premium_pipe.util.HashMapConverter;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private CategoryEntity category;

    @Column(name = "slug")
    private String slug;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFileEntity> files = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductInfoEntity> infos = new ArrayList<>();

}
