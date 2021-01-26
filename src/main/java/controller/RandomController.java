package controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import model.RAF;

public class RandomController implements Initializable {

	public RAF raf = new RAF();
	File fichero = new File("src/main/resources/ficheros/datos.dat");

	@FXML
	private GridPane view;

	@FXML
	private TextArea txtContenido;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtLocalidad;

	@FXML
	private TextField txtCopas;

	@FXML
	private CheckBox checkLocalidad;

	@FXML
	private Button btnInsertar;

	@FXML
	private Button btnVisualizar;

	@FXML
	private Button btnEquipos;

	@FXML
	private Button btnModificar;

	@FXML
	void onEquiposAction(ActionEvent event) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Codigo del equipo");
		dialog.setHeaderText("¿Qué código de equipo buscas?");
		dialog.setContentText("Introduce el código que quieres buscar:");

		String codigo = null;

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			codigo = result.get();
		}

		@SuppressWarnings("resource")
		RandomAccessFile ficheroRandom = new RandomAccessFile(fichero, "r");

		Charset charset = StandardCharsets.UTF_16;

		@SuppressWarnings("unused")
		char separador = ',';
		String s1 = "";
		String contenido = "";

		if (ficheroRandom.length() == 0)
			txtContenido.setText("No hay datos a visualizar");
		else {
			ficheroRandom.seek((Integer.valueOf(codigo) - 1) * 231);
			contenido += ("ID: " + ficheroRandom.readInt());

			contenido += ficheroRandom.readChar();

			byte[] arr1 = new byte[80];
			ficheroRandom.readFully(arr1);
			s1 = new String(arr1, charset);
			contenido += ("Nombre Equipo: " + s1);

			contenido += ficheroRandom.readChar();

			byte[] arr2 = new byte[10];
			ficheroRandom.readFully(arr2);
			s1 = new String(arr2, charset);
			contenido += ("Codigo Liga: " + s1);

			contenido += ficheroRandom.readChar();

			byte[] arr3 = new byte[120];
			ficheroRandom.readFully(arr3);
			s1 = new String(arr3, charset);
			contenido += ("Localidad: " + s1);

			contenido += ficheroRandom.readChar();

			contenido += ("Copas ganadas: " + ficheroRandom.readInt());

			contenido += ficheroRandom.readChar();

			contenido += ("Internacional: " + ficheroRandom.readBoolean());

			contenido += ficheroRandom.readChar();

		}
		txtContenido.setText(contenido);
	}

	@FXML
	void onInsertarAction(ActionEvent event) throws IOException {
		String[] datos = new String[5];
		datos[0] = (txtNombre.textProperty().get() + "                                                  ").substring(0,
				40);
		datos[1] = (txtCodigo.textProperty().get() + "     ").substring(0, 5);
		datos[2] = (txtLocalidad.textProperty().get()
				+ "                                                                                     ").substring(0,
						60);
		datos[3] = txtCopas.textProperty().get();
		datos[4] = checkLocalidad.selectedProperty().get()+"";

		@SuppressWarnings("resource")
		RandomAccessFile ficheroRandom = new RandomAccessFile(fichero, "rw");
		int id = 0;
		char separador = ',';

		if (ficheroRandom.length() == 0)
			id = 1;
		else {
			ficheroRandom.seek(ficheroRandom.length() - 231);
			id = ficheroRandom.readInt() + 1;
			ficheroRandom.seek(ficheroRandom.length());
		}

		ficheroRandom.writeInt(id);
		ficheroRandom.writeChar(separador);
		// nombre
		ficheroRandom.writeChars(datos[0]);
		ficheroRandom.writeChar(separador);
		// liga
		ficheroRandom.writeChars(datos[1]);
		ficheroRandom.writeChar(separador);
		// localidad
		ficheroRandom.writeChars(datos[2]);
		ficheroRandom.writeChar(separador);
		// Copas
		ficheroRandom.writeInt(Integer.parseInt(datos[3]));
		ficheroRandom.writeChar(separador);
		// Internacional
		ficheroRandom.writeBoolean(Boolean.parseBoolean(datos[4]));
		ficheroRandom.writeChar(separador);

	}

	@FXML
	void onModificarAction(ActionEvent event) {

		System.out.println("\nEste método aún no funciona!!");

		/*
		 * try {
		 * 
		 * RandomAccessFile ficheroRandom = new RandomAccessFile(fichero, "rw");
		 * 
		 * TextInputDialog CodigoR = new TextInputDialog();
		 * CodigoR.setTitle("Codigo de equipo");
		 * CodigoR.setHeaderText("¿Qué código de equipo buscas?");
		 * CodigoR.setContentText("Introduce el código que quieres buscar:");
		 * 
		 * String codigo = null; String copasR = null;
		 * 
		 * Optional<String> codigoResult = CodigoR.showAndWait(); if
		 * (codigoResult.isPresent()) { codigo = codigoResult.get(); }
		 * 
		 * ficheroRandom.seek((Integer.valueOf(codigo) - 1) * 51 + 42);
		 * 
		 * TextInputDialog copas = new TextInputDialog();
		 * copas.setTitle("Copas nuevas");
		 * copas.setHeaderText("¿Cuales son las copas nuevas?");
		 * copas.setContentText("Introduce las copas nuevas: ");
		 * 
		 * Optional<String> copasResult = copas.showAndWait(); if
		 * (copasResult.isPresent()) { copasR = copasResult.get(); }
		 * 
		 * ficheroRandom.writeInt(Integer.valueOf(copasR));
		 * 
		 * ficheroRandom.close();
		 * 
		 * } catch (Exception e1) { e1.printStackTrace(); }
		 */
	}

	@FXML
	void onVisualizarAction(ActionEvent event) throws IOException {
		@SuppressWarnings("resource")
		RandomAccessFile ficheroRandom = new RandomAccessFile(fichero, "r");

		Charset charset = StandardCharsets.UTF_16;

		@SuppressWarnings("unused")
		char separador = ',';
		String s1 = "";
		String contenido = "";

		if (ficheroRandom.length() == 0)
			txtContenido.setText("No hay datos");
		else {
			while (ficheroRandom.getFilePointer() < fichero.length()) {

				contenido += (ficheroRandom.readInt());
				contenido += ficheroRandom.readChar();

				byte[] arr1 = new byte[80];
				ficheroRandom.readFully(arr1);
				s1 = new String(arr1, charset);
				contenido += (s1);

				contenido += ficheroRandom.readChar();

				byte[] arr2 = new byte[10];
				ficheroRandom.readFully(arr2);
				s1 = new String(arr2, charset);
				contenido += (s1);

				contenido += ficheroRandom.readChar();

				byte[] arr3 = new byte[120];
				ficheroRandom.readFully(arr3);
				s1 = new String(arr3, charset);
				contenido += (s1);

				contenido += ficheroRandom.readChar();

				contenido += (ficheroRandom.readInt());

				contenido += ficheroRandom.readChar();

				contenido += (ficheroRandom.readBoolean());

				contenido += ficheroRandom.readChar() + "\n";

			}
			txtContenido.setText(contenido);
		}
	}

	@FXML
	void onCheckedAction(ActionEvent event) {
		System.out.println(checkLocalidad.selectedProperty().get());
	}

	public RandomController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RandomView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public GridPane getView() {
		return this.view;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		raf.copasGanadasProperty().bindBidirectional(txtCopas.textProperty());
		raf.internacionalProperty().bindBidirectional(checkLocalidad.selectedProperty());
		raf.contenidoProperty().bindBidirectional(txtContenido.textProperty());
		raf.nombreEquipoProperty().bindBidirectional(txtNombre.textProperty());
		raf.codigoLigaProperty().bindBidirectional(txtCodigo.textProperty());
		raf.localidadProperty().bindBidirectional(txtLocalidad.textProperty());

	}
}
