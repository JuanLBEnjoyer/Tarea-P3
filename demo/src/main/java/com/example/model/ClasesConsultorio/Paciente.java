package com.example.model.ClasesConsultorio;

import com.example.model.Patrones.Observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Paciente extends Persona implements Observer{

    private Collection<Cita> citasProgramadas;

    public Paciente(String nombre, String id, LocalDate fechaDeNacimiento) {
        super(nombre, id, fechaDeNacimiento);

        this.citasProgramadas = new ArrayList<>();

    }

    public Collection<Cita> getCitasProgramadas() {
        return citasProgramadas;
    }

    public void setCitasProgramadas(Collection<Cita> citasProgramadas) {
        this.citasProgramadas = citasProgramadas;
    }

    public void agregarCitaProgramada(Cita cita) {
        citasProgramadas.add(cita);
    }

    public void eliminarCitaProgramada(Cita cita) {
        citasProgramadas.remove(cita);
    }

    @Override
    public void update(String mensaje) {
        System.out.println("Notificación para " + getNombre() + ": " + mensaje);
    }

    
    
}
