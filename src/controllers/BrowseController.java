package controllers;

import dao.HistoryDaoPlain;
import dao.ListingDaoPlain;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import models.Listing;
import utils.Session;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BrowseController {

    @FXML private ComboBox<String> catBox;
    @FXML private TreeTableView<Listing> tree;
    @FXML private TreeTableColumn<Listing,String>  colTitle, colAuthor;
    @FXML private TreeTableColumn<Listing,Number> colPrice, colQty;
    @FXML private TreeTableColumn<Listing,Void>   colBuy;     // add in FXML

    private final ListingDaoPlain dao = new ListingDaoPlain();
    private final HistoryDaoPlain history = new HistoryDaoPlain();

    @FXML
    private void initialize() {
        catBox.setItems(FXCollections.observableArrayList(
                "Computer","Math","Natural Science","English","Other"));
        catBox.getSelectionModel().selectFirst();

        colTitle .setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new TreeItemPropertyValueFactory<>("author"));
        colPrice .setCellValueFactory(new TreeItemPropertyValueFactory<>("sysPrice"));
        colQty   .setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));

        tree.setRowFactory(tv -> new TreeTableRow<>() {
            @Override protected void updateItem(Listing item, boolean empty) {
                super.updateItem(item, empty);
                setStyle((empty || item==null) ? "" :
                        "HDR".equals(item.id()) ? "-fx-font-weight: bold;" : "");
            }
        });

        addBuyButtonColumn();
        load();
    }

    /* ── BUY column ─────────────────────────────────────── */
    private void addBuyButtonColumn() {
        colBuy.setCellFactory(col -> new TreeTableCell<>() {
            private final Button btn = new Button("Buy");
            {
                btn.setOnAction(e -> {
                    Listing l = getTreeTableRow().getItem();
                    if (l == null || "HDR".equals(l.id())) return;

                    if (dao.purchase(l.id())) {
                        try { history.logSaleAndPayout(Session.user(), l); }
                        catch (IOException ex) { ex.printStackTrace(); }

                        new Alert(Alert.AlertType.INFORMATION,
                                "Purchased \"" + l.title() + "\"").showAndWait();
                        load();
                    } else {
                        new Alert(Alert.AlertType.WARNING,
                                "Sorry, sold out!").showAndWait();
                    }
                });
            }
            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || getTreeTableRow().getItem()==null
                        || "HDR".equals(getTreeTableRow().getItem().id()))
                    setGraphic(null);
                else
                    setGraphic(btn);
            }
        });
    }

    /* ── Load & grouping ────────────────────────────────── */
    @FXML public void load() {
        List<Listing> rows = dao.findByCategory(catBox.getValue());

        Map<String,List<Listing>> grouped = rows.stream()
                .collect(Collectors.groupingBy(Listing::cond,
                        LinkedHashMap::new, Collectors.toList()));

        TreeItem<Listing> root = new TreeItem<>();
        root.setExpanded(true);
        addGroup(root, grouped,"Used-Like-New","Used Like New");
        addGroup(root, grouped,"Moderately-Used","Moderately Used");
        addGroup(root, grouped,"Heavily-Used","Heavily Used");
        tree.setRoot(root);
    }
    private void addGroup(TreeItem<Listing> parent, Map<String,List<Listing>> map,
                          String key, String hdr) {
        var bucket = map.getOrDefault(key, List.of());
        if (bucket.isEmpty()) return;
        TreeItem<Listing> h = new TreeItem<>(headerRow(hdr)); h.setExpanded(true);
        bucket.forEach(l -> h.getChildren().add(new TreeItem<>(l)));
        parent.getChildren().add(h);
    }
    private Listing headerRow(String lbl){
        return new Listing("HDR","","** "+lbl+" **","",0,"","",0,0,0);
    }

    /* ── Logout button handler ──────────────────────────── */
    @FXML
    private void logout(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            stage.setMaximized(true);
            stage.setTitle("SunDevil Book Login");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
