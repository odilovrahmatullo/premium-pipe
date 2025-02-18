package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductInfoEntity;
import premium_pipe.model.request.OrderItem;
import premium_pipe.repository.OrderItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductInfoService productInfoService;
    private final ProductService productService;

    public void create(List<OrderItem> items) {
        for(OrderItem orderItem : items){
            ProductInfoEntity infoEntity = productInfoService.getEntity(orderItem.getId());

        }
    }
}
