package Business.Entities.Shops;

public class ShopSponsored extends Shop{

    private String sponsorBrand;

    public ShopSponsored(String name, String description, int foundationYear, String businessModel, String sponsorBrand) {
        super(name, description, foundationYear, businessModel);
        this.sponsorBrand = sponsorBrand;
    }

    public String getSponsorBrand() {
        return sponsorBrand;
    }
}
