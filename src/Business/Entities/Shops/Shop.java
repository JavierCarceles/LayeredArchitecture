package Business.Entities.Shops;

import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;

import java.util.ArrayList;

public class Shop {

    protected String name;
    protected String description;
    protected int since;
    protected double earnings;
    protected String businessModel;
    protected ArrayList<ProductShop> catalogue;

    /**
     * Constructor del objeto.
     * @param name
     * @param description
     * @param foundationYear
     * @param businessModel
     */

    public Shop(String name, String description, int foundationYear, String businessModel) {
        this.name = name;
        this.description = description;
        this.since = foundationYear;
        this.earnings = 0;
        this.businessModel = businessModel;
        this.catalogue = new ArrayList<ProductShop>();
    }

    /**
     * Devuelve el nombre de la tienda.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la descripción de la tienda.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Devuelve el año en el que se fundó la tienda.
     * @return
     */
    public int getSince() {
        return since;
    }

    /**
     * Devuelve las ganancias totales de la tienda.
     * @return
     */
    public double getEarnings() {
        return earnings;
    }

    /**
     * Añade el valor que llega por parametro, a las ganancias totales de la tienda.
     * @param earnings
     */
    public void addEarnings(double earnings) {
        this.earnings += earnings;
    }

    /**
     * Devuelve la lista de productos que se venden en la tienda.
     * @return
     */
    public ArrayList<ProductShop> getCatalogue() {
        return catalogue;
    }

    /**
     * Pone los productos que llegan por parametro, como catalogo.
     * @param catalogue
     */
    public void setCatalogue(ArrayList<ProductShop> catalogue) {
        this.catalogue = catalogue;
    }
}
