package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Medico extends Persona {

    private String especialidad;
    private EstadoMedico estado;
    private final Collection<Cita> citasPendientes;

    public Medico(String nombre, String id, LocalDate fechaNacimiento, String especialidad) {
        super(nombre,id,fechaNacimiento);
        this.especialidad = especialidad;
        this.citasPendientes = new ArrayList<>();
        this.estado = EstadoMedico.ACTIVO;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Collection<Cita> getCitasPendientes() {
        return citasPendientes;
    }

    public EstadoMedico getEstado() {
        return estado;
    }

    public void cambiarEstado() {
        if (estado == EstadoMedico.ACTIVO) {
            this.estado = EstadoMedico.INACTIVO;
        } else {
            this.estado = EstadoMedico.ACTIVO;
        }
    }

    public void agregarCitaPendiente(Cita cita) {
        citasPendientes.add(cita);
    }

    public void eliminarCitaPendiente(Cita cita) {
        citasPendientes.remove(cita);
    }
    
}
