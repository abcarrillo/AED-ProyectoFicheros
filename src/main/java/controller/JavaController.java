package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

public class JavaController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private TextField textRuta;

	@FXML
	private Button btnObtener;

	@FXML
	private Button btnCrear;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnMover;

	@FXML
	private TextArea textListado;

	@FXML
	private TextArea textContenido;

	@FXML
	private Button btnVer;

	@FXML
	private Button btnModificar;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		textRuta.setText(System.getProperty("user.dir").toString());
		File file = new File(textRuta.getText());

	}

	public JavaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/JavaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public GridPane getView() {
		return this.view;
	}

	@FXML
	void onCrearAction(ActionEvent event) throws IOException {
		File file = new File(textRuta.getText());
		boolean resultado = file.createNewFile();
		if (!resultado) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("No se ha creado el fichero porque ya existía.");

			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("Se ha creado el fichero.");

			alert.showAndWait();
		}
	}

	@FXML
	void onCrearCarpetAction(ActionEvent event) {
		File file = new File(textRuta.getText());
		boolean resultado = file.mkdir();
		if (!resultado) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("No se ha creado el fichero porque ya existía.");

			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("Se ha creado la carpeta.");

			alert.showAndWait();
		}
	}

	@FXML
	void onEliminarAction(ActionEvent event) throws IOException {
		File file = new File(textRuta.getText());

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmacion eliminar");
		alert.setHeaderText("Archivos.");
		alert.setContentText("¿Seguro que quieres eliminar?");

		Optional<ButtonType> result = alert.showAndWait();
		try {
			if (result.get() == ButtonType.OK) {
				if (Files.isRegularFile(file.toPath())) {
					Files.delete(file.toPath());
				} else {
					deleteDirectoryRecursion(file.toPath());
				}

				if (!Files.exists(file.toPath(), LinkOption.NOFOLLOW_LINKS)) {
					Alert alertaEliminar = new Alert(AlertType.INFORMATION);
					alertaEliminar.setTitle("Aviso");
					alertaEliminar.setHeaderText("Archivos.");
					alertaEliminar.setContentText("Se ha eliminado con éxito.");

					alertaEliminar.showAndWait();
				} else {
					Alert alertaEliminar = new Alert(AlertType.INFORMATION);
					alertaEliminar.setTitle("Aviso");
					alertaEliminar.setHeaderText("Archivos.");
					alertaEliminar.setContentText("Ha ocurrido un error al eliminar.");

					alertaEliminar.showAndWait();
				}

			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void deleteDirectoryRecursion(Path path) throws IOException {
		if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
				for (Path entry : entries) {
					deleteDirectoryRecursion(entry);
				}
			}
		}
		Files.delete(path);
	}

	@FXML
	void onModificarAction(ActionEvent event) {

		try {
			Files.writeString(Path.of(textRuta.getText()), textContenido.getText());
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("Ha habido un error intentando escribir en el archivo. \n" + ex.getMessage());

			alert.showAndWait();
		}

		onVerAction(event);
	}

	@FXML
	void onMoverAction(ActionEvent event) {
		File file = new File(textRuta.getText());

		TextInputDialog dialog = new TextInputDialog(textRuta.getText());
		dialog.setTitle("Mover archivo");
		dialog.setHeaderText("Archivos.");
		dialog.setContentText("Por favor introduzca la nueva ruta");

		Optional<String> result = dialog.showAndWait();

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> file.renameTo(new File(name)));
	}

	@FXML
	void onObtenerAction(ActionEvent event) {
		File file = new File(textRuta.getText());
		if (file.exists()) {
			try {
				textListado.setText("");
				for (String subdirectorio : file.list()) {
					textListado.setText(textListado.getText() + "\n" + subdirectorio);
				}
			} catch (Exception ex) {

			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("El fichero NO existe");

			alert.showAndWait();
		}
	}

	@FXML
	void onVerAction(ActionEvent event) {
		String contenido = "";
		try {
			contenido = Files.readString(Path.of(textRuta.getText()));
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Aviso");
			alert.setHeaderText("Archivos.");
			alert.setContentText("Ha habido un error intentando leer el archivo. \n" + ex.getMessage());

			alert.showAndWait();
		}

		textContenido.setText(contenido);
	}

}
