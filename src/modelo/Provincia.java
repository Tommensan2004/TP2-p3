package modelo;

import java.util.Objects;

public class Provincia {
	private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private int region; // Identificador de la región
    
    public Provincia(int id, String nombre, double latitud, double longitud) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.region = 0;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Provincia provincia = (Provincia) obj;
        return id == provincia.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

