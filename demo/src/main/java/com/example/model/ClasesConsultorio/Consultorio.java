package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.model.Patrones.Iterador.Iterador;
import com.example.model.Patrones.Iterador.IteradorMedicosActivos;


public class Consultorio {

    private String nombre;
    private String direccion;
    private LocalDate fechaCreacion;
    private Collection<Paciente> pacientes;
    private Collection<Medico> medicos;
    private static Consultorio instanciaUnica;
    private AdministradorCitas administradorCitas;
    private List<Cita> historialCitas;


    private Consultorio(String nombre, String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaCreacion = LocalDate.now();
        this.pacientes = new ArrayList<Paciente>();
        this.medicos = new ArrayList<Medico>();
        this.historialCitas = new ArrayList<Cita>();
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


    public Collection<Medico> getMedicos() {
        return medicos;
    }


    public void setMedicos(Collection<Medico> medicos) {
        this.medicos = medicos;
    }

    public void agregarCitaAlHistorial(Cita cita) {
        historialCitas.add(cita);
    }

    public List<Cita> getHistorialCitas() {
        return historialCitas;
    }

    //Metodo del patron singleton 

    public static Consultorio obtenerInstancia(String nombre, String direccion) {
        if (instanciaUnica == null) {
            instanciaUnica = new Consultorio(nombre, direccion);
        }
        return instanciaUnica;
    }

    public Paciente buscarPaciente(String id) {
        return pacientes.stream()
                .filter(paciente -> paciente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Medico buscarMedico(String id) {
        return medicos.stream()
                .filter(medico -> medico.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void validarPacienteExiste(Paciente paciente) {
        if (pacientes.contains(paciente)) {
            throw new PersonaExistenteException("El paciente con ID " + paciente.getId() + " ya existe");
        }
    }

    private void validarMedicoExiste(Medico medico) {
        if (medicos.contains(medico)) {
            throw new PersonaExistenteException("El médico con ID " + medico.getId() + " ya existe");
        }
    }

   public void agregarPaciente(Paciente paciente) {
    validarPacienteExiste(paciente);
    pacientes.add(paciente);
}

public void agregarMedico(Medico medico) {
    validarMedicoExiste(medico);
    medicos.add(medico);
}

    public void eliminarPaciente(Paciente paciente) {
        if (pacientes.contains(paciente)) {
            pacientes.remove(paciente);
        } else {
            throw new IllegalArgumentException("El paciente con ID " + paciente.getId() + " no existe.");
        }
    }

    public void eliminarMedico(Medico medico) {
        if (medicos.contains(medico)) {
            medicos.remove(medico);
        } else {
            throw new IllegalArgumentException("El médico con ID " + medico.getId() + " no existe.");
        }
    }

public AdministradorCitas crearAdministrador(){
    this.administradorCitas = new AdministradorCitas(this);
    return this.administradorCitas;
}

public Iterador<Medico> crearIteradorMedicosActivos() {
    return new IteradorMedicosActivos(medicos);
}

private Optional<Cita> buscarCitaMedico(Cita cita, Medico medico){
    return medico.getCitasPendientes().stream().filter(c->c.equals(cita)).findAny();
}

public boolean valdidarCitaMedico(Cita cita, Medico medico){

    return buscarCitaMedico(cita, medico).isPresent();

}

private Optional<Cita> buscarCitaPaciente(Cita cita, Paciente paciente){
    return paciente.getCitasProgramadas().stream().filter(c->c.equals(cita)).findAny();
}

public boolean valdidarCitaPaciente(Cita cita, Paciente paciente){

    return buscarCitaPaciente(cita, paciente).isPresent();

}




    
}
