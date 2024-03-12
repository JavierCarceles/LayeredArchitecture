package Persistence.Products.ProductDAOs;

import Business.Entities.Products.Product;
import Business.Entities.Review;
import Persistence.Products.Interfaces.DAOProductsInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class ProductAPIDAO implements DAOProductsInterface {
    private static ApiHelper apiHelper;
    private static Gson gson;
    private static final String productURL = "https://balandrau.salle.url.edu/dpoo/P1-G11/products";
    public ProductAPIDAO() throws ApiException {
        apiHelper = new ApiHelper();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public ArrayList<Product> getData(){

        ArrayList<Product> products = new ArrayList<>();

        try {

            String productsString = apiHelper.getFromUrl(productURL);

            if (!productsString.equals("[]")){
                JsonArray jsonArray = JsonParser.parseString(productsString).getAsJsonArray();
                products.addAll(Arrays.asList(gson.fromJson(jsonArray, Product[].class)));
                return products;
            }
            return products;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean deleteData(int position) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            apiHelper.deleteFromUrl(String.valueOf(stringBuilder.append(productURL).append("/").append(position)));
            return true;
        } catch (ApiException e) {
            return false;
        }
    }
    private boolean updateData(ArrayList<Product> products, int position) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            apiHelper.deleteFromUrl(String.valueOf(stringBuilder.append(productURL).append("/").append(position)));
            return postData(products);
        } catch (ApiException e) {
            return false;
        }
    }
    @Override
    public boolean postData(ArrayList<Product> products){
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.beginArray();
            for (int i = products.size()-1; i < products.size(); i++) {
                gson.toJson(products.get(i), Product.class, jsonWriter);
            }
            jsonWriter.endArray();
            stringWriter.flush();
            String jsonString = stringWriter.toString().trim().substring(1, stringWriter.toString().length() - 1);

            apiHelper.postToUrl(productURL, jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }

    }
    @Override
    public boolean addReview(int position, Review review) {
        ArrayList<Product> products = getData();
        Product product = products.get(position);

        products.remove(position);
        product.getReviews().add(review);
        products.add(product);

        return updateData(products,position);
    }
}
