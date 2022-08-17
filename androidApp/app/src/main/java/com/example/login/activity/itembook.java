package com.example.login.activity;


public class itembook {
    String bookTitle,bookIsbn,bookAuthor,bookPublisher,bookDate,bookBatch,bookimg;

    public  itembook(){

    }
    public itembook(String bookTitle, String bookIsbn, String bookAuthor, String bookPublisher, String bookDate, String bookBatch, String bookimg) {
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookDate = bookDate;
        this.bookBatch = bookBatch;
        this.bookimg = bookimg;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle =  bookTitle;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn ="ISBN: " + bookIsbn;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor ="Author: " + bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher ="Publisher: " + bookPublisher;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate ="Publish Date: " + bookDate;
    }

    public String getBookBatch() {
        return bookBatch;
    }

    public void setBookBatch(String bookBatch) {
        this.bookBatch ="Batch: " + bookBatch;
    }

    public String getBookimg() {
        return bookimg;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }
}
