package com.example.model.ClasesConsultorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.example.model.Patrones.Observer.Observer;
import com.example.model.Patrones.Observer.Sujeto;

public class AdministradorCitas implements Sujeto {

     private Collection<Observer> observers;
     private final Consultorio consultorio;

    public AdministradorCitas(Consultorio consultorio){
        this.consultorio = consultorio;
        this.observers = new ArrayList<>();
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


    
}
