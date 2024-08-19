package com.example.model.Patrones.Observer;

import java.util.Collection;

import com.example.model.ClasesConsultorio.Cita;

public interface Sujeto {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notificarObservers(String mensaje);

    void verficarProximasCitas(Collection<Cita> citas);
    
}
