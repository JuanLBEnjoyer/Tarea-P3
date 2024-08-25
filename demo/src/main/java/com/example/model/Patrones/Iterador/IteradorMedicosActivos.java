package com.example.model.Patrones.Iterador;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.model.ClasesConsultorio.EstadoMedico;
import com.example.model.ClasesConsultorio.Medico;

public class IteradorMedicosActivos implements Iterador<Medico> {
    private final List<Medico> medicosActivos;
    private int posicion;

    public IteradorMedicosActivos(Collection<Medico> medicos){
        this.medicosActivos = medicos.stream().
        filter(m->m.getEstado()==EstadoMedico.ACTIVO)
        .collect(Collectors.toList());

        this.posicion = 0;
    }

    @Override

    public boolean hasNext(){

        return posicion < medicosActivos.size();
    }

    //Retorna el siguiente medico en la lista de medicos activos y si no hay mas medicos retorna un valor nulo

    
    @Override
    public Medico next() {
        if (hasNext()) {
            return medicosActivos.get(posicion++);
        }
        return null; 
    }
}
