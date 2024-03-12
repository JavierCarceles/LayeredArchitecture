package Persistence.Products.Interfaces;

import Business.Entities.Products.Product;
import Business.Entities.Review;

import java.util.ArrayList;

public interface DAOProductsInterface {
    boolean postData(ArrayList<Product> products);
    ArrayList<Product> getData();
    boolean deleteData(int position);

    boolean addReview(int position, Review review);
}
