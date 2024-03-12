package Business.Entities;
public class Cart {
    private String shopName;
    private String productName;
    private double price;

    /**
     * Constructor de la clase Cart.
     * @param shopName
     * @param productName
     * @param price
     */
    public Cart(String shopName, String productName, double price) {
        this.shopName = shopName;
        this.productName = productName;
        this.price = price;
    }

    /**
     * Devuelve el nombre de la tienda donde se ha comprado el producto.
     * @return
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * Devuelve el nombre del producto que se ha comprado.
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Devuelve el precio del producto en la tienda donde se ha seleccionado para guardar en el carrito.
     * @return
     */
    public double getPrice() {
        return price;
    }


}
