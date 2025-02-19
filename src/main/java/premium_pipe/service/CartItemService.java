package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CartEntity;
import premium_pipe.entity.CartItemEntity;
import premium_pipe.entity.ProductInfoEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.repository.CartItemRepository;
import premium_pipe.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductInfoService productInfoService;

    public void addItemToCart(final Long cartId,Long productInfoId,Integer quantity){
        CartEntity cart = cartService.getCart(cartId);
        ProductInfoEntity productInfo = productInfoService.getEntity(productInfoId);
        CartItemEntity cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProductInfo().getId().equals(productInfoId))
                .findFirst().orElse(new CartItemEntity());
        if(cartItem.getId()==null){
            cartItem.setProductInfo(productInfo);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }


    public void removeItemFromCart(Long cartId,Long cartItemId){
        CartEntity cart = cartService.getCart(cartId);
        CartItemEntity itemRemove = getCartItem(cartId,cartItemId);
        cart.removeItem(itemRemove);
        cartRepository.save(cart);
        cart.updateTotalProducts();
    }

    public void updateItemQuantity(Long cartId,Long cartItemId,int quantity){
        CartEntity cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProductInfo().getId().equals(cartItemId))
                .findFirst()
                .ifPresent(item ->
                        item.setQuantity(quantity));
        cart.updateTotalProducts();
        cartRepository.save(cart);

    }

    public CartItemEntity getCartItem(final Long cartId,Long cartItemId){
        CartEntity cart = cartService.getCart(cartId);
        return cart.getCartItems().stream().filter(item ->
                item.getProductInfo().getId().equals(cartItemId)).findFirst().orElseThrow(() ->
                new NotFoundException("Product not found"));
    }

}
