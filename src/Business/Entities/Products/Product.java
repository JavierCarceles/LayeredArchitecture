package Business.Entities.Products;

import Business.Entities.Review;

import java.util.ArrayList;

public class Product {

    protected final String name;
    protected final String brand;
    protected final double mrp;
    protected final String category;
    private ArrayList<Review> reviews;

    public Product(String name, String brand, double maxValue, String category) {
        this.name = name;
        this.brand = brand;
        this.mrp = maxValue;
        this.category = category;}


    public Product(String name, String brand, double mrp, String category, ArrayList<Review> reviews) {
        this.name = name;
        this.brand = brand;
        this.mrp = mrp;
        this.category = category;
        this.reviews = reviews;
    }

    public String getName() {
        return name;}



    public String getBrand() {
        return brand;}


    public double getMrp() {
        return mrp;}

    /**

     Devuelve la categoria a la que pertenece el producto.
     @return*/
    public String getCategory() {
        return category;}

    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) reviews;}


    public void setReviews(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);}

    public double averageReviewRating(){
        double total = 0;
        for (int i = 0; i < reviews.size(); i++) {
            total += reviews.get(i).getRating();
        }
        return total/reviews.size();
    }
}