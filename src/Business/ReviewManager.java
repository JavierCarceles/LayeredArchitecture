package Business;

import Business.Entities.Review;

public class ReviewManager {

    public Review createReview(int ratting, String comment){
        return new Review(ratting, comment);
    }

}
