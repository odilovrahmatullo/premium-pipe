package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CartEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.repository.CartItemRepository;
import premium_pipe.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

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

}
