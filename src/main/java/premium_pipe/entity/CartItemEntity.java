package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_info")
    private ProductInfoEntity productInfo;

    @Builder.Default
    private Integer quantity = 1;
    @Column(name = "is_ordered")
    @Builder.Default
    private Boolean isOrdered = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart")
    private CartEntity cart;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;



}
