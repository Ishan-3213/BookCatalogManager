package Genre;

public enum Genres {
    CCB("Cartoons & Comics Books", "Cartoons_Comics_Books.csv"),
    HCB("Hobbies & Collectibles Books", "Hobbies_Collectibles_Books.csv"),
    MTV("Movies & TV", "Movies_TV.csv"),
    MRB("Music & Radio Books", "Music_Radio_Books.csv"),
    NEB("Nostalgia & Eclectic Books", "Nostalgia_Eclectic_Books.csv"),
    OTR("Old-Time Radio", "Old_Time_Radio.csv"),
    SSM("Sports & Sports Memorabilia", "Sports_Sports_Memorabilia.csv"),
    TPA("Trains, Planes & Automobiles", "Trains_Planes_Automobiles.csv");

    private final String genreName;
    private final String associatedFileName;

    Genres(String genreName, String associatedFileName) {
        this.genreName = genreName;
        this.associatedFileName = associatedFileName;
    }

    public static String getGenreName(String genre) {
        for (Genres gnr : Genres.values()) {
            if (gnr.name().equals(genre)) {
                return gnr.genreName;
            }
        }
        return null;
    }

    public static String getAssociatedFileName(String genre) {
        for (Genres gnr : Genres.values()) {
            if (gnr.name().equals(genre)) {
                return gnr.associatedFileName;
            }
        }
        return null;
    }
}
