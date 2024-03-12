package Business;

import Business.Entities.Cart;
import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;
import Business.Entities.Review;
import Business.Entities.Shops.Shop;
import Business.Entities.Shops.ShopLoyality;
import Business.Entities.Shops.ShopSponsored;
import Persistence.Shops.Interfaces.DAOShopsInterface;
import Persistence.Shops.ShopDAOs.ShopAPIDAO;
import Persistence.Shops.ShopDAOs.ShopDAO;

import java.util.ArrayList;
public class ShopManager {

    private DAOShopsInterface daoShopsInterface;

    public ShopManager(String opt) {
        switch (opt){
            case "JSON": this.daoShopsInterface = new ShopDAO();break;
            case "API": this.daoShopsInterface = new ShopAPIDAO();break;
        }
    }

    public ArrayList<Shop> getData() {
        return daoShopsInterface.getData();
    }

    /**
     * Comprueba que el nombre que llega por parámetro coincide con el de alguna tienda del sistema.
     * @param name
     * @return
     */
    public boolean checkName(String name) {
        ArrayList<Shop> shops = getData();
        if (shops.isEmpty()){
            return true;
        }else{
            for (Shop shop : shops) {
                if (shop.getName().equals(name)) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean createShopA(String name, String description, int foundationYear, String businessModel) {
        ArrayList<Shop> shops = getData();
        shops.add(new Shop(name,description,foundationYear,businessModel));
        return daoShopsInterface.postData(shops);
    }

    /**
     * Crea una tienda y la añade al fichero JSON, si el proceso sale bien devuelve un valor true para informar de que ha ido bien.
     * @param name
     * @param description
     * @param foundationYear
     * @param businessModel
     * @return
     */
    public boolean createShopB(String name, String description, int foundationYear, String businessModel, double threshold) {
        ArrayList<Shop> shops = getData();
        shops.add(new ShopLoyality(name,description,foundationYear,businessModel, threshold));
        return daoShopsInterface.postData(shops);
    }

    public boolean createShopC(String name, String description, int foundationYear, String businessModel, String sponsoredBrand) {
        ArrayList<Shop> shops = getData();
        shops.add(new ShopSponsored(name,description,foundationYear,businessModel, sponsoredBrand));
        return daoShopsInterface.postData(shops);
    }

    /**
     * Crea un producto y lo añade a la lista de productos de la tienda que se pasa por parámetro.
     * @param shopName
     * @param product
     * @param price
     * @return
     */
    public boolean addProduct(String shopName, Product product, double price) {
        if (getShopBrand(shopName).equals(product.getBrand())){
            price = price * 0.9;
        }
        return daoShopsInterface.addProduct(shopName,new ProductShop(product.getName(), product.getBrand(), product.getMrp(), product.getCategory(), price));
    }

    /**
     * Elimina el producto de la tienda en la posicion del parámetro positionShop, y de su catalogo, elimina el producto que se encuentre en
     * la posicion del parámetro positionProduct.
     * @param positionShop
     * @param positionProduct
     * @return
     */
    public boolean removeProduct(int positionShop, int positionProduct) {
        return daoShopsInterface.removeProduct(positionShop, positionProduct);
    }

    /**
     * Añade reseñas al producto determinado por la positionProduct de la tienda y actualiza el fichero JSON con la nueva informacion.
     * @param positionShop
     * @param positionProduct
     * @param reviews
     */
    public void updateShop(int positionShop, int positionProduct, ArrayList<Review> reviews) {
        ArrayList<Shop> shops = getData();
        shops.get(positionShop).getCatalogue().get(positionProduct).setReviews(reviews);
        daoShopsInterface.postData(shops);
    }

    /**
     * Actualiza las ganancias de la tienda y actualiza la información en el respectivo fichero JSON.
     * @param cartData
     * @return
     */
    public ArrayList<Shop> updateEarnings(ArrayList<Cart> cartData) {
        ArrayList<Shop> shops = getData();

        int sizeShops = shops.size();
        int sizeCart = cartData.size();
        ArrayList<Shop> shopsEdited = new ArrayList<>();
        for (int i = 0; i < sizeShops; i++) {
            for (int j = 0; j < sizeCart; j++) {
                if (cartData.get(j).getShopName().equals(shops.get(i).getName())){
                    for (int k = 0; k < shops.get(i).getCatalogue().size(); k++) {
                        if (shops.get(i).getCatalogue().get(k).getName().equals(cartData.get(j).getProductName()) && shops.get(i).getCatalogue().get(k) instanceof ProductShop ){
                            shops.get(i).addEarnings(((ProductShop)shops.get(i).getCatalogue().get(k)).getRealPrice());
                            shopsEdited.add(shops.get(i));
                        }
                    }

                }
            }
        }
        daoShopsInterface.postData(shops);
        return shopsEdited;
    }

    public String getShopBrand(String shopName) {
        ArrayList<Shop> shops = getData();
        for (Shop shop : shops) {
            if (shop.getName().equals(shopName) && shop instanceof ShopSponsored) {
                return ((ShopSponsored) shop).getSponsorBrand();
            }
        }
        return null;
    }
}
