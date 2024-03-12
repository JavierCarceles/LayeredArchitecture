package Business.Entities.Products;

public class ProductShop extends Product {
    private final double price;

    public ProductShop(String name, String brand, double mrp, String category, double price) {
        super(name, brand, mrp, category);
        this.price = price;
    }

    /**

     Devuelve el precio al que se vende el producto.
     @return*/
    public double getPrice() {
        return price;}

    public double getRealPrice(){
        switch (category){
            case "General":
                return price/(1+(21/100));
            case "Reduced Taxes":
                if (averageReviewRating() > 3.5){
                    return price/(1+(5/100));
                }
                return price/(1+(1/100));
            case "Superreduced Taxes":
                if (mrp >100){
                    return price/(1+(4/100));
                }
                return price;
            default:
                return 0;
        }
    }
}