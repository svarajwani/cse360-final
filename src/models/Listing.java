package models;

import javafx.beans.property.*;

public class Book {
    // ✅ Original Book fields
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty author = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty condition = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final BooleanProperty selected = new SimpleBooleanProperty(false); // checkbox property

    // ✅ Listing.java fields
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty seller = new SimpleStringProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final StringProperty cat = new SimpleStringProperty();
    private final StringProperty cond = new SimpleStringProperty();
    private final DoubleProperty origPrice = new SimpleDoubleProperty();
    private final DoubleProperty sysPrice = new SimpleDoubleProperty();

    // ✅ Constructor (extend as needed)
    public Book(String title, String author, String category, String condition, double price, int qty) {
        this.title.set(title);
        this.author.set(author);
        this.category.set(category);
        this.condition.set(condition);
        this.price.set(price);
        this.qty.set(qty);
    }

    // ✅ Optional full constructor from Listing
    public Book(String id, String seller, String title, String author,
                int year, String cat, String cond,
                double orig, double sys, int qty) {
        this.id.set(id);
        this.seller.set(seller);
        this.title.set(title);
        this.author.set(author);
        this.year.set(year);
        this.cat.set(cat);
        this.cond.set(cond);
        this.origPrice.set(orig);
        this.sysPrice.set(sys);
        this.qty.set(qty);
    }

    // ✅ Original Book getters
    public String getTitle()       { return title.get(); }
    public String getAuthor()      { return author.get(); }
    public String getCategory()    { return category.get(); }
    public String getCondition()   { return condition.get(); }
    public double getPrice()       { return price.get(); }
    public int getQty()            { return qty.get(); }
    public void setQty(int qty)    { this.qty.set(qty); }

    // ✅ Selection (checkbox)
    public boolean isSelected()              { return selected.get(); }
    public void setSelected(boolean value)   { selected.set(value); }
    public BooleanProperty selectedProperty(){ return selected; }

    // ✅ Property accessors (original)
    public StringProperty titleProperty()    { return title; }
    public StringProperty authorProperty()   { return author; }
    public StringProperty categoryProperty() { return category; }
    public StringProperty conditionProperty(){ return condition; }
    public DoubleProperty priceProperty()    { return price; }
    public IntegerProperty qtyProperty()     { return qty; }

    // ✅ Listing-style Getters
    public String id()        { return id.get(); }
    public String seller()    { return seller.get(); }
    public int year()         { return year.get(); }
    public String cat()       { return cat.get(); }
    public String cond()      { return cond.get(); }
    public double origPrice() { return origPrice.get(); }
    public double sysPrice()  { return sysPrice.get(); }

    // ✅ Listing-style Property bindings
    public DoubleProperty sysPriceProperty()  { return sysPrice; }
    public StringProperty condProperty()      { return cond; }
}
