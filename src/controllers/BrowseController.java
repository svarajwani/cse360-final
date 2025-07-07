package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import models.Book;

public class BrowseController {
    @FXML private ComboBox<String> catBox;
    @FXML private TreeTableView<Book> tree;
    @FXML private TreeTableColumn<Book, String> colTitle;
    @FXML private TreeTableColumn<Book, String> colAuthor;
    @FXML private TreeTableColumn<Book, String> colPrice;
    @FXML private TreeTableColumn<Book, String> colCondition;
    @FXML private TreeTableColumn<Book, String> colCategory;
    @FXML private TreeTableColumn<Book, String> colQty;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        catBox.getItems().setAll("Computer", "Math", "Natural Science", "English", "Other");
        catBox.getSelectionModel().selectFirst();

        colTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getTitle()));
        colAuthor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getAuthor()));
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getValue().getPrice())));
        colCondition.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getCondition()));
        colCategory.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().getCategory()));
        colQty.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getValue().getQty())));


        books.addAll(
                new Book("Discrete Math", "Rosen", "Math", "Slightly Used", 95.0, 3),
                new Book("Operating Systems", "Silberschatz", "Computer", "New", 110.0, 5),
                new Book("Physics for Scientists", "Tipler", "Natural Science", "Very Used", 60.0, 1),
                new Book("Pride and Prejudice", "Austen", "English", "Slightly Used", 28.0, 2),
                new Book("Discrete Mathematics", "Kenneth Rosen", "Math", "Slightly Used", 75.0, 2),
                new Book("Linear Algebra and Its Applications", "David C. Lay", "Math", "New", 90.0, 4),
                new Book("Calculus Early Transcendentals", "James Stewart", "Math", "Very Used", 35.0, 1),
                new Book("Introduction to Algorithms", "Cormen", "Computer", "New", 120.0, 5),
                new Book("Computer Networking", "James Kurose", "Computer", "Slightly Used", 60.0, 2),
                new Book("Operating System Concepts", "Silberschatz", "Computer", "New", 95.0, 3),
                new Book("Artificial Intelligence: A Modern Approach", "Russell & Norvig", "Computer", "Slightly Used", 100.0, 1),
                new Book("General Chemistry", "Darin Brown", "Natural Science", "New", 80.0, 3),
                new Book("Biology", "Campbell", "Natural Science", "Very Used", 25.0, 1),
                new Book("Fundamentals of Physics", "Halliday & Resnick", "Natural Science", "New", 99.0, 2),
                new Book("Organic Chemistry", "Paula Yurkanis Bruice", "Natural Science", "Slightly Used", 50.0, 4),
                new Book("Pride and Prejudice", "Jane Austen", "English", "Very Used", 10.0, 2),
                new Book("To Kill a Mockingbird", "Harper Lee", "English", "Slightly Used", 15.0, 1),
                new Book("1984", "George Orwell", "English", "New", 18.0, 3),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "English", "New", 17.0, 2),
                new Book("Macbeth", "William Shakespeare", "English", "Slightly Used", 9.0, 1),
                new Book("Data Structures", "Seymour Lipschutz", "Computer", "Very Used", 22.0, 1),
                new Book("Multivariable Calculus", "James Stewart", "Math", "New", 70.0, 2),
                new Book("Differential Equations", "Paul Blanchard", "Math", "Slightly Used", 60.0, 1),
                new Book("Microbiology", "Gerard J. Tortora", "Natural Science", "New", 100.0, 3),
                new Book("Genetics: Analysis & Principles", "Robert Brooker", "Natural Science", "Slightly Used", 40.0, 1),
                new Book("Moby Dick", "Herman Melville", "English", "Very Used", 7.0, 1),
                new Book("Jane Eyre", "Charlotte Bronte", "English", "New", 13.0, 2),
                new Book("Database System Concepts", "Silberschatz", "Computer", "New", 80.0, 2),
                new Book("Computer Organization", "Carl Hamacher", "Computer", "Very Used", 20.0, 1),
                new Book("Other Book 1", "Random Author", "Other", "Slightly Used", 11.0, 1),
                new Book("Other Book 2", "Another Author", "Other", "New", 20.0, 2),
                new Book("Other Book 3", "Somebody Else", "Other", "Very Used", 5.0, 1)
        );

        catBox.setOnAction(event -> updateBookTable());
        updateBookTable();
    }

    @FXML
    private void handleAddBook(javafx.event.ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddBook.fxml"));
        Parent root = loader.load();
        AddBookController addBookController = loader.getController();
        addBookController.setBrowseController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Book");
        stage.setScene(new Scene(root));
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void addBook(Book book) {
        books.add(book);
        updateBookTable();
    }

    @FXML
    private void load(javafx.event.ActionEvent event) {
        System.out.println("Load button clicked!");
    }

    @FXML
    private void handleBuyBook() {
        TreeItem<Book> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Book book = selectedItem.getValue();
            if (book.getQty() > 0) {
                book.setQty(book.getQty() - 1);
                updateBookTable();
                System.out.println("Added to cart and purchased: " + book.getTitle());
            } else {
                System.out.println("Sorry, out of stock!");
            }
        } else {
            System.out.println("No book selected!");
        }
    }


    @FXML
    private void logout(javafx.event.ActionEvent event) {
        ((javafx.stage.Stage)(((javafx.scene.Node)event.getSource()).getScene().getWindow())).close();
    }

    private void updateBookTable() {
        String selectedCategory = catBox.getValue();
        TreeItem<Book> root = new TreeItem<>();
        for (Book book : books) {
            if (book.getCategory().equals(selectedCategory)) {
                root.getChildren().add(new TreeItem<>(book));
            }
        }
        tree.setRoot(root);
        tree.setShowRoot(false);
    }
}
