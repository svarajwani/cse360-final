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
import javafx.scene.control.cell.CheckBoxTreeTableCell; // ✅ ADDED
import javafx.beans.property.SimpleStringProperty;     // ✅ ADDED
import models.Book;

public class BrowseController {
    @FXML private ComboBox<String> catBox;
    @FXML private TreeTableView<Book> tree;

    @FXML private TreeTableColumn<Book, Boolean> colSelect; // ✅ NEW
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

        // ✅ Checkbox setup
        if (colSelect != null) {
            colSelect.setCellValueFactory(data -> data.getValue().getValue().selectedProperty());
            colSelect.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(colSelect));
        }

        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getTitle()));
        colAuthor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAuthor()));
        colPrice.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue().getPrice())));
        colCondition.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCondition()));
        colCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getCategory()));
        colQty.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue().getQty())));

        books.addAll(
                // your full book list here (unchanged)
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
        // ✅ Step 1: Original single-selection logic (preserved)
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

        // ✅ Step 2: Checkbox-based multi-purchase logic (added, not replacing)
        for (Book book : books) {
            if (book.isSelected()) {
                if (book.getQty() > 0) {
                    book.setQty(book.getQty() - 1);
                    book.setSelected(false);
                    System.out.println("Purchased via checkbox: " + book.getTitle());
                } else {
                    System.out.println("Out of stock (checkbox): " + book.getTitle());
                }
            }
        }

        updateBookTable();
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
