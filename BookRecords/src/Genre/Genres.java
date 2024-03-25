// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------

package Genre;
/**
 * Enum representing various genres along with their associated file names.
 */
public enum Genres {
    // Each enum constant represents a genre along with its corresponding genre name and associated file name
    CCB("Cartoons & Comics Books", "Cartoons_Comics_Books.csv.txt"),
    HCB("Hobbies & Collectibles Books", "Hobbies_Collectibles_Books.csv.txt"),
    MTV("Movies & TV", "Movies_TV.csv.txt"),
    MRB("Music & Radio Books", "Music_Radio_Books.csv.txt"),
    NEB("Nostalgia & Eclectic Books", "Nostalgia_Eclectic_Books.csv.txt"),
    OTR("Old-Time Radio", "Old_Time_Radio.csv.txt"),
    SSM("Sports & Sports Memorabilia", "Sports_Sports_Memorabilia.csv.txt"),
    TPA("Trains, Planes & Automobiles", "Trains_Planes_Automobiles.csv.txt");

    private final String genreName; // Name of the genre
    private final String associatedFileName; // Associated file name for the genre

    /**
     * Constructor for Genres enum.
     * @param genreName The name of the genre.
     * @param associatedFileName The associated file name for the genre.
     */
    Genres(String genreName, String associatedFileName) {
        this.genreName = genreName;
        this.associatedFileName = associatedFileName;
    }

    /**
     * Get the genre name based on the provided genre enum constant.
     * @param genre The genre enum constant.
     * @return The name of the genre.
     */
    public static String getGenreName(String genre) {
        for (Genres gnr : Genres.values()) {
            if (gnr.name().equals(genre)) {
                return gnr.genreName;
            }
        }
        return null;
    }

    /**
     * Get the associated file name for the provided genre enum constant.
     * @param genre The genre enum constant.
     * @return The associated file name for the genre.
     */
    public static String getAssociatedFileName(String genre) {
        for (Genres gnr : Genres.values()) {
            if (gnr.name().equals(genre)) {
                return gnr.associatedFileName;
            }
        }
        return null;
    }
}
