package com.junqueira.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class menuController implements Initializable {
    
    private Label label;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnUsuario;
    @FXML
    private Button btnCliente;
    @FXML
    private Button btnRelatorio;
    @FXML
    private Button btnSobre;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sairSistema(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void abrirJanela(ActionEvent event) throws IOException {
        Button botao = (Button) event.getSource();
        
        switch(botao.getText()){
            case "Usuário":
                abrirJanela("/fxml/usuarioView.fxml");
                break;
            case "Cliente":
                abrirJanela("/fxml/clienteView.fxml");
                break;
            case "Relatório":
                abrirJanela("/fxml/servicoView.fxml");
                break;
            case "Sobre":
                abrirJanela("/fxml/sobreView.fxml");
                break;
        }
    }
    
    private void abrirJanela(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Image applicationIcon = new Image(getClass().getResourceAsStream("/images/logoar2.png"));
        stage.getIcons().add(applicationIcon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
