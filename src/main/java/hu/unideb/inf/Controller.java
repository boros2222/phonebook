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

/**
 * Class for controlling the application.
 */
public class Controller {

    /** Logger. */
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    /** AnchorPane containing everything. */
    @FXML private AnchorPane mainPane;
    /** AnchorPane for containing contacts. */
    @FXML private AnchorPane tablePane;
    /** AnchorPane for containing create menu. */
    @FXML private AnchorPane createPane;
    /** AnchorPane for containing about menu. */
    @FXML private AnchorPane aboutPane;
    /** AnchorPane for containing settings menu. */
    @FXML private AnchorPane settingsPane;
    /** Label for containing the name of current menu. */
    @FXML private Label menuLabel;
    /** Label for error text. */
    @FXML private Label errorLabel;
    /** Label for error text. */
    @FXML private Label errorLabel2;
    /** TableView for contacts. */
    @FXML private TableView tableView;
    /** TextField for firstname. */
    @FXML private TextField firstnameField;
    /** TextField for lastname. */
    @FXML private TextField lastnameField;
    /** TextField for phone number. */
    @FXML private TextField phonenumberField;
    /** TextField for email address. */
    @FXML private TextField emailField;
    /** TextField for address. */
    @FXML private TextField addressField;
    /** TextField for filename. */
    @FXML private TextField filenameField;
    /** Button for creating new contact. */
    @FXML private Button createButton;

    /** Storing contacts for the view. */
    private ObservableList<Contact> viewData;

    /** Object for storing contacts. */
    private Contacts contacts = new Contacts();

    /**
     * Showing list of contacts.
     * @param event event
     */
    @FXML
    private void showContacts(ActionEvent event) {
        showPane(tablePane);

        logger.info("Showing contacts menu");
    }

    /**
     * Showing create menu.
     * @param event event
     */
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

    /**
     * Showing settings menu.
     * @param event event
     */
    @FXML
    private void showSettings(ActionEvent event) {
        showPane(settingsPane);

        logger.info("Showing settings menu");
    }

    /**
     * Saving settings.
     * @param event event
     */
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

    /**
     * Showing about menu.
     * @param event event
     */
    @FXML
    private void showAbout(ActionEvent event) {
        showPane(aboutPane);

        logger.info("Showing about menu");
    }

    /**
     * Exiting application.
     * @param event event
     */
    @FXML
    private void exitApp(ActionEvent event) {
        logger.info("Closing app...");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Creating new or editing existing contact.
     * @param event event
     */
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

    /**
     * Showing create menu and changing it for editing.
     * @param contact Contact object we want to edit
     */
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

    /**
     * Reading data from fields to a Contact object.
     * @param contact Contact object we want to load data to
     * @throws IllegalArgumentException when error happens loading data to {@code contact}
     */
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

    /**
     * Showing a menu and hiding the others.
     * @param pane the menu we want to show
     */
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

    /**
     * Initializing controller.
     */
    public void initialize() {
        logger.info("Initializing controller...");
        setupTable();
        viewData = loadData();
        tableView.setItems(viewData);
    }

    /**
     * Loading data from disk to a list compatible with the view.
     * @return a list of contacts compatible with the view.
     */
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

    /**
     * Creating table for showing contacts.
     */
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
