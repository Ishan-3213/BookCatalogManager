// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------
package doPart2;

import Genre.Genres;

import java.io.Serializable;

/**
 * Represents a Book entity with various attributes.
 */
public class Book implements Serializable {
    public String title;
    public String authors;
    public double price;
    public String isbn;
    public String genre;
    public int year;

    /**
     * Constructs a Book object with the specified attributes.
     *
     * @param title    The title of the book.
     * @param authors  The authors of the book.
     * @param price    The price of the book.
     * @param isbn     The ISBN of the book.
     * @param genre    The genre of the book.
     * @param year     The publication year of the book.
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    /**
     * Retrieves the price of the book.
     *
     * @return The price of the book.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the publication year of the book.
     *
     * @return The publication year of the book.
     */
    public int getYear() {
        return year;
    }

    /**
     * Retrieves the authors of the book.
     *
     * @return The authors of the book.
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Retrieves the genre of the book.
     *
     * @return The genre of the book.
     */
    public String getGenre() {
        return Genres.getGenreName(genre);
    }

    /**
     * Retrieves the ISBN of the book.
     *
     * @return The ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Retrieves the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Compares this Book to the specified object for equality.
     *
     * @param obj  The object to compare this Book against.
     * @return True if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Double.compare(book.price, price) == 0 &&
                year == book.year &&
                title.equals(book.title) &&
                authors.equals(book.authors) &&
                isbn.equals(book.isbn) &&
                genre.equals(book.genre);
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string representation of the Book object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(this.getTitle()).append("\n");
        sb.append("Authors: ").append(this.getAuthors()).append("\n");
        sb.append("Price: $").append(String.format("%.2f", this.getPrice())).append("\n");
        sb.append("ISBN: ").append(this.getIsbn()).append("\n");
        sb.append("Genre: ").append(this.getGenre()).append("\n");
        sb.append("Year: ").append(this.getYear()).append("\n");
        return sb.toString();
    }

    /**
     * Parses a line from the input file and extracts fields.
     *
     * @param line  The line to parse.
     * @return An array of fields extracted from the line.
     */
    public static String[] getFields(String line) {
        String[] tempFields = line.split(",", -1);
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
