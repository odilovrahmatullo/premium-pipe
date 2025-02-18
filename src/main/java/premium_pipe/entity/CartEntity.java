package premium_pipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private Integer totalProducts = 0;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItemEntity> cartItems;

    public void addItem(CartItemEntity item) {
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalProducts();
    }

    public void removeItem(CartItemEntity item) {
        this.cartItems.remove(item);
        item.setCart(null);
        updateTotalProducts();
    }

    public void updateTotalProducts() {
        this.totalProducts = cartItems.stream()
                .map(CartItemEntity::getQuantity)
                .reduce(0, Integer::sum);
    }
}
