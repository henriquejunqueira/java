package com.junqueira.util;

import java.lang.reflect.Field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class ValidaExibeTooltip {
	
	private static final String VALIDACAO_ESTILO_BORDA = "-fx-border-color: #FF0000;";
	
	/**
	 * *******ADICIONAR E REMOVER ESTILOS DA BORDA********
	 */
	static void adicionarCorBordaToolTip(Node n, Tooltip t) {
		Tooltip.install(n, t);
		n.setStyle(VALIDACAO_ESTILO_BORDA);
	}

	static void removerCorBordaToolTip(Node n, Tooltip t) {
		Tooltip.uninstall(n, t);
		n.setStyle("-fx-background-radius: 20");
	}
	
	public static void mensagem(Node n, Tooltip t) {
		Tooltip.install(n, t);
	}	



	/**
	 * *********** Exibição do ToolTip (duração E comportamento) ************
	 */
	public static void tempoToolTip(Tooltip tooltip) {
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println(e);
		}
	}
}
