package hu.unideb.inf;

import javafx.beans.property.ReadOnlyStringWrapper;
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

public class Controller {
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane tablePane;
    @FXML private AnchorPane createPane;
    @FXML private AnchorPane aboutPane;
    @FXML private AnchorPane settingsPane;
    @FXML private Label menuLabel;
    @FXML private Label errorLabel;
    @FXML private Label errorLabel2;
    @FXML private TableView tableView;
    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private TextField phonenumberField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private TextField filenameField;
    @FXML private Button createButton;

    private ObservableList<Contact> viewData;
    private Contacts contacts = new Contacts();

    @FXML
    private void showContacts(ActionEvent event) {
        showPane(tablePane);

        logger.info("Showing contacts menu");
    }

    @FXML
    private void showCreate(ActionEvent event) {
        showPane(createPane);

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
    private void showSettings(ActionEvent event) {
        showPane(settingsPane);

        logger.info("Showing settings menu");
    }

    @FXML
    private void saveSettings(ActionEvent event) {
        try {
            contacts.setFileName(filenameField.getText());

            viewData = loadData();
            tableView.setItems(viewData);
            tableView.refresh();

            logger.info("Settings saved successfully");

            showContacts(new ActionEvent());
        }
        catch (Exception e) {
            errorLabel2.setText(e.getMessage());
            logger.error("Error saving settings: " + e.getMessage());
        }
    }

    @FXML
    private void showAbout(ActionEvent event) {
        showPane(aboutPane);

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
                    readFields(contact);

                    contacts.saveToDisk();
                    tableView.refresh();

                    logger.info(contact.getLastName() + " " + contact.getFirstName() + " - contact is edited successfully");
                }
            }
            else {
                Contact newContact = new Contact();
                readFields(newContact);

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

    public void readFields(Contact contact) throws IllegalArgumentException {
        contact.setLastName(lastnameField.getText());
        contact.setFirstName(firstnameField.getText());

        if(!phonenumberField.getText().equals(""))
            contact.setPhoneNumber(PhoneNumber.of(phonenumberField.getText()));

        if(!emailField.getText().equals(""))
            contact.setEmail(emailField.getText());

        if(!addressField.getText().equals(""))
            contact.setAddress(addressField.getText());
    }

    public void showPane(AnchorPane pane) {
        tablePane.setVisible(tablePane == pane);
        createPane.setVisible(createPane == pane);
        settingsPane.setVisible(settingsPane == pane);
        aboutPane.setVisible(aboutPane == pane);

        if(tablePane == pane) {
            menuLabel.setText("Névjegyek");
        }
        else if (createPane == pane) {
            lastnameField.setText("");
            lastnameField.setDisable(false);
            firstnameField.setText("");
            firstnameField.setDisable(false);
            phonenumberField.setText("");
            emailField.setText("");
            addressField.setText("");
            menuLabel.setText("Új névjegy hozzáadása");
            createButton.setText("Hozzáadás");
        }
        else if (settingsPane == pane) {
            menuLabel.setText("Beállítások");
            filenameField.setText(contacts.getFileName());
        }
        else if (aboutPane == pane) {
            menuLabel.setText("Készítő");
        }
    }

    public void initialize() {
        logger.info("Initializing controller...");
        setupTable();
        viewData = loadData();
        tableView.setItems(viewData);
    }

    private ObservableList<Contact> loadData() {
        try {
            contacts.loadFromDisk();

            return FXCollections.observableArrayList(contacts.getElements());
        }
        catch (Exception e) {
            logger.error(e.getMessage());

            return FXCollections.observableArrayList();
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
