package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Medico extends Persona {

    private EstadoMedico estado;

    private final Collection<Cita> citasPendientes;

    public Medico(String nombre, String id, LocalDate fechaNacimiento) {
        super(nombre,id,fechaNacimiento);
        this.estado = EstadoMedico.ACTIVO;
        this.citasPendientes = new ArrayList<>();
    }

    public EstadoMedico getEstado(){
        return estado;
    }

    public void cambiarEstado() {
        if (estado == EstadoMedico.ACTIVO) {
            this.estado = EstadoMedico.INACTIVO;
        } else {
            this.estado = EstadoMedico.ACTIVO;
        }
    }

    public Collection<Cita> getCitasPendientes() {
        return citasPendientes;
    }
    public void agregarCitaPendiente(Cita cita) {
        citasPendientes.add(cita);
    }

    public void eliminarCitaPendiente(Cita cita) {
        citasPendientes.remove(cita);
    }    
    
}
