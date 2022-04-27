package com.example.mycontactbook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private Button addNewButton;

    @FXML
    private TableColumn<Contact, String> cityCol;

    @FXML
    private TextField searchText;

    @FXML
    private TextField cityText;

    @FXML
    private ListView<Contact> contactListView;

    @FXML
    private TableView<Contact> contactTableView;

    @FXML
    private TableColumn<Contact, String> dateAddedCol;

    @FXML
    private Button deleteContactButton;

    @FXML
    private TableColumn<Contact, Integer> idCol;

    @FXML
    private TableColumn<Contact, String> emailCol;

    @FXML
    private TextField emailText;

    @FXML
    private TextField idText;

    @FXML
    private TextField firstText;

    @FXML
    private TableColumn<Contact, String> fnCol;

    @FXML
    private TextField lastText;

    @FXML
    private TableColumn<Contact, String> lnCol;

    @FXML
    private TableColumn<Contact, String> noteCol;

    @FXML
    private TextField noteText;

    @FXML
    private TableColumn<Contact, String> phoneCol;

    @FXML
    private TextField phoneText;

    @FXML
    private Label saifLabel;

    @FXML
    private Button clearButton;

    @FXML
    private Button updateContactButton;

    @FXML
    private Button saveToCSVButton;

    @FXML
    void buttonAction(ActionEvent event) {
        addNewButton.setOnAction(events -> {
            Alert add = new Alert(Alert.AlertType.INFORMATION);
            add.setTitle("New Contact Added");
            add.setHeaderText("New contact has been added.");
            add.showAndWait();
            addContact();
            clearText();
        });

        updateContactButton.setOnAction(events -> {
            Alert update = new Alert(Alert.AlertType.INFORMATION);
            update.setTitle("Update Successful");
            update.setHeaderText("Selected contact has been updated.");
            update.showAndWait();
            updateContact();
            clearText();
        });

        deleteContactButton.setOnAction(events -> {
            Alert delete = new Alert(Alert.AlertType.INFORMATION);
            delete.setTitle("Delete Successful");
            delete.setHeaderText("Selected contact has been deleted.");
            delete.showAndWait();
            deleteContact();
            clearText();
        });

        clearButton.setOnAction(events -> {
            clearText();
        });

        saveToCSVButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Contact List saved to CSV");
                clearText();
            }
        });

        saveToCSVButton.setOnAction(events -> {
            Alert saveToCSV = new Alert(Alert.AlertType.INFORMATION);
            saveToCSV.setTitle("Save to CSV Successful");
            saveToCSV.setHeaderText("List of contacts has been saved to CSV.");
            saveToCSV.showAndWait();
            saveToCSV();

        });
    }

    @FXML
    void listViewSelect(MouseEvent event) {
        Contact contact = contactListView.getSelectionModel().getSelectedItem();
        idText.setText("" + contact.getId());
        firstText.setText(contact.getFirstName());
        lastText.setText(contact.getLastName());
        emailText.setText(contact.getEmail());
        phoneText.setText(contact.getPhone());
        cityText.setText(contact.getCity());
        noteText.setText(contact.getNote());
    }

    @FXML
    void tableViewSelect(MouseEvent event) {
        Contact contact = contactTableView.getSelectionModel().getSelectedItem();
        idText.setText("" + contact.getId());
        firstText.setText(contact.getFirstName());
        lastText.setText(contact.getLastName());
        emailText.setText(contact.getEmail());
        phoneText.setText(contact.getPhone());
        cityText.setText(contact.getCity());
        noteText.setText(contact.getNote());
    }

    public Connection getConnection() {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:mycontacts.sqlite");
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ObservableList<Contact> getContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        Connection con = getConnection();
        String query = "SELECT * FROM contacts";
        Statement stmt;
        ResultSet results;

        try {
            stmt = con.createStatement();
            results = stmt.executeQuery(query);
            Contact contact;

            while (results.next()) {
                contact = new Contact(results.getInt("id"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        results.getString("email"),
                        results.getString("phone"),
                        results.getString("city"),
                        results.getString("note"),
                        results.getString("timestamp"));
                contactList.add(contact);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return contactList;
    }

    private void addContact() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("MM-dd-yyyy");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String fn, ln, email, phone, city, note, ts;
        fn = firstText.getText();
        ln = lastText.getText();
        email = emailText.getText();
        phone = phoneText.getText();
        city = cityText.getText();
        note = noteText.getText();

        ts = dataFormat.format(timestamp);
        Connection con = getConnection();
        PreparedStatement prepstmt;

        try {
            prepstmt = con.prepareStatement("INSERT INTO contacts(firstName,lastName,email,phone,city,note,timestamp)VALUES(?,?,?,?,?,?,?)");;
            prepstmt.setString(1,fn);
            prepstmt.setString(2,ln);
            prepstmt.setString(3,email);
            prepstmt.setString(4,phone);
            prepstmt.setString(5,city);
            prepstmt.setString(6,note);
            prepstmt.setString(7,ts);
            prepstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
        viewContacts();
    }

    private void updateContact() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("MM-dd-yyyy");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int id;
        String fn, ln, email, phone, city, note, ts;

        id = Integer.parseInt(idText.getText());
        fn = firstText.getText();
        ln = lastText.getText();
        email = emailText.getText();
        phone = phoneText.getText();
        city = cityText.getText();
        note = noteText.getText();

        ts = dataFormat.format(timestamp);
        Connection con = getConnection();
        PreparedStatement prepstmt;

        try {
            prepstmt = con.prepareStatement("UPDATE contacts SET firstName = ?,lastName = ?,email = ?,phone = ?,city = ?,note = ?,timestamp = ? WHERE id = ?");
            prepstmt.setString(1,fn);
            prepstmt.setString(2,ln);
            prepstmt.setString(3,email);
            prepstmt.setString(4,phone);
            prepstmt.setString(5,city);
            prepstmt.setString(6,note);
            prepstmt.setString(7,ts);
            prepstmt.setString(8, String.valueOf(id));
            prepstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
        viewContacts();
    }

    private void deleteContact() {
        Connection con = getConnection();
        PreparedStatement prepstmt;
        int id = Integer.parseInt(idText.getText());

        try {
            prepstmt = con.prepareStatement("DELETE FROM contacts WHERE id = ?");
            prepstmt.setString(1, String.valueOf(id));
            prepstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
        viewContacts();
    }

    public void viewContacts() {
        ObservableList<Contact> list = getContacts();
        idCol.setCellValueFactory(new PropertyValueFactory<Contact, Integer>("id"));
        fnCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lnCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("phone"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("city"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("note"));
        dateAddedCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("timestamp"));
        contactTableView.setItems(list);
        contactListView.setItems(list);

        FilteredList<Contact> filteredContacts = new FilteredList<>(list, contact -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredContacts.setPredicate(Contact -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                if (Contact.getFirstName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else if (Contact.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<Contact> contactSortedList = new SortedList<>(filteredContacts);
        contactSortedList.comparatorProperty().bind(contactTableView.comparatorProperty());
        contactTableView.setItems(contactSortedList);
        contactListView.setItems(contactSortedList);
    }

    private void saveToCSV() {
        Connection con = getConnection();
        String query = "SELECT * FROM contacts";
        Statement stmt;
        ResultSet results;

        try {
            stmt = con.createStatement();
            results = stmt.executeQuery(query);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("ContactsList.csv"));
            fileWriter.write("ID, First Name, Last Name, Email , Phone, City, Note, Date Added");
            while (results.next()) {
                int id = results.getInt("id");
                String fn = results.getString("firstName");
                String ln = results.getString("lastName");
                String email = results.getString("email");
                String phone = results.getString("phone");
                String city = results.getString("city");
                String note = results.getString("note");
                String timestamp = results.getString("timestamp");

                String row = String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                        id, fn, ln, email, phone, city, note, timestamp);
                fileWriter.newLine();
                fileWriter.write(row);
            }
            stmt.close();
            fileWriter.close();

        } catch(Exception e) {
            System.out.println(e);
        }
        viewContacts();
    }

    private void clearText() {
        firstText.clear();
        lastText.clear();
        emailText.clear();
        phoneText.clear();
        cityText.clear();
        noteText.clear();
        searchText.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewContacts();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                saifLabel.requestFocus();
            }
        });
    }
}
