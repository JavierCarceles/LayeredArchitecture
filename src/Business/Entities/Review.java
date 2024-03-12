package Business.Entities;

public class Review {
    //Puntuació que posa l'usuari de l'1 al 5
    int rating;
    String comment;

    /**
     * Constructor del objeto.
     * @param rating
     * @param comment
     */
    public Review (int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Devuelve el texto respectivo al comentario que alguien haya dejado sobre este producto
     * en la reseña.
     * @return
     */
    public String getComment() {
        return comment;
    }


    /**
     * Devuelve la valoracion de la reseña sobre este producto.
     * @return
     */
    public int getRating() {
        return rating;
    }

}
