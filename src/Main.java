import Business.CartManager;
import Business.ProductManager;
import Business.ReviewManager;
import Business.ShopManager;
import Persistence.CartDAO;
import Presentation.ConsoleUIManager;
import Presentation.Controller;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

public class Main {
    /**
     * Main del proyecto con las instancias de los managers, DAO's y el consoleUIManager.
     * @param args
     */
    public static void main(String[] args){

        ProductManager productManager;
        ShopManager shopManager;
        ConsoleUIManager consoleUIManager = new ConsoleUIManager();
        ReviewManager reviewManager = new ReviewManager();
        consoleUIManager.printTitle();

        try{
            consoleUIManager.printText("Checking API status...");
            new ApiHelper();
            productManager = new ProductManager("API");
            shopManager = new ShopManager("API");
            consoleUIManager.printText("Conection with API verifyed...");
        } catch (ApiException e) {
            consoleUIManager.printText("Impossible to connect with host baladrau\nVerifying local files...");
            productManager = new ProductManager("JSON");
            shopManager = new ShopManager("JSON");
        }

        CartDAO cartDAO = new CartDAO();
        CartManager cartManager = new CartManager(cartDAO);

        Controller controller = new Controller(consoleUIManager, productManager, shopManager, cartManager, reviewManager);
        controller.run();

    }
}