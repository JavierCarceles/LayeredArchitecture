package Business;

import Business.Entities.Products.Product;
import Business.Entities.Review;
import Persistence.Products.Interfaces.DAOProductsInterface;
import Persistence.Products.ProductDAOs.ProductAPIDAO;
import Persistence.Products.ProductDAOs.ProductDAO;
import edu.salle.url.api.exception.ApiException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
public class ProductManager {
    private static final String path = "Files/products.json";
    private DAOProductsInterface daoProductsInterface;
    public ProductManager(String opt) {
        switch (opt){
            case "JSON": this.daoProductsInterface = new ProductDAO();break;

            case "API":
                try {
                    this.daoProductsInterface = new ProductAPIDAO();
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    /**
     * Comprueba a traves del return del productDAO si el fichero JSON donde se guardan los
     * productos esta operativo para continuar con la ejecución del programa.
     * @return
     */
    public boolean checkProductFile() throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        return reader==null;
    }

    /**
     * Llama al productDAO para recoger la información del fichero JSON.
     * @return
     */
    public ArrayList<Product> getData() {
        return daoProductsInterface.getData();
    }

    /**
     * Comprueba que el nombre que llega por parametro existe en la lista de productos existentes o si existen productos.
     * @param name
     * @return
     */
    public boolean checkName(String name) {
        ArrayList<Product> products = getData();
        if (products.isEmpty()){
            return true;
        } else{
            for (Product product : products) {
                if (product.getName().equals(name)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Crea un producto, lo añade a una lista y se la pasa al productDAO para que reescriba los productos con la nueva incorporación.
     * @param name
     * @param brand
     * @param maxValue
     * @param category
     * @return
     */
    public boolean createProduct(String name, String brand, double maxValue, String category) {
        Product product = new Product(name,brand,maxValue,category);
        ArrayList<Product> products = getData();
        products.add(product);
        return daoProductsInterface.postData(products);
    }

    /**
     * Elimina el producto en la posición que llega por parametro y actualiza el fichero JSON.
     * @param position
     * @return
     */
    public boolean removeProduct(int position) {
        return daoProductsInterface.deleteData(position);
    }

    /**
     * Recoge todos los productos con el productDAO y devuelve el producto cuando lo encuentra.
     * @param name
     * @return
     */
    public Product getProduct(String name) {
        ArrayList<Product> products = getData();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Añade una review al producto que se encuentra en la posición que llega por parametro.
     * @param position
     * @param review
     * @return
     */
    public boolean addReview(int position, Review review) {
        return daoProductsInterface.addReview(position, review);
    }

    /**
     * Devuele la lista de reseñas que tiene el producto, incorporando previamente la reseña que se pasa por parametro, al producto que
     * también se pasa por parametro.
     * @param review
     * @param product
     * @return
     */
    public ArrayList<Review> getReviewsList(Review review, Product product) {

        if (product.getReviews()==null){
            ArrayList<Review> reviews = new ArrayList<>();
            reviews.add(review);
            product.setReviews(reviews);
        }else{
            product.getReviews().add(review);
        }
        return product.getReviews();
    }


}
