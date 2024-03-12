package Business;

import Business.Entities.Cart;
import Persistence.CartDAO;
import java.util.ArrayList;
public class CartManager {
    private static CartDAO cartDAO;

    /**
     * Constructor de la clase que instancia el DAO respectivo.
     * @param cartDAO
     */
    public CartManager(CartDAO cartDAO){
        this.cartDAO = cartDAO;
    }

    /**
     * Crea un objeto para guardar en el carrito y usa el cartDAO para añadirlo al fichero JSON.
     * @param shopName
     * @param productName
     * @param price
     * @return
     */
    public boolean addCart(String shopName, String productName, double price){
        Cart cart = new Cart(shopName, productName, price);
        ArrayList<Cart> carts = cartDAO.getData();
        carts.add(cart);
        return cartDAO.addProductstoCart(carts);
    }

    /**
     * Recoge la informacion del fichero a través del cartDAO.
     * @return
     */
    public ArrayList<Cart> getCartData() {
        return cartDAO.getData();
    }

    /**
     * Recoge todos los productos del carrito, suma sus precios, y devuelve el total.
     * @return
     */
    public double getTotal(){
        ArrayList<Cart> carts = cartDAO.getData();
        int size = carts.size();
        double totalPrice = 0;
        for (int i = 0; i < size; i++) {
            totalPrice += carts.get(i).getPrice();
        }
        return totalPrice;
    }

    /**
     * Llama al cartDAO para eliminar la información del JSON donde se guardan los productos del carrito.
     */
    public void clearJsonFile(){
        cartDAO.clearCart();
    }
}
