package com.example.model.Patrones.Observer;

import com.example.model.ClasesConsultorio.Paciente;

public interface Sujeto {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notificarObservers(String mensaje);

    void verficarProximasCitasPaciente(Paciente paciente);
    
}
