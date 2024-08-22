package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.model.ClasesConsultorio.AdministradorCitas;
import com.example.model.ClasesConsultorio.Cita;
import com.example.model.ClasesConsultorio.Consultorio;
import com.example.model.ClasesConsultorio.Medico;
import com.example.model.ClasesConsultorio.Paciente;


public class Prueba {

    public static void main(String[] args) {

        Consultorio c1 = Consultorio.obtenerInstancia("Consultorio 1", "Calle 10");
       AdministradorCitas admin = c1.crearAdministrador();

        Paciente paciente = new Paciente("Manolito", "12345", LocalDate.of(2015, 4, 24));
        Paciente paciente2 = new Paciente("Carlitos", "54321", LocalDate.of(1999, 3, 4));

        Medico medico = new Medico("Roberto", "67890", LocalDate.of(1970, 3, 12));
        Medico medico2 = new Medico("John", "09876", LocalDate.of(1984, 6, 2));


        c1.agregarPaciente(paciente);
        c1.agregarPaciente(paciente2);
        c1.agregarMedico(medico);
        c1.agregarMedico(medico2);
 

        Cita cita1 = admin.programarCita(LocalDateTime.of( 2024, 9, 3, 12, 30), paciente, "Seguimiento de Tratamiento", "203");

        System.out.println(cita1.toString());

        System.out.println("citas programadas"+medico.getCitasPendientes());
        System.out.println("citas programadas"+medico2.getCitasPendientes());
        
    }
    
}
