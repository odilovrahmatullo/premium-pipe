package premium_pipe.model.request;

import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Integer quantity;
    private Boolean isOrdered;
}
