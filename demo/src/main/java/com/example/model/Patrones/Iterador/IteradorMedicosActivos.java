package com.example.model.Patrones.Iterador;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.model.ClasesConsultorio.EstadoDoctor;
import com.example.model.ClasesConsultorio.Medico;

public class IteradorMedicosActivos implements Iterador<Medico> {

    private final List<Medico> medicosActivos;
    private int posicion;

    public IteradorMedicosActivos(Collection<Medico> medicos){
        this.medicosActivos = medicos.stream().
        filter(m->m.getEstado()==EstadoDoctor.ACTIVO)
        .collect(Collectors.toList());

        this.posicion = 0;
    }

    @Override

    public boolean hasNext(){

        return posicion < medicosActivos.size();
    }

    @Override
    public Medico next() {
        if (hasNext()) {
            return medicosActivos.get(posicion++);
        }
        return null; 
    }


    
}
