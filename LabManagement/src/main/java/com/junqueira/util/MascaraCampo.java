package com.junqueira.util;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class MascaraCampo {
	/*--------------------------------------------------------------
	 * 
	 *                     MÉTODOS DE APOIO
	 * 
	 *-------------------------------------------------------------*/

	// ----> Ajustando ao tamanho máximo indicado
	public static void maxField(TextField textField, Integer tamanho) {
		textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null || newValue.length() > tamanho) {
				textField.setText(oldValue);
			}
		});
	}

	// ----> Posicionar após ultimo caracter
	private static void positionCaret(TextField textField) {
		Platform.runLater(() -> {
			if (textField.getText().length() != 0) {
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	// ----> Aceitar somente números
	public static String somenteValorNumerico(TextField field) {
		String result = field.getText();
		if (result == null) {
			return null;
		}
		return result.replaceAll("[^0-9]", "");
	}

	// ----> Retirar caracteres como - / etc e pegar somente os numeros
	public static Long converterParaLong(String string) {
		String textoDigitado = string;
		textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
		if (textoDigitado.length() > 0)
			return Long.parseLong(textoDigitado);
		else
			return 0L;
	}

	/*--------------------------------------------------------------
	 * 
	 *                     MÁSCARAS PERSONALIZADAS
	 * 
	 *-------------------------------------------------------------*/


	/*---------------------*/
	/*-------- NUMERO --------*/
	/*---------------------*/
	public static void mskNumero(TextField textField) {
		MascaraCampo.maxField(textField, 9);
		textField.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
			String textoDigitado = textField.getText();
			textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
			textField.setText(textoDigitado);
			MascaraCampo.positionCaret(textField);
		});
	}
	
	/*---------------------*/
	/*------ TELEFONE -----*/
	/*---------------------*/
	public static void mskTelefone(TextField textField) {
		// -- Determina o tamanho máximo do campo
		MascaraCampo.maxField(textField, 14);

		textField.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
			try {
				// Armazena na variável textoDigitado o valor do TextField
				String textoDigitado = textField.getText();

				// Configura o recebimento de apenas números 0 a 9
				// ^ representa negação, ou seja, se for diferente de 0 a 9
				// Substitui por vazio
				textoDigitado = textoDigitado.replaceAll("[^0-9]", "");

				// Captura o tamanho do texto digitado
				int tamanhoTexto = textoDigitado.length();

				// Acrescenta o parenteses ao DDD
				// \\d{2})(\\d -> busca pelos dois primeiros digitos
				// ($1) insere o parentes no primeiro bloco de 2 digitos
				// $2 segundo bloco
				textoDigitado = textoDigitado.replaceFirst("(\\d{2})(\\d)", "($1)$2");

				// Acrescenta o hífen entre o prefixo e número do telefone
				// (\\d{2})(\\d) -> busca no primeiro bloco 4 digitos
				// $1-$2 -> acrescenta o hífen entre o primeiro e o segundo
				// bloco
				textoDigitado = textoDigitado.replaceFirst("(\\d{4})(\\d)", "$1-$2");

				// Verifica se o tamanho do texto é maior que 10, neste caso
				// seria um celular
				// Dessa forma o hífen trocaria de posicao
				if (tamanhoTexto > 10) {
					textoDigitado = textoDigitado.replaceAll("-", "");
					textoDigitado = textoDigitado.replaceFirst("(\\d{5})(\\d)", "$1-$2");
				}

				// Verifica a posicao para inserir o Hífen do telefone
				// Seta o texto já configurando no TextField
				textField.setText(textoDigitado);

				// Posiciona após ultimo caracter
				MascaraCampo.positionCaret(textField);

			} catch (Exception ex) {
				System.out.println("Máscara Telefone - erro: " + ex);
			}
		});
	}

	/*---------------------*/
	/*-------- CEP --------*/
	/*---------------------*/
	public static void mskCep(TextField textField) {
		MascaraCampo.maxField(textField, 9);
		textField.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
			String textoDigitado = textField.getText();
			textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
			textoDigitado = textoDigitado.replaceFirst("(\\d{5})(\\d)", "$1-$2");
			textField.setText(textoDigitado);
			MascaraCampo.positionCaret(textField);
		});
	}

	/*---------------------*/
	/*-------- CPF --------*/
	/*---------------------*/
	public static void mskCpf(TextField textField) {
		MascaraCampo.maxField(textField, 14);
		textField.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
			String textoDigitado = textField.getText();
			textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
			textoDigitado = textoDigitado.replaceFirst("(\\d{3})(\\d)", "$1.$2");
			textoDigitado = textoDigitado.replaceFirst("(\\d{3})(\\d)", "$1.$2");
			textoDigitado = textoDigitado.replaceFirst("(\\d{3})(\\d)", "$1-$2");
			try {
				textField.setText(textoDigitado);
				MascaraCampo.positionCaret(textField);
			} catch (Exception ex) {
			}
		});
	}

	/*----------------------*/
	/*-------- CNPJ --------*/
	/*----------------------*/
	public static void mskCnpj(TextField textField) {
		MascaraCampo.maxField(textField, 18);
		textField.lengthProperty().addListener((observableValue, oldValue, newValue) -> {
			String textoDigitado = textField.getText();
			textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
			textoDigitado = textoDigitado.replaceFirst("(\\d{2})(\\d)", "$1.$2");
			textoDigitado = textoDigitado.replaceFirst("(\\d{3})(\\d)", "$1.$2");
			textoDigitado = textoDigitado.replaceFirst("(\\d{3})(\\d)", "$1/$2");
			textoDigitado = textoDigitado.replaceFirst("(\\d{4})(\\d)", "$1-$2");
			textField.setText(textoDigitado);
			MascaraCampo.positionCaret(textField);
		});
	}

	/*---------------------------*/
	/*-------- MONETÁRIO --------*/
	/*--------------------------*/	
	public static void mskMonetario(final TextField textField) {

		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
			String textoDigitado = textField.getText();
			textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
			textoDigitado = textoDigitado.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
			textoDigitado = textoDigitado.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
			textoDigitado = textoDigitado.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
			textoDigitado = textoDigitado.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
			textoDigitado = textoDigitado.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
			textField.setText(textoDigitado);
			MascaraCampo.positionCaret(textField);
			textField.textProperty().addListener((ChangeListener<String>) new ChangeListener<String>() {

				public void changed(ObservableValue<? extends String> observableValue, String oldValue,
						String newValue) {
					if (newValue.length() > 17) {
						textField.setText(oldValue);
					}
				}
			});
		});
		textField.focusedProperty().addListener((observableValue, aBoolean, fieldChange) -> {
			int length;
			if (!(fieldChange || (length = textField.getText().length()) <= 0 || length >= 3)) {
				textField.setText(textField.getText() + "00");
				System.out.println("TAMANHO DO CAMPO: " + textField.getText().length());
			}
		});
	}
}
