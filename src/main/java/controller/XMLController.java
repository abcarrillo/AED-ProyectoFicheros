package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import model.XML;

public class XMLController implements Initializable{
	
    @FXML
    private AnchorPane view;

    @FXML
    private TextField txtRuta;

    @FXML
    private TextArea txtContenido;

    @FXML
    private Button btnVerContenido;

    @FXML
    private Button btnInsertarContrato;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    private TextField txtFechaFin;

    @FXML
    private TextField txtNombreEquipo;


    @FXML
    void onEliminarAction(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Eliminar un equipo");
    	dialog.setHeaderText("Por favor, inserte el nombre exacto del equipo que quiere eliminar.");
    	dialog.setContentText("Nombre del equipo: ");
    	
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		XML.eliminarUnEquipo(this.txtRuta.getText(), result.get());
    	}
    	
    }

    @FXML
    void onGuardarArchivoAction(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Guardar archivo");
    	dialog.setHeaderText("Por favor, inserte la ruta exacta donde quiere guardar el archivo.");
    	dialog.setContentText("Ruta: ");
    	
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		XML.guardarEnRuta(this.txtRuta.getText(), result.get());
    	}
    }

    @FXML
    void onInsertarContratoAction(ActionEvent event) {
    	if(!txtRuta.getText().isEmpty() && !txtNombreEquipo.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtFechaInicio.getText().isEmpty() && !txtFechaFin.getText().isEmpty()) {
    		XML.insertarContrato(this.txtRuta.getText(), txtNombreEquipo.getText(), txtNombre.getText(), txtFechaInicio.getText(), txtFechaFin.getText());
    	}
    	
    }
    
    @FXML
    void onModificarCopasAction(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Modificar copas de un equipo");
    	dialog.setHeaderText("Por favor, inserte el nombre exacto del equipo que quiere modificar sus copas.");
    	dialog.setContentText("Nombre del equipo: ");
    	Optional<String> result = dialog.showAndWait();
    	
    	if (result.isPresent()){
    		if(!txtRuta.getText().isEmpty() ) {
    			TextInputDialog dialog2 = new TextInputDialog("");
    	    	dialog2.setTitle("Modificar copas de un equipo");
    	    	dialog2.setHeaderText("Por favor, inserte el nuevo numero de copas");
    	    	dialog2.setContentText("Copas nuevas: ");
    	    	
    	    	Optional<String> result2 = dialog2.showAndWait();
    	    	if (result2.isPresent()){
    	    		XML.cambiarCopasGanadas(txtRuta.getText(), result.get(), result2.get());
    	    	}
        	}
    	}
    	
    }

    @FXML
    void onVerContenidoAction(ActionEvent event) {
    	String contenido = XML.listarEquipos(this.txtRuta.getText());
    	txtContenido.setText(contenido);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		File file = new File("src/main/resources/ficheros/Equipos.xml");
		String absolutePath = file.getAbsolutePath();
		txtRuta.setText(absolutePath);
	}
	
	public AnchorPane getView() {
		return this.view;
	}

	public XMLController () throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/XMLView.fxml"));
		loader.setController(this);
		loader.load();
	}
}
