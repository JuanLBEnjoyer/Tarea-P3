package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.example.model.Patrones.Iterador.Iterador;
import com.example.model.Patrones.Observer.Observer;
import com.example.model.Patrones.Observer.Sujeto;

public class AdministradorCitas implements Sujeto {

    private Collection<Observer> observers;
    private final Consultorio consultorio;
    private final Random random;

    public AdministradorCitas(Consultorio consultorio){
        this.consultorio = consultorio;
        this.observers = new ArrayList<>();
        this.random = new Random();
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(String mensaje) {
        observers.forEach(observer -> observer.update(mensaje));
    }

    //Metodo que develve un true o false dependiendo si el observer esta registrado o no

    public boolean contieneObserver(Observer observer) {
        return observers.contains(observer);
    }

    //Metodo que notifica a los observers si tienen una cita proxima

    @Override

    public void verficarProximasCitasPaciente(Paciente paciente) {
        LocalDate hoy = LocalDate.now();
        paciente.getCitasProgramadas().stream()
                .filter(cita -> cita.getFechaHoraCita().toLocalDate().isEqual(hoy.plusDays(1)))
                .forEach(cita -> notificarObservers("Recordatorio: Tienes una cita mañana. Motivo: " + cita.getMotivo()));
    }

    //Metodo para programar una cita donde primero se elige un medico al azar para luego crearla y 
    //agregarla a sus respectivas listas

    public Cita programarCita(LocalDateTime fechaHoraCita, Paciente paciente, String motivo, String salaCita) {
        Iterador<Medico> iteradorMedicos = consultorio.crearIteradorMedicosActivos();

        if (!iteradorMedicos.hasNext()) {
            throw new IllegalStateException("No hay medicos activos disponibles para programar citas.");
        }

        List<Medico> medicosActivos = new ArrayList<>();
        while (iteradorMedicos.hasNext()) {
            medicosActivos.add(iteradorMedicos.next());
        }

        Medico medico = medicosActivos.get(random.nextInt(medicosActivos.size()));

        if (verificarCruceCitas(fechaHoraCita, paciente, medico)) {
            throw new IllegalArgumentException("No se puede programar la cita porque se cruza con otra cita.");
        }

        Cita nuevaCita = new Cita(fechaHoraCita, paciente, medico, motivo, salaCita);

        paciente.agregarCitaProgramada(nuevaCita);
        medico.agregarCitaPendiente(nuevaCita);

        if (!contieneObserver(paciente)) {
            addObserver(paciente);
        }

        return nuevaCita;
    }

    //Metodo que se asegura de que la cita programada no se cruce diractemente con otra tanto del 
    //paciente como del medico, tambien se verifica que el intervalo entre citas sea de minimo 1 hora

    private boolean verificarCruceCitas(LocalDateTime fechaHoraCita, Paciente paciente, Medico medico) {

        boolean crucesPaciente = paciente.getCitasProgramadas().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isEqual(fechaHoraCita));
    
        boolean crucesMedico = medico.getCitasPendientes().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isEqual(fechaHoraCita));
    
        LocalDateTime horaLimite = fechaHoraCita.minusHours(1);
        boolean diferenciaHoraPaciente = paciente.getCitasProgramadas().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isAfter(horaLimite));
    
        boolean diferenciaHoraMedico = medico.getCitasPendientes().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isAfter(horaLimite));
    
        return crucesPaciente || crucesMedico || diferenciaHoraPaciente || diferenciaHoraMedico;
    }

    //Metodo que cancela una cita determinada para luego eliminarla de sus listas correspondientes 
    //asegurandose que esta exista dentro de las citas programadas de cada uno, 
    //por ultimo se agrega al historial de citas del consultorio

    public void cancelarCita(Cita cita) {
        cita.setEstadoCita(EstadoCita.CANCELADA);
        if (consultorio.valdidarCitaMedico(cita, cita.getMedico())){
        cita.getMedico().eliminarCitaPendiente(cita);
            }
        else if(consultorio.valdidarCitaPaciente(cita, cita.getPaciente())){
            cita.getPaciente().eliminarCitaProgramada(cita);
        }
        consultorio.agregarCitaAlHistorial(cita);
    }

    //Metodo que finaliza una cita determinada para luego eliminarla de sus listas correspondientes 
    //asegurandose que esta exista dentro de las citas programadas de cada uno, 
    //por ultimo se agrega al historial de citas del consultorio

    public void finalizarCita(Cita cita) {
        cita.setEstadoCita(EstadoCita.FINALIZADA);
        if (consultorio.valdidarCitaMedico(cita, cita.getMedico())){
        cita.getMedico().eliminarCitaPendiente(cita);
            }
        else if(consultorio.valdidarCitaPaciente(cita, cita.getPaciente())){
            cita.getPaciente().eliminarCitaProgramada(cita);
        }
        consultorio.agregarCitaAlHistorial(cita);
    }

}
