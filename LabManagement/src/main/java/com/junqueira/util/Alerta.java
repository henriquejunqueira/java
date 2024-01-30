package com.junqueira.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerta {
    ButtonType btnConfirmar = new ButtonType("Confirmar");
    ButtonType btnCancelar = new ButtonType("Cancelar");
    boolean resposta;
    
    // Método para mensagem de informação
    public void msgInformacao(String mensagemComplemento){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informação sobre cadastro");
        alert.setContentText(mensagemComplemento);
        alert.showAndWait();
    }
    
    // Método para exclusão de registros
    public boolean msgConfirmaExclusao(String mensagemExclusao){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exclusão de registro");
        alert.setContentText("Deseja realmente excluir o registro " + mensagemExclusao + "?");
        alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);
        alert.showAndWait().ifPresent(b ->{
            if(b == btnConfirmar){
                resposta = true;
            }else{
                resposta = false;
            }
        });
        return resposta;
    }
}
