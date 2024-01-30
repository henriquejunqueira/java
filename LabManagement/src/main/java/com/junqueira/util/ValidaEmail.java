package com.junqueira.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class ValidaEmail {
	private static Pattern pattern;
	private static Matcher matcher;

	private static final Tooltip toolTip = new Tooltip("Email Inválido");

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	public static boolean validate(final Node node, String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		
		TextField textField = (TextField) node;
		
		if (matcher.matches()) {
			ValidaExibeTooltip.removerCorBordaToolTip(textField, toolTip);
			return true;
			
		} else {
			ValidaExibeTooltip.adicionarCorBordaToolTip(textField, toolTip);
			
			return false;
		}
		
	}
	

	
}
