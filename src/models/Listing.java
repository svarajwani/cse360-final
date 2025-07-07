package models;

import javafx.beans.property.*;

/** Simple POJO + JavaFX properties so TableView can bind */
public class Listing {

    // ── core fields ──────────────────────────────────────
    private final StringProperty id        = new SimpleStringProperty();
    private final StringProperty seller    = new SimpleStringProperty();
    private final StringProperty title     = new SimpleStringProperty();
    private final StringProperty author    = new SimpleStringProperty();
    private final IntegerProperty year     = new SimpleIntegerProperty();
    private final StringProperty cat       = new SimpleStringProperty();
    private final StringProperty cond      = new SimpleStringProperty();
    private final DoubleProperty origPrice = new SimpleDoubleProperty();
    private final DoubleProperty sysPrice  = new SimpleDoubleProperty();
    private final IntegerProperty qty      = new SimpleIntegerProperty();

    // ── constructors ────────────────────────────────────
    public Listing() {}                       // for “header rows”

    public Listing(String id, String seller, String title, String author,
                   int year, String cat, String cond,
                   double orig, double sys, int qty) {
        this.id.set(id);         this.seller.set(seller);
        this.title.set(title);   this.author.set(author);
        this.year.set(year);     this.cat.set(cat);       this.cond.set(cond);
        this.origPrice.set(orig);this.sysPrice.set(sys);  this.qty.set(qty);
    }

    // ── getters (for DAO / business logic) ──────────────
    public String id()        { return id.get(); }
    public String seller()    { return seller.get(); }
    public String title()     { return title.get(); }
    public String author()    { return author.get(); }
    public int    year()      { return year.get(); }
    public String cat()       { return cat.get(); }
    public String cond()      { return cond.get(); }
    public double origPrice() { return origPrice.get(); }
    public double sysPrice()  { return sysPrice.get(); }
    public int    qty()       { return qty.get(); }
    public void   setQty(int q){ qty.set(q); }

    // ── JavaFX property getters (for TableView) ─────────
    public StringProperty  titleProperty()    { return title;     }
    public StringProperty  authorProperty()   { return author;    }
    public DoubleProperty  sysPriceProperty() { return sysPrice;  }
    public IntegerProperty qtyProperty()      { return qty;       }
    public StringProperty  condProperty()     { return cond;      }
}
