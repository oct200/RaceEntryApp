    package org.example;
    
    import javafx.application.Platform;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.input.MouseEvent;
    import javafx.stage.Stage;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.model.Cursa;
    import org.model.Inscriere;
    import org.model.Participant;
    import org.model.User;
    import org.service.AppException;
    import org.service.IClientObserver;
    import org.service.IService;
    
    import java.io.IOException;
    import java.util.List;
    
    public class Principal implements IClientObserver{
        IService proxy;
        @FXML
        public ComboBox<String> comboBoxEchipe;
        @FXML
        public ComboBox<String> comboBoxCapacitateMotor;
        @FXML
        public TableView<Participant> tableViewParticipanti;
        @FXML
        public TableView<Cursa> tableViewCurse;
    
        private static final Logger logger = LogManager.getLogger(Principal.class);
    
        public void setServiceAplicatie(IService service) {
            this.proxy = service;
            initializeComponents();
        }
    
        private void initializeComponents() {
    
            try {
                initializareCombobox();
                initTabele();
                loadTabelParticipanti();
                loadTabelCurse();
    
                comboBoxEchipe.setOnAction(e -> loadTabelParticipanti());
                comboBoxCapacitateMotor.setOnAction(e -> {
                    loadTabelCurse();
                    tableViewParticipanti.getSelectionModel().clearSelection();
                });
            }
            catch(Exception e){
                System.out.println("SAFDer");
                logger.error(e);
            }
        }
    
        private void initTabele() {
            TableColumn<Participant, Integer> idParticipant = new TableColumn<>("ID");
            idParticipant.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
    
            TableColumn<Participant, String> numeParticipant = new TableColumn<>("Nume");
            numeParticipant.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nume"));
    
            TableColumn<Participant, String> echipaParticipant = new TableColumn<>("Echipa");
            echipaParticipant.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("echipa"));
    
    
            tableViewParticipanti.getColumns().clear();
            tableViewParticipanti.getColumns().addAll(idParticipant, numeParticipant, echipaParticipant);
    
    
            // === CURSE TABLE ===
            TableColumn<Cursa, Integer> idCursa = new TableColumn<>("ID");
            idCursa.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
    
            TableColumn<Cursa, Integer> capacitateCursa = new TableColumn<>("numarParticipanti");
            capacitateCursa.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("numarParticipanti"));
    
            TableColumn<Cursa, String> capMotor = new TableColumn<>("capMotor");
            capMotor.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("capMotor"));
    
            tableViewCurse.getColumns().clear();
            tableViewCurse.getColumns().addAll(idCursa, capacitateCursa,capMotor);
    
        }
    
    
    
    
        private void initializareCombobox() {
            try {
                logger.info("Incepere initializareCombobox");
                List<String> listaEchipe = proxy.getAllTeams();
                List<String> listaCap = new java.util.ArrayList<>(proxy.getCapacitati().stream().map(Object::toString).toList());
                listaCap.add(0, "Toate");
                listaEchipe.add(0, "Toate");
                comboBoxEchipe.setItems(FXCollections.observableArrayList(listaEchipe));
                comboBoxCapacitateMotor.setItems(FXCollections.observableArrayList(listaCap));
                comboBoxEchipe.getSelectionModel().selectFirst();
                comboBoxCapacitateMotor.getSelectionModel().selectFirst();
                logger.info("final initializareCombobox");
            }
            catch (Exception e){
                logger.error(e);
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",e.getMessage());
            }
        }
    
        private void loadTabelParticipanti() {
            logger.info("Incepere loadTabelParticipanti");
            try {
                List<Participant> participanti;
                if ("Toate".equals(comboBoxEchipe.getValue())) {
                    participanti = proxy.getAllParticipanti();
                } else {
                    participanti = proxy.getAllParticipantiByEchipa(comboBoxEchipe.getValue());
                }
                ObservableList<Participant> data = FXCollections.observableArrayList(participanti);
                tableViewParticipanti.setItems(data);
                logger.info("final loadTabelParticipanti");
            } catch (Exception ex) {
                logger.error("Eroare in loadTabelParticipanti: ", ex);
            }
        }
    
        @FXML
        protected void selectComboCap(){
            logger.info("Incepere selectComboCap");
            loadTabelCurse();
        }
    
        private void loadTabelCurse() {
            logger.info("Incepere loadTabelCurse");
            try {
                List<Cursa> curse;
                if ("Toate".equals(comboBoxCapacitateMotor.getValue())) {
                    curse = proxy.getAllCurse();
                } else {
                    int capacitate = Integer.parseInt(comboBoxCapacitateMotor.getValue());
                    curse = proxy.getAllCurseByCapMotor(capacitate);
                }
                ObservableList<Cursa> data = FXCollections.observableArrayList(curse);
                tableViewCurse.setItems(data);
                logger.info("final loadTabelCurse");
            } catch (Exception ex) {
                logger.error("Eroare in loadTabelCurse: ", ex);
            }
        }
    
        @FXML
        private void tabelParticipantiClick(MouseEvent event) {
            logger.info("starting tabelParticipantiClick");
            try {
                Participant participant = tableViewParticipanti.getSelectionModel().getSelectedItem();
                if (participant != null) {
                    List<Cursa> curse = proxy.getCurseForParticipant(participant);
                    tableViewCurse.setItems(FXCollections.observableArrayList(curse));
                } else {
                    logger.info("No row selected in tabelParticipantiClick");
                }
            } catch (Exception ex) {
                logger.error("Eroare in tabelParticipantiClick: ", ex);
            }
        }
    
        @FXML
        private void buttonLogOut_Click() {
            try {
                proxy.logout(new User(1L,"sf","sd"));
                Stage primaryStage = (Stage) comboBoxEchipe.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("autentificare.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                AutentificareController comntroller = fxmlLoader.getController();
                comntroller.setServiceAplicatie(proxy);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            catch (Exception ex) {
                logger.error("Eroare in tabelParticipantiClick: ", ex);
            }
        }
    
            @FXML
            private void buttonInscrieParticipant_Click() {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("adaugareParticipant.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    // Get the controller and pass the service
                    AdaugareParticipant controller = fxmlLoader.getController();
                    controller.setServiceAplicatie(proxy);

                    // Create a new stage (window)
                    Stage newStage = new Stage();
                    newStage.setTitle("Adaugare Participant");
                    newStage.setScene(scene);
                    newStage.show();
                } catch (IOException ex) {
                    logger.error("Eroare la deschiderea ferestrei de adaugare participant: ", ex);
                    MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", ex.getMessage());
                }

            }
    
        @FXML
        protected void buttonAdaugareParticipantLaCursa_Click() {
            logger.info("starting buttonAdaugareParticipantLaCursa_Click");
            try {
                Participant participant = tableViewParticipanti.getSelectionModel().getSelectedItem();
                Cursa cursa = tableViewCurse.getSelectionModel().getSelectedItem();
                if (participant != null && cursa != null) {
                    proxy.adaugaInscriere(participant, cursa);
                    //MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Adaugat","Participantul a fost adaugat la cursa");
                    logger.info("final buttonAdaugareParticipantLaCursa_Click");
                } else {
                    logger.info("Rows not selected for buttonAdaugareParticipantLaCursa_Click");
                }
            } catch (Exception ex) {
                logger.error("Eroare in buttonAdaugareParticipantLaCursa_Click: ", ex);
            }
        }

        @Override
        public void refresh() {
            Platform.runLater(() -> {
                loadTabelParticipanti();
                loadTabelCurse();
            });
        }
    }
    
    
