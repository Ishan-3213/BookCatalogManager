package doPart2;

import java.io.Serializable;

public class Book implements Serializable {
    public String title;
    public String authors;
    public double price;
    public String isbn;
    public String genre;
    public int year;

    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getAuthors() {
        return authors;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    // TO-DO: Override equals() and toString() methods if needed
    // TO-DO: Setters

    public static String[] getFields(String line) {
        String[] tempFields = line.split(",");
        String title= tempFields[0];
        int count = 0;
        if (title.startsWith("\"")){
            for (count=1; count<tempFields.length; count++){
                if (tempFields[count].endsWith("\"")){
                    title +=", "+tempFields[count];
                    count++;
                    break;
                }
                title += tempFields[count];
            }
        }
        int One = 0;
        if (count<tempFields.length && count!=0) One = 1;
        String [] fields = new String[tempFields.length-count+One];
        if (count!=0 && count != tempFields.length){
            fields[0] = title;
            int fieldsCount = 1;
            for (int i = count; i<tempFields.length; i++){
                fields[fieldsCount] = tempFields[i];
                fieldsCount++;
            }
        }
        else fields = tempFields;
        return fields;
    }
}
