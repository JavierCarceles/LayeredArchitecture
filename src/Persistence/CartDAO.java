package Persistence;

import Business.Entities.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
public class CartDAO{
    private static Gson gson;
    private static String path = "Files/cartList.json";
    /**
     * Constructor de la clase donde se inicializa el GsonBuilder con tal de que
     * los ficheros JSON se escriban con el respectivo formato.
     */
    public CartDAO(){
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Recoge la informacion del fichero declarado en la variable path de la clase.
     * @return
     */
    public ArrayList<Cart> getData() {
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            FileReader reader = new FileReader(path);

            String texto = Files.readString(Paths.get(path));
            if (texto.isEmpty() || texto.equals("[]")){
                return carts;
            }else{
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                carts.addAll(Arrays.asList(gson.fromJson(jsonArray, Cart[].class)));
                return carts;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Escribe los productos (en este caso como objetos Cart) en el fichero json.
     * @param carts
     * @return
     */
    public boolean addProductstoCart(ArrayList<Cart> carts) {
        int size = carts.size();

        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path))) {
            jsonWriter.beginArray();
            for (int i = 0; i < size; i++) {
                gson.toJson(carts.get(i), Cart.class, jsonWriter);
            }
            jsonWriter.endArray();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vacía el contenido del fichero JSON para llevar a cabo la funcion de vaciar el carrito.
     */
    public void clearCart(){
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(path))) {
            jsonWriter.beginArray();
            gson.toJson("");
            jsonWriter.endArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




