package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @Column(name = "code_value")
    private Integer codeValue;
    @Column(name = "diameter_value")
    private String diameterValue;
    @Column(name = "unit_value")
    private String unitValue;
    @Column(name = "package_value")
    private String packageValue;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
