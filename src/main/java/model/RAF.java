package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RAF {
	
	StringProperty id = new SimpleStringProperty();
	StringProperty codigoLiga = new SimpleStringProperty();
	StringProperty copasGanadas = new SimpleStringProperty();
	BooleanProperty internacional = new SimpleBooleanProperty();
	StringProperty localidad = new SimpleStringProperty();
	StringProperty contenido = new SimpleStringProperty();
	StringProperty nombreEquipo = new SimpleStringProperty();
	
	
	

	public final StringProperty nombreEquipoProperty() {
		return this.nombreEquipo;
	}

	public final String getNombreEquipo() {
		return this.nombreEquipoProperty().get();
	}

	public final void setNombreEquipo(final String nombreEquipo) {
		this.nombreEquipoProperty().set(nombreEquipo);
	}

	public final StringProperty codigoLigaProperty() {
		return this.codigoLiga;
	}

	public final String getCodigoLiga() {
		return this.codigoLigaProperty().get();
	}

	public final void setCodigoLiga(final String codigoLiga) {
		this.codigoLigaProperty().set(codigoLiga);
	}

	public final BooleanProperty internacionalProperty() {
		return this.internacional;
	}

	public final boolean isInternacional() {
		return this.internacionalProperty().get();
	}

	public final void setInternacional(final boolean internacional) {
		this.internacionalProperty().set(internacional);
	}

	public final StringProperty copasGanadasProperty() {
		return this.copasGanadas;
	}

	public final String getCopasGanadas() {
		return this.copasGanadasProperty().get();
	}

	public final void setCopasGanadas(final String copasGanadas) {
		this.copasGanadasProperty().set(copasGanadas);
	}

	public final StringProperty localidadProperty() {
		return this.localidad;
	}

	public final String getLocalidad() {
		return this.localidadProperty().get();
	}

	public final void setLocalidad(final String localidad) {
		this.localidadProperty().set(localidad);
	}
	
	public StringProperty contenidoProperty() {
		return this.contenido;
	}

	public String getContenido() {
		return this.contenidoProperty().get();
	}

	public void setContenido(final String contenido) {
		this.contenidoProperty().set(contenido);
	}

	public StringProperty idProperty() {
		return this.id;
	}

	public String getId() {
		return this.idProperty().get();
	}

	public void setId(final String id) {
		this.idProperty().set(id);
	}
}
