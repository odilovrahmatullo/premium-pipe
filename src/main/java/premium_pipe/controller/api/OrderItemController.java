package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.request.OrderItem;
import premium_pipe.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody List<OrderItem> items){
        if(items.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        try{
            orderItemService.create(items);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
