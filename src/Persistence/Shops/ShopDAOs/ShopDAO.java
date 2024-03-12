package Persistence.Shops.ShopDAOs;

import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;
import Business.Entities.Shops.Shop;
import Persistence.Shops.Interfaces.DAOShopsInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopDAO implements DAOShopsInterface {
    private static String path = "Files/shops.json";
    private static Gson gson;
    public ShopDAO(){
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public ArrayList<Shop> getData() {

        ArrayList<Shop> shops = new ArrayList<>();
        try {

            FileReader reader = new FileReader(path);

            String texto = Files.readString(Paths.get(path));
            if (texto.isEmpty() || texto.equals("[]")){
                return shops;
            }else{
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                shops.addAll(Arrays.asList(gson.fromJson(jsonArray, Shop[].class)));
                return shops;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeProduct(int positionShop, int positionProduct) {
        ArrayList<Shop> shops = getData();

        shops.get(positionShop).getCatalogue().remove(positionProduct);

        return postData(shops);
    }

    @Override
    public boolean addProduct(String shopName, ProductShop product) {
        ArrayList<Shop> shops = getData();
        int size = shops.size();

        for (int i = 0; i < size; i++) {
            if (shops.get(i).getName().equals(shopName)) {
                shops.get(i).getCatalogue().add(product);
            }
        }

        return postData(shops);
    }

    @Override
    public boolean postData(ArrayList<Shop> shops){
        int size = shops.size();

        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path))){
            jsonWriter.beginArray();
            for (int i = 0; i < size; i++) {
                gson.toJson(shops.get(i), Shop.class, jsonWriter);
            }
            jsonWriter.endArray();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
