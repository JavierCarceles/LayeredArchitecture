package Persistence.Shops.Interfaces;

import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;
import Business.Entities.Shops.Shop;
import java.util.ArrayList;

public interface DAOShopsInterface {

    boolean postData(ArrayList<Shop> shops);
    ArrayList<Shop> getData();
    
    boolean removeProduct(int positionShop, int positionProduct);

    boolean addProduct(String shopName, ProductShop product);
}
