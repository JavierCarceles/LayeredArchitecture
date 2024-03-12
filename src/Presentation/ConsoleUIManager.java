package Presentation;

import Business.Entities.Cart;
import Business.Entities.Products.Product;
import Business.Entities.Products.ProductShop;
import Business.Entities.Review;
import Business.Entities.Shops.Shop;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUIManager {
    private static Scanner sc;

    /**
     * Constructor de la clase que instancia el Scanner.
     */
    public ConsoleUIManager(){
        sc = new Scanner(System.in);
    }

    /**
     * Printar cualquier texto que le llegue por parametro.
     * @param text
     */
    public void printText(String text) {
        System.out.println(text);
    }

    /**
     * Printar el nombre "elcofre".
     */
    public void printTitle() {
        System.out.println("  EEEEE  L      CCCCC  OOO   FFFFF  RRRR   EEEEE");
        System.out.println("  E      L     C      O   O  F      R   R  E    ");
        System.out.println("  EEEE   L     C      O   O  FFFF   RRRR   EEEE ");
        System.out.println("  E      L     C      O   O  F      R  R   E    ");
        System.out.println("  EEEEE  LLLLL  CCCCC  OOO   F      R   RR EEEEE");
        System.out.println("\nWelcome to elCofre Digital Shopping Experiences.\n");
    }

    /**
     * Printar el menu inicial del programa.
     */
    public void printMainMenu() {
        System.out.println("\n\t1) Manage Products\n" +
                "\t2) Manage Shops\n" +
                "\t3) Search Products\n" +
                "\t4) List Shops\n" +
                "\t5) Your Cart\n\n" +
                "\t6) Exit\n");
    }

    /**
     * Pedir un numero entero con cualquier restriccion de rango que se necesite.
     * @param text
     * @param min
     * @param max
     * @return
     */
    public int askForInteger(String text, int min, int max) {
        while (true) {
            try {
                System.out.print(text);
                int num = sc.nextInt();
                if (num >= min && num <= max) {
                    return num;
                }else{
                    System.out.println("ERROR: Not a valid number, the value has to be between " + min + " and " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("This isn't an integer!");
            } finally {
                sc.nextLine();
            }
        }
    }

    /**
     * Pedir un carácter, también con limitaciones de rango por si en un futuro hay mas opciones no requerir de otro metodo o
     * modificar este.
     * @param text
     * @param min
     * @param max
     * @return
     */
    public char askForChar(String text, char min, char max) {
        while(true) {
            try {
                System.out.print(text);
                char option = Character.toUpperCase(sc.next().charAt(0));
                if (option >= min && option <= max) {
                    return option;
                }
                else {
                    System.out.println("Not a valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("This isn't a char!");
            } finally {
                sc.nextLine();
            }
        }
    }

    /**
     * Pedir un numero decimal de tipo double, con limitación de rango al igual que askforinteger y askforchar.
     * @param text
     * @param min
     * @param max
     * @return
     */
    public double askForDouble(String text, double min, double max) {
        while (true) {
            try {
                System.out.print(text);
                double num = sc.nextDouble();
                if (num >= min && num <= max) {
                    return num;
                }else{
                    System.out.println("ERROR: Not a valid number, the value has to be between " + min + " and " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("This isn't a double!");
            } finally {
                sc.nextLine();
            }
        }
    }

    /**
     * Printar el submenú de la opción 1 del menú principal.
     */
    public void printDigitalShoppingExperience1() {
        System.out.println("\t1) Create a Product\n" +
                "\t2) Remove a Product\n" +
                "\n\t3) Back");
    }

    /**
     * Printar el submenú de la opción 2 del menú principal.
     */
    public void printDigitalShoppingExperience2() {
        System.out.println("\t1) Create a Shop\n" +
                "\t2) Expand a Shop’s Catalogue\n" +
                "\t3) Reduce a Shop’s Catalogue\n" +
                "\t4) Back");
    }

    /**
     * Pedir un texto al usuario sin restricciones de input.
     * @param text
     * @return
     */
    public String askForString(String text) {
        System.out.println(text);
        return sc.nextLine();
    }

    /**
     * Convierte cualquier texto al formato formal pedido en el enunciado.
     * @param text
     * @return
     */
    public String formatString(String text) {
        String[] palabras = text.split("\\s+");
        StringBuilder nuevoTexto = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                String palabraFormateada = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
                nuevoTexto.append(palabraFormateada).append(" ");
            }
        }
        return nuevoTexto.toString().trim();
    }

    /**
     * Transforma los productos que le llegan por parametro a un texto donde se muestran todos, para devolverlo
     * al metodo printText().
     * @param products
     * @return
     */
    public String printProducts(ArrayList<Product> products) {
        int size = products.size();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i).append(". ").append(products.get(i).getName()).append("\n");
        }
        return stringBuilder.toString();

    }

    public String printProductShops(ArrayList<ProductShop> products) {
        int size = products.size();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i).append(". ").append(products.get(i).getName()).append("\n");
        }
        return stringBuilder.toString();

    }

    /**
     * Transforma las tiendas que le llegan por parametro a un texto donde se muestran todas, para devolverlo
     * al metodo printText().
     * @param shops
     * @return
     */
    public String printShops(ArrayList<Shop> shops) {
        int size = shops.size();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i).append(". ").append(shops.get(i).getName()).append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();

    }

    /**
     * Printa los productos que coincidan con el texto que llega a través del parametro query, comprobando si existen productos creados,
     * y si existe en alguna tienda, cuyo caso imprime el valor al que se vende en esa tienda.
     * @param products
     * @param shops
     * @param query
     */
    public void listProducts(ArrayList<Product> products, ArrayList<Shop> shops, String query) {
        int size = 0;
        int i = 0;
        int j = 0;
        boolean found = false;
        if (!products.isEmpty()) {
            for (Product product : products) {
                found = false;
                if (product.getBrand().equals(query) || product.getName().toLowerCase().contains(query.toLowerCase())) {
                    System.out.println(i + ") \"" + product.getName() + "\" by \"" + product.getBrand() + "\"\n");
                    i++;
                    for (Shop shop : shops) {
                        size = shop.getCatalogue().size();
                        for (j = 0; j < size; j++) {
                            if (shop.getCatalogue().get(j).getName().equals(product.getName())) {
                                if (!found) {
                                    found = true;
                                    System.out.println("Sold at:\n");
                                }
                                System.out.println("\t- " + shop.getName() + ": " + shop.getCatalogue().get(j).getPrice() + "\n");
                            }
                        }
                    }
                    if (!found) {
                        System.out.println("This product is not currently being sold in any shops.\n");
                    }
                }
            }

        } else {
            System.out.println("There are no products in our data base");
        }
    }

    /**
     * Printa las valoraciones que llegan por parametro, si llega una lista vacía de valoraciones, el metodo muestra el mensaje
     * de que no hay valoraciones para ese producto.
     * @param reviews
     */
    public void printReviews(ArrayList<Review> reviews) {
        if (reviews.size()==0){
            System.out.println("There's no reviews for this product\n");
        }else {
            double avgRating = 0;

            for (Review review : reviews) {
                System.out.println(review.getRating() + "* " + review.getComment());
                avgRating += review.getRating();
            }
            avgRating /= reviews.size();

            System.out.println("Average rating: " + avgRating);
        }
    }

    /**
     * Printa los productos de una tienda.
     * @param shop
     */
    public void printCatalogue(Shop shop) {
        int i = 0;

        System.out.println(shop.getName() + "- Since " + shop.getSince() + "\n" + shop.getDescription() + "\n");
        for (ProductShop product: shop.getCatalogue()) {
            System.out.println("\t" + i + ") \"" + product.getName() + "\" by \"" + product.getBrand() + "\"\nPrice: " + product.getPrice() + "\n");
            i++;
        }
    }

    /**
     * Printa la lista de productos que haya en el carrito.
     * @param cartData
     * @return
     */
    public String printCart(ArrayList<Cart> cartData) {

        int size = cartData.size();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("\t- ").append(cartData.get(i).getProductName()).append(" by ").append(cartData.get(i).getShopName()).append("\n\t  Price: ").append(cartData.get(i).getPrice());
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();

    }

    /**
     * Printa el mensaje de ganancia total de la tienda al vender un determinado producto.
     * @param updateEarnings
     * @param cartData
     */
    public void printCartCheckOut(ArrayList<Shop> updateEarnings, ArrayList<Cart> cartData) {
        int sizeShops = updateEarnings.size();
        int sizeCartData = cartData.size();
        for (int i = 0; i < sizeShops; i++) {
            for (int j = 0; j < sizeCartData; j++) {
                if (cartData.get(j).getShopName().equals(updateEarnings.get(i).getName())){
                    System.out.println(updateEarnings.get(i).getName() + " has earned " + cartData.get(j).getPrice() + ", for an historic total of " + updateEarnings.get(i).getEarnings());
                }
            }
        }
        System.out.println("\nYour cart has been cleared");

    }

    public String transformChar(char category) {
        switch (category){
            case 'A':
                return "GENERAL";
            case 'B':
                return "REDUCED";
            case 'C':
                return "SUPER_REDUCED";
        }
        return null;
    }

    public String transformChar2(char businessModel) {
        switch (businessModel){
            case 'A':
                return "MAX_PROFIT";
            case 'B':
                return "LOYALITY";
            case 'C':
                return "SPONSORED";
        }
        return null;
    }
}
