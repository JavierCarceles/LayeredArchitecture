package Persistence.Products.ProductDAOs;

import Business.Entities.Products.Product;
import Business.Entities.Review;
import Persistence.Products.Interfaces.DAOProductsInterface;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductDAO implements DAOProductsInterface {
    private static Gson gson;
    private static String path = "Files/products.json";

    /**
     * Constructor de la clase donde se inicializa el GsonBuilder con tal de que
     * los ficheros JSON se escriban con el respectivo formato.
     */
    public ProductDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Recoge la informacion del fichero declarado en la variable path de la clase.
     * @return
     */
    @Override
    public ArrayList<Product> getData() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            FileReader reader = new FileReader(path);

            String texto = Files.readString(Paths.get(path));
            if (texto.isEmpty() || texto.equals("[]")){
                return products;
            }else{
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                products.addAll(Arrays.asList(gson.fromJson(jsonArray, Product[].class)));
                return products;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteData(int position) {
        ArrayList<Product> products = getData();
        products.remove(position);

        return postData(products);
    }

    @Override
    public boolean addReview(int position, Review review) {
        ArrayList<Product> products = getData();

        products.get(position).getReviews().add(review);

        return postData(products);
    }

    /**
     * Escribe los productos en el fichero json.
     * @param products
     * @return
     */
    @Override
    public boolean postData(ArrayList<Product> products){
        int size = products.size();

        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path))){
            jsonWriter.beginArray();
            for (int i = 0; i < size; i++) {
                gson.toJson(products.get(i), Product.class, jsonWriter);
            }
            jsonWriter.endArray();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}