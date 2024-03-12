package Persistence.Shops.ShopDAOs;

import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;
import Business.Entities.Shops.Shop;
import Persistence.Shops.Interfaces.DAOShopsInterface;
import java.io.IOException;
import java.io.StringWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;
import java.util.ArrayList;
import java.util.Arrays;


public class ShopAPIDAO implements DAOShopsInterface {

    private static ApiHelper apiHelper;
    private static Gson gson;
    private static final String shopURL = "https://balandrau.salle.url.edu/dpoo/P1-G11/shops";


    public ShopAPIDAO() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            apiHelper = new ApiHelper();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ArrayList<Shop> getData() {

        ArrayList<Shop> shops = new ArrayList<>();

        try {

            ApiHelper apiHelper = new ApiHelper();
            String shopsString = apiHelper.getFromUrl(shopURL);

            if (!shopsString.equals("[]")){
                JsonArray jsonArray = JsonParser.parseString(shopsString).getAsJsonArray();
                shops.addAll(Arrays.asList(gson.fromJson(jsonArray, Shop[].class)));
                return shops;
            }
            return shops;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean updateData(ArrayList<Shop> shops, int position) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            apiHelper.deleteFromUrl(String.valueOf(stringBuilder.append(shopURL).append("/").append(position)));
            return postData(shops);
        } catch (ApiException e) {
            return false;
        }
    }

    @Override
    public boolean removeProduct(int positionShop, int positionProduct) {
        ArrayList<Shop> shops = getData();
        Shop shop = shops.get(positionShop);

        shops.remove(positionShop);
        shop.getCatalogue().remove(positionProduct);
        shops.add(shop);

        return updateData(shops, positionShop);
    }

    @Override
    public boolean addProduct(String shopName, ProductShop product) {
        ArrayList<Shop> shops = getData();
        int size = shops.size();
        int position = 0;

        for (int i = 0; i < size; i++) {
            if (shops.get(i).getName().equals(shopName)) {
                position = i;
            }
        }
        Shop shop = shops.get(position);
        shop.getCatalogue().add(product);
        shops.remove(position);
        shops.add(shop);

        return updateData(shops, position);
    }

    @Override
    public boolean postData(ArrayList<Shop> shops) {
        try {

            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.beginArray();
            for (int i = shops.size()-1; i < shops.size(); i++) {
                gson.toJson(shops.get(i), Shop.class, jsonWriter);
            }
            jsonWriter.endArray();
            stringWriter.flush();
            String jsonString = stringWriter.toString().trim().substring(1, stringWriter.toString().length() - 1);

            apiHelper.postToUrl(shopURL, jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
