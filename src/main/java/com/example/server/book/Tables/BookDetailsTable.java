package com.example.server.book.Tables;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class BookDetailsTable {
    @Id
    private static String bookId;
    private static String bookName;
    private static String authorName;
    private static Float ratings;
    private static boolean isAvailable;

    public static String getBookId() {
        return bookId;
    }

    public static void setBookId(String bookId) {
        BookDetailsTable.bookId = bookId;
    }

    public static String getBookName() {
        return bookName;
    }

    public static void setBookName(String bookName) {
        BookDetailsTable.bookName = bookName;
    }

    public static String getAuthorName() {
        return authorName;
    }

    public static void setAuthorName(String authorName) {
        BookDetailsTable.authorName = authorName;
    }

    public static Float getRatings() {
        return ratings;
    }

    public static void setRatings(Float ratings) {
        BookDetailsTable.ratings = ratings;
    }

    public static boolean getIsAvailable() {
        return isAvailable;
    }

    public static void setIsAvailable(boolean isAvailable) {
        BookDetailsTable.isAvailable = isAvailable;
    }
}
