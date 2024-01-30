package com.junqueira.util;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class ValidaCampos {
    private static final Tooltip tooltip = new Tooltip("Campo Obrigatório");
    public static boolean checarCampoVazio(Node... controle) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        tooltip.setStyle("-fx-background-color: linear-gradient(#FF6B6B, #FFA6A6); -fx-font-weight: bold;");
        ValidaExibeTooltip.tempoToolTip(tooltip);
        List<Node> camposFalha = new ArrayList<>();
        
        for(Node n : controle){
            if(n instanceof TextField){
                TextField tf = (TextField) n;
                tf.textProperty().addListener((observable, oldValue, newValue) -> {
                    ValidaExibeTooltip.removerCorBordaToolTip(n, tooltip);
                });
                if(tf.getText().trim().equals("")){
                    camposFalha.add(n);
                    ValidaExibeTooltip.adicionarCorBordaToolTip(n, tooltip);
                }
            }
            if(n instanceof ComboBox){
                ComboBox<?> comboBox = (ComboBox<?>) n;
                    comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    ValidaExibeTooltip.removerCorBordaToolTip(comboBox, tooltip);
		});
		if (comboBox.getValue() == null) {
                    camposFalha.add(n);
                    ValidaExibeTooltip.adicionarCorBordaToolTip(comboBox, tooltip);
                }
            }
            if(n instanceof DatePicker){
                
            }
        }
        
        return camposFalha.isEmpty();
    }
}
