package com.aakash.booksapp.Model;

public class BookStore
{

    private String bookname;
    private String bookdescription;
    private String bookprice;
    private String image;


    public BookStore() {}

    // Getter and setter method
    public String getbookname()
    {
        return bookname;
    }
    public void setbookname(String bookname)
    {
        this.bookname = bookname;
    }

    public String bookdescription()
    {
        return bookdescription;
    }
    public void bookdescription(String bookdescription)
    { this.bookdescription = bookdescription; }

    public String getbookprice()
    {
        return bookprice;
    }
    public void setbookprice(String bookprice)
    {
        this.bookprice = bookprice;
    }

    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }

}
