package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import premium_pipe.exception.NotFoundException;
import premium_pipe.service.CartItemService;
import premium_pipe.service.CartService;

@RequestMapping("/api/cartItems")
@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?>addItemToCard(@RequestParam(required = false) Long cartId,
                                             @RequestParam Long productInfoId,
                                             @RequestParam Integer quantity){
        try {
            if (cartId == null) {
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId,productInfoId,quantity);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{cartId}/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try{
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cartId}/{itemId}")
    public ResponseEntity<Void> updateItemQuantity(@PathVariable("cartId") Long cartId,
                                                   @PathVariable Long itemId,
                                                   @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
