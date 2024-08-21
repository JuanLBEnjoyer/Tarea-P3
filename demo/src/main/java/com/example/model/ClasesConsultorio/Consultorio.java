package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import com.example.model.Patrones.Iterador.Iterador;
import com.example.model.Patrones.Iterador.IteradorMedicosActivos;

public class Consultorio {

    private String nombre;
    private String direccion;
    private LocalDate fechaCreacion;
    private Collection<Paciente> pacientes;
    private Collection<Medico> doctores;
    private static Consultorio instanciaUnica;
    private AdministradorCitas administradorCitas;


    private Consultorio(String nombre, String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaCreacion = LocalDate.now();
        this.pacientes = new ArrayList<Paciente>();
        this.doctores = new ArrayList<Medico>();
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Collection<Paciente> getPacientes() {
        return pacientes;
    }


    public void setPacientes(Collection<Paciente> pacientes) {
        this.pacientes = pacientes;
    }


    public Collection<Medico> getDoctores() {
        return doctores;
    }


    public void setDoctores(Collection<Medico> doctores) {
        this.doctores = doctores;
    }


    public static Consultorio getInstanciaUnica() {
        return instanciaUnica;
    }

    public static Consultorio obtenerInstancia(String nombre, String direccion) {
        if (instanciaUnica == null) {
            instanciaUnica = new Consultorio(nombre, direccion);
        }
        return instanciaUnica;
    }

    public Persona buscarPersona(String id, Collection<Persona> lista){
        Predicate<Persona> condicion = p -> p.getId().equals(id);
        Optional<Persona> persona = lista.stream().filter(condicion).findFirst();
        return persona.orElse(null);
    }

    public Optional<Persona> verificarPersona(Persona persona, Collection<Persona> lista){
        Predicate<Persona> condicion = p->p.getId().equals(persona.getId());
        return lista.stream().filter(condicion).findAny();
    }

    private void validarPersonaExiste(Persona persona, Collection<Persona> lista) {
        boolean existePersona = verificarPersona(persona, lista).isPresent();
        if (existePersona) {
            throw new PersonaExistenteException("La persona con ID " + persona.getId() + " ya existe");
        }
    }

    public void agregarPersona(Persona persona, Collection<Persona> lista){
        validarPersonaExiste(persona, lista);
        lista.add(persona);
    }

    public void eliminarPersona(Persona persona, Collection<Persona> lista) {
        lista.remove(persona);
    }

    public Iterador<Medico> crearIteradorMedicosActivos() {
    return new IteradorMedicosActivos(doctores);
}

public AdministradorCitas crearAdministrador(){
    this.administradorCitas = new AdministradorCitas(this);
    return this.administradorCitas;
}

public Optional<Cita> buscarCitaMedico(Cita cita, Medico medico){
    return medico.getCitasPendientes().stream().filter(c->c.equals(cita)).findAny();
}

public boolean valdidarCitaMedico(Cita cita, Medico medico){

    return buscarCitaMedico(cita, medico).isPresent();

}

public Optional<Cita> buscarCitaPaciente(Cita cita, Paciente paciente){
    return paciente.getCitasProgramadas().stream().filter(c->c.equals(cita)).findAny();
}

public boolean valdidarCitaPaciente(Cita cita, Paciente paciente){

    return buscarCitaPaciente(cita, paciente).isPresent();

}




    
}
