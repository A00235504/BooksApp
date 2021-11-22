package com.aakash.booksapp.Model;

public class Popularbooks
{

    private String bookname;
    private String bookdescription;
    private String bookprice;
    private String image;


    public Popularbooks() {}

    // Getter and setter method
    public String getbookname()
    {
        return bookname;
    }
    public void setbookname(String bookname)
    {
        this.bookname = bookname;
    }

    public String getbookdescription()
    {
        return bookdescription;
    }
    public void setbookdescription(String bookdescription)
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

