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

    public boolean contieneObserver(Observer observer) {
        return observers.contains(observer);
    }

    @Override
    public void verficarProximasCitas(Collection<Cita> citas) {
        LocalDate hoy = LocalDate.now();
        citas.stream()
                .filter(cita -> cita.getFechaHoraCita().toLocalDate().isEqual(hoy.plusDays(1)))
                .forEach(cita -> notificarObservers("Recordatorio: Tienes una cita mañana. Motivo: " + cita.getMotivo()));
    }

    public void programarCita(LocalDateTime fechaHoraCita, Paciente paciente, String motivo, String salaCita) {
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
    }

    private boolean verificarCruceCitas(LocalDateTime fechaHoraCita, Paciente paciente, Medico medico) {

        boolean crucesPaciente = paciente.getCitasProgramadas().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isEqual(fechaHoraCita));
    
        boolean crucesMedico = medico.getCitasPendientes().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isEqual(fechaHoraCita));
    
        LocalDateTime horaLimite = fechaHoraCita.minusHours(1);
        boolean diferenciaHoraPaciente = paciente.getCitasProgramadas().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isAfter(horaLimite));
    
        boolean diferenciaHoraDoctor = medico.getCitasPendientes().stream()
                .anyMatch(cita -> cita.getFechaHoraCita().isAfter(horaLimite));
    
        return crucesPaciente || crucesMedico || diferenciaHoraPaciente || diferenciaHoraDoctor;
    }




    
}
