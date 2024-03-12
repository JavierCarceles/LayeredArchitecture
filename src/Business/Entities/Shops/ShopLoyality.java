package Business.Entities.Shops;

public class ShopLoyality extends Shop{

    private double threshold;

    public ShopLoyality(String name, String description, int foundationYear, String businessModel, double threshold) {
        super(name, description, foundationYear, businessModel);
        this.threshold = threshold;
    }

}
