package hu.unideb.inf;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Controller {
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane tablePane;
    @FXML private AnchorPane createPane;
    @FXML private AnchorPane aboutPane;
    @FXML private Label menuLabel;
    @FXML private Label errorLabel;
    @FXML private TableView tableView;
    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private TextField phonenumberField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private Button createButton;

    private Contacts contacts = new Contacts("contacts.json");
    private ObservableList<Contact> viewData;

    @FXML
    private void showContacts(ActionEvent event) {
        tablePane.setVisible(true);
        createPane.setVisible(false);
        aboutPane.setVisible(false);
        menuLabel.setText("Névjegyek");

        logger.info("Showing contacts menu");
    }

    @FXML
    private void showCreate(ActionEvent event) {
        lastnameField.setText("");
        lastnameField.setDisable(false);
        firstnameField.setText("");
        firstnameField.setDisable(false);
        phonenumberField.setText("");
        emailField.setText("");
        addressField.setText("");

        tablePane.setVisible(false);
        createPane.setVisible(true);
        aboutPane.setVisible(false);
        menuLabel.setText("Új névjegy hozzáadása");
        createButton.setText("Hozzáadás");

        if(event instanceof EditEvent) {
            EditEvent editEvent = (EditEvent) event;
            createButton.setOnAction((ev) -> {
                createContact(editEvent);
            });
        }
        else {
            createButton.setOnAction((ev) -> {
                createContact(event);
            });
        }

        logger.info("Showing create/edit menu");
    }

    @FXML
    private void showAbout(ActionEvent event) {
        tablePane.setVisible(false);
        createPane.setVisible(false);
        aboutPane.setVisible(true);
        menuLabel.setText("Készítő");

        logger.info("Showing about menu");
    }

    @FXML
    private void exitApp(ActionEvent event) {
        logger.info("Closing app...");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void createContact(ActionEvent event) {
        try {
            if(event instanceof EditEvent) {
                EditEvent editEvent = (EditEvent) event;

                if(editEvent.getContact() != null) {
                    Contact contact = editEvent.getContact();
                    contact.setLastName(lastnameField.getText());
                    contact.setFirstName(firstnameField.getText());
                    contact.setPhoneNumber(PhoneNumber.of(phonenumberField.getText()));
                    contact.setEmail(emailField.getText());
                    contact.setAddress(addressField.getText());

                    contacts.saveToDisk();
                    tableView.refresh();

                    logger.info(contact.getLastName() + " " + contact.getFirstName() + " - contact is edited successfully");
                }
            }
            else {
                Contact newContact = new Contact();
                newContact.setLastName(lastnameField.getText());
                newContact.setFirstName(firstnameField.getText());
                newContact.setPhoneNumber(PhoneNumber.of(phonenumberField.getText()));
                newContact.setEmail(emailField.getText());
                newContact.setAddress(addressField.getText());

                contacts.addContact(newContact);
                viewData.add(newContact);
                contacts.saveToDisk();

                logger.info(newContact.getLastName() + " " + newContact.getFirstName() + " - contact is created successfully");
            }

            showContacts(new ActionEvent());
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
            logger.error("Error creating/editing contact: " + e.getMessage());
        }
    }

    private void editContact(Contact contact) {
        showCreate(new EditEvent(contact));

        lastnameField.setText(contact.getLastName());
        lastnameField.setDisable(true);
        firstnameField.setText(contact.getFirstName());
        firstnameField.setDisable(true);
        phonenumberField.setText(contact.getPhoneNumber().getNumber());
        emailField.setText(contact.getEmail());
        addressField.setText(contact.getAddress());

        menuLabel.setText("Névjegy módosítása");
        createButton.setText("Módosítás");
        logger.info(contact.getLastName() + " " + contact.getFirstName() + " - editing contact...");
    }

    public void initialize() {
        logger.info("Initializing controller...");
        setupTable();
        ObservableList<Contact> viewData = loadData();
        if (viewData != null)
            tableView.setItems(viewData);
    }

    private ObservableList<Contact> loadData() {
        try {
            contacts.loadFromDisk();
            viewData = FXCollections.observableArrayList(contacts.getElements());

            return viewData;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void setupTable() {
        menuLabel.setText("Névjegyek");
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Contact, String> c1 = new TableColumn<Contact, String>("Vezetéknév");
        c1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        c1.setMinWidth(120);
        tableView.getColumns().add(c1);

        TableColumn<Contact, String> c2 = new TableColumn<Contact, String>("Keresztnév");
        c2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        c2.setMinWidth(120);
        tableView.getColumns().add(c2);

        TableColumn<Contact, String> c3 = new TableColumn<Contact, String>("Telefonszám");
        c3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhoneNumber().toString()));
        c3.setMinWidth(120);
        tableView.getColumns().add(c3);

        TableColumn<Contact, String> c4 = new TableColumn<Contact, String>("Lakcím");
        c4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        c4.setMinWidth(280);
        tableView.getColumns().add(c4);

        TableColumn<Contact, String> c5 = new TableColumn<Contact, String>("Email cím");
        c5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
        c5.setMinWidth(180);
        tableView.getColumns().add(c5);

        // Adding edit button
        TableColumn editCol = new TableColumn( "Módosítás" );
        Callback<TableColumn<Contact, String>, TableCell<Contact, String>> editCellFactory = new Callback<>() {
            @Override
            public TableCell call( final TableColumn<Contact, String> param )
            {
                final TableCell<Contact, String> cell = new TableCell<Contact, String>()
                {
                    final Button btn = new Button( "Módosítás" );

                    @Override
                    public void updateItem( String item, boolean empty )
                    {
                        super.updateItem( item, empty );
                        if ( empty )
                        {
                            setGraphic( null );
                            setText( null );
                        }
                        else {
                            btn.setOnAction( ( ActionEvent event ) ->
                            {
                                try {
                                    Contact contact = getTableView().getItems().get( getIndex() );
                                    editContact(contact);
                                }
                                catch (Exception e) {
                                    logger.error("Error editing contact: " + e.getMessage());
                                }
                            } );
                            setGraphic( btn );
                            setText( null );
                        }
                    }
                };
                return cell;
            }
        };
        editCol.setCellFactory(editCellFactory);
        editCol.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(editCol);

        // Adding remove button
        TableColumn removeCol = new TableColumn( "Törlés" );
        Callback<TableColumn<Contact, String>, TableCell<Contact, String>> removeCellFactory = new Callback<>() {
            @Override
            public TableCell call( final TableColumn<Contact, String> param )
            {
                final TableCell<Contact, String> cell = new TableCell<Contact, String>()
                {
                    final Button btn = new Button( "Törlés" );

                    @Override
                    public void updateItem( String item, boolean empty )
                    {
                        super.updateItem( item, empty );
                        if ( empty )
                        {
                            setGraphic( null );
                            setText( null );
                        }
                        else {
                            btn.setOnAction( ( ActionEvent event ) ->
                            {
                                try {
                                    Contact contact = getTableView().getItems().get( getIndex() );
                                    viewData.remove(contact);
                                    contacts.removeContact(contact);
                                    contacts.saveToDisk();
                                    logger.info(contact.getLastName() + " " + contact.getFirstName() + " - contact is removed successfully");
                                }
                                catch (Exception e) {
                                    logger.error("Error removing contact: " + e.getMessage());
                                }
                            } );
                            setGraphic( btn );
                            setText( null );
                        }
                    }
                };
                return cell;
            }
        };
        removeCol.setCellFactory(removeCellFactory);
        removeCol.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(removeCol);
    }
}
