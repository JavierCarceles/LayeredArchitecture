package Presentation;

import Business.CartManager;
import Business.ProductManager;
import Business.ReviewManager;
import Business.ShopManager;

import java.io.FileNotFoundException;

public class Controller {
    private final ConsoleUIManager consoleUIManager;
    private final ProductManager productManager;
    private final ShopManager shopManager;
    private final CartManager cartManager;
    private final ReviewManager reviewManager;

    /**
     * Constructor de la clase Controller.
     * @param consoleUIManager
     * @param productManager
     * @param shopManager
     * @param cartManager
     */
    public Controller(ConsoleUIManager consoleUIManager, ProductManager productManager, ShopManager shopManager, CartManager cartManager, ReviewManager reviewManager) {
        this.consoleUIManager = consoleUIManager;
        this.productManager = productManager;
        this.shopManager = shopManager;
        this.cartManager = cartManager;
        this.reviewManager = reviewManager;
    }

    /**
     * Método que se encarga de printar por pantalla el menu principal.
     */
    public void run() {
        int opt = 0;

        try {
            productManager.checkProductFile();
            consoleUIManager.printText("Starting program...");
            do {
                consoleUIManager.printMainMenu();
                opt = consoleUIManager.askForInteger("Choose a Digital Shopping Experience: ", 1, 6);
                executeOption(opt);
            } while (opt != 6);
        }catch (FileNotFoundException e){
            consoleUIManager.printText("Error: the files can't be accessed\nShutting down...");
        }

    }

    /**
     * Método con toda la logica del programa necesaria para navegar por los menús, ademas
     * de dar todas las ordenes a las demas clases managers y consoleuiManager.
     * @param opt
     */
    private void executeOption(int opt) {

        switch (opt){
            case 1:
                consoleUIManager.printDigitalShoppingExperience1();

                switch (consoleUIManager.askForInteger("Choose an option: ", 1, 3)){
                    case 1:
                        String name = consoleUIManager.askForString("Please enter the product's name: ");
                        if (productManager.checkName(name)) {
                            String brand = consoleUIManager.formatString(consoleUIManager.askForString("Please enter the product's brand: "));
                            double maxValue = consoleUIManager.askForDouble("Please enter the product’s maximum retail price: ", 0, 999999);
                            char category = consoleUIManager.askForChar("The system supports the following product categories:\n\n" +
                                    "A) General\nB) Reduced Taxes\nC) Superreduced Taxes\n\n" +
                                    "Please pick the product’s category: ", 'A', 'C');
                            String transformChar = consoleUIManager.transformChar(category);
                            if (productManager.createProduct(name, brand, maxValue, transformChar)) {
                                consoleUIManager.printText("The product \"" + name + "\" by \"" + brand + "\" was added to the system.");
                            } else {
                                consoleUIManager.printText("ERROR: The product was not added");
                            }
                        }
                        else {
                            consoleUIManager.printText("ERROR: The name is already taken");
                        }
                        break;
                    case 2:
                        boolean removed = false;
                        if (!productManager.getData().isEmpty()) {
                            do {
                                int position = consoleUIManager.askForInteger(consoleUIManager.printProducts(productManager.getData()) + "\nwich one would you like to remove? ", 0, productManager.getData().size());
                                if (consoleUIManager.askForString("Are you sure you want to remove \"" + productManager.getData().get(position).getName() + "\" by \"" + productManager.getData().get(position).getBrand() + "\"? ").equalsIgnoreCase("yes")) {
                                    removed = true;
                                    String nameProduct = productManager.getData().get(position).getName();
                                    String brandProduct = productManager.getData().get(position).getBrand();
                                    if (productManager.removeProduct(position)) {
                                        consoleUIManager.printText("\"" + nameProduct + "\" by \"" + brandProduct + "\" has been withdrawn from sale.");
                                    } else {
                                        consoleUIManager.printText("ERROR: The product was not removed");
                                    }
                                }
                            } while (!removed);
                        } else {
                            consoleUIManager.printText("ERROR: There are no products");
                        }
                        break;
                }
                break;
            case 2:

                consoleUIManager.printDigitalShoppingExperience2();
                switch (consoleUIManager.askForInteger("Choose an option: ", 1, 4)) {
                    case 1:
                        String name = consoleUIManager.askForString("Please enter the shop's name: ");
                        if (shopManager.checkName(name)) {
                            String description = consoleUIManager.askForString("Please enter the shop’s description: ");
                            int foundationYear = consoleUIManager.askForInteger("Please enter the shop’s founding year: ",0,2023);
                            char businessModel = consoleUIManager.askForChar("The system supports the following business models:\n" +
                                    "A) Maximum Benefits\nB) Loyalty\nC) Sponsored\nPlease pick the shop’s business model: ",'A','C');

                            String businessModelString = consoleUIManager.transformChar2(businessModel);
                            double threshold = 0;
                            String sponsoredBrand="";
                            boolean fine;

                            if (businessModel == 'B') {
                                threshold = consoleUIManager.askForDouble("Please enter the shop’s loyalty threshold: ", 0.1, 999);
                                fine = shopManager.createShopB(name,description,foundationYear,businessModelString, threshold);
                            }else if (businessModel=='C'){
                                sponsoredBrand = consoleUIManager.askForString("Please enter the shop’s sponsoring brand: ");
                                fine = shopManager.createShopC(name,description,foundationYear,businessModelString, sponsoredBrand);
                            }else{
                                fine = shopManager.createShopA(name,description,foundationYear,businessModelString);
                            }
                            if (fine) {
                                consoleUIManager.printText("\"" + name + "\" is now part of the elCofre family.");
                            } else {
                                consoleUIManager.printText("The shop was not added to the system");
                            }

                        } else {
                            consoleUIManager.printText("ERROR: The name is already taken");
                        }
                        break;
                    case 2:
                       String shopName = consoleUIManager.askForString("Please enter the shop's name: ");
                        if (!shopManager.checkName(shopName)) {
                            String productName = consoleUIManager.askForString("Please enter the product’s name: ");
                            if (!productManager.checkName(productName)) {
                                double price = consoleUIManager.askForDouble("Please enter the product’s price at this shop: ",0,productManager.getProduct(productName).getMrp());
                                if (shopManager.addProduct(shopName, productManager.getProduct(productName), price)) {
                                    consoleUIManager.printText("The product has been added to the shop");
                                } else {
                                    consoleUIManager.printText("The product has not been added to the shop");
                                }
                            } else {
                                consoleUIManager.printText("ERROR: The product you have entered does not exist");
                            }
                        } else {
                            consoleUIManager.printText("ERROR: The shop you have entered does not exist");
                        }
                        break;
                    case 3:
                        if (!shopManager.getData().isEmpty()) {
                            int positionShop = consoleUIManager.askForInteger(consoleUIManager.printShops(shopManager.getData()), 0, shopManager.getData().size());
                            consoleUIManager.printText("This shop sells the following products:");
                            int positionProduct = consoleUIManager.askForInteger(consoleUIManager.printProductShops(shopManager.getData().get(positionShop).getCatalogue()) + "\nwich one would you like to remove? ", 0, shopManager.getData().get(positionShop).getCatalogue().size());
                            String productName = shopManager.getData().get(positionShop).getCatalogue().get(positionProduct).getName();
                            String productBrand = shopManager.getData().get(positionShop).getCatalogue().get(positionProduct).getBrand();
                            if (shopManager.removeProduct(positionShop,positionProduct)) {
                                consoleUIManager.printText("The product \"" + productName + "\" by \"" + productBrand + "\" is no longer being sold in the shop");
                            } else {
                                consoleUIManager.printText("ERROR: The product \"" + productName + "\" by \"" + productBrand + "\" has not been removed from the catalogue");
                            }

                        } else {
                            consoleUIManager.printText("There are no shops");
                        }
                        break;
                }
                break;
            case 3:
                String query = consoleUIManager.askForString("Enter your query: ");
                consoleUIManager.listProducts(productManager.getData(),shopManager.getData(),query);
                int position = consoleUIManager.askForInteger("Which one would you like to review?",0,productManager.getData().size());
                switch (consoleUIManager.askForInteger("\n1) Read reviews\n2) Review product\n\nChoose an option: ",0,2)) {
                    case 1:
                        consoleUIManager.printText("These are the reviews for \"" + productManager.getData().get(position).getName() + "\" by \"" + productManager.getData().get(position).getBrand() + "\":\n");
                        consoleUIManager.printReviews((productManager.getData().get(position)).getReviews());
                        break;
                    case 2:
                        int rating = consoleUIManager.askForString("Please rate the product (1-5 stars): ").length();
                        String comment = consoleUIManager.askForString("Please add a comment to your review: ");
                        if (productManager.addReview(position, reviewManager.createReview(rating,comment))) {
                            consoleUIManager.printText("Thank you for your review of \"" + productManager.getData().get(position).getName() + "\" by \"" + productManager.getData().get(position).getBrand() + "\".");
                        } else {
                            consoleUIManager.printText("ERROR: The review has not been added to the product");
                        }
                        break;
                }

                break;
            case 4:
                int positionShop = -1;
                do {
                    consoleUIManager.printText("The elCofre family if formed by the following shops:\n");
                    positionShop = consoleUIManager.askForInteger(consoleUIManager.printShops(shopManager.getData()) + shopManager.getData().size() + ") Exit\nWhich catalogue do you want to see? ", 0, shopManager.getData().size());
                    if (shopManager.getData().size()>positionShop) {

                        int option = -1;
                        do {
                            consoleUIManager.printCatalogue(shopManager.getData().get(positionShop));
                            int positionProduct = consoleUIManager.askForInteger( shopManager.getData().get(positionShop).getCatalogue().size() + ")Exit\nWhich one are you interested in? ", 0, shopManager.getData().get(positionShop).getCatalogue().size());
                            if (positionProduct<shopManager.getData().get(positionShop).getCatalogue().size()) {
                                option = consoleUIManager.askForInteger("\n\t1) Read reviews\n\t2) Review product\n\t3) Add to Cart\n\t4)Exit\n\nChoose an option: ", 1, 4);
                                switch (option) {
                                    case 1:
                                        consoleUIManager.printReviews(shopManager.getData().get(positionShop).getCatalogue().get(positionProduct).getReviews());
                                        break;
                                    case 2:
                                        shopManager.updateShop(positionShop, positionProduct, productManager.getReviewsList(reviewManager.createReview(
                                                consoleUIManager.askForInteger("Please rate the product (1-5 stars): ",1,5), consoleUIManager.askForString("Please add a comment to your review: ")),
                                                shopManager.getData().get(positionShop).getCatalogue().get(positionProduct)));

                                        break;
                                    case 3:
                                        if(cartManager.addCart(shopManager.getData().get(positionShop).getName(),shopManager.getData().get(positionShop).getCatalogue().get(positionProduct).getName(), shopManager.getData().get(positionShop).getCatalogue().get(positionProduct).getPrice())){
                                            consoleUIManager.printText("The product has been added to your cart");
                                        }else{
                                            consoleUIManager.printText("Error adding the product to your cart");
                                        }
                                        break;
                                }
                            }else{
                                option=4;
                                positionShop=shopManager.getData().size();
                            }
                        } while (option != 4);
                    }
                }while (positionShop!=shopManager.getData().size());
                break;
            case 5:
                int optionMenu5 = 0;
                if (cartManager.getCartData().size()!=0) {
                    do {
                        consoleUIManager.printText("Your cart contains the following items:\n\n" + consoleUIManager.printCart(cartManager.getCartData()) + "\nTotal price: " + cartManager.getTotal());
                        optionMenu5 = consoleUIManager.askForInteger("\n\t1) Checkout\n\t2) Clear cart\n\n\t3) Back\nChoose an option: ", 1, 3);
                        switch (optionMenu5) {
                            case 1:
                                String answer = consoleUIManager.askForString("Are you sure you want to checkout? ");
                                answer = answer.toLowerCase();
                                if (answer.equals("yes")) {
                                    consoleUIManager.printCartCheckOut(shopManager.updateEarnings(cartManager.getCartData()), cartManager.getCartData());
                                    cartManager.clearJsonFile();
                                    consoleUIManager.printText("Your cart has been cleared");
                                }
                                break;
                            case 2:
                                String answer2 = consoleUIManager.askForString("Are you sure you want to clear your cart? ");
                                answer2 = answer2.toLowerCase();
                                if (answer2.equals("yes")) {
                                    cartManager.clearJsonFile();
                                    consoleUIManager.printText("Your cart has been cleared");
                                }
                                break;
                            case 3:

                                break;
                        }
                    }while (optionMenu5!=3);
                }else{
                    consoleUIManager.printText("You have no items in your cart. ");
                }
                break;
        }

    }
}
