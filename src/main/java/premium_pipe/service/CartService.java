package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CartEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.repository.CartItemRepository;
import premium_pipe.repository.CartRepository;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong();


    public CartEntity getCart(final Long id){
        CartEntity cart = cartRepository.findById(id).orElseThrow(()-> new NotFoundException("Cart not found"));
        cart.updateTotalProducts();
        return cartRepository.save(cart);
    }

    public void clearCart(final Long id){
        CartEntity cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    public Long initializeNewCart(){
        CartEntity cart = new CartEntity();
        Long newCartId = cartIdGenerator.incrementAndGet();
        cart.setId(newCartId);
        return cartRepository.save(cart).getId();
    }

}
