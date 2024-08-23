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
        // Crear el consultorio
        Consultorio consultorio = Consultorio.obtenerInstancia("Consultorio XYZ", "123 Calle Principal");
        AdministradorCitas adminCitas = consultorio.crearAdministrador();

        // Crear médicos
        Medico medico1 = new Medico("Dr. Juan Pérez", "M123", LocalDate.of(1980, 5, 15));
        Medico medico2 = new Medico("Dr. Laura Gómez", "M124", LocalDate.of(1975, 8, 22));
        consultorio.getMedicos().add(medico1);
        consultorio.getMedicos().add(medico2);

        // Crear pacientes
        Paciente paciente1 = new Paciente("Ana Gómez", "P456", LocalDate.of(1990, 3, 25));
        Paciente paciente2 = new Paciente("Luis Martínez", "P457", LocalDate.of(1985, 7, 30));
        consultorio.getPacientes().add(paciente1);
        consultorio.getPacientes().add(paciente2);

        // Programar citas
        LocalDateTime fechaHoraCita1 = LocalDateTime.of(2024, 8, 23, 10, 0);
        String motivo1 = "Consulta de rutina";
        String salaCita1 = "Sala 1";
        Cita cita1 = adminCitas.programarCita(fechaHoraCita1, paciente1, motivo1, salaCita1);

        LocalDateTime fechaHoraCita2 = LocalDateTime.of(2024, 8, 24, 14, 0);
        String motivo2 = "Chequeo dermatológico";
        String salaCita2 = "Sala 2";
        Cita cita2 = adminCitas.programarCita(fechaHoraCita2, paciente2, motivo2, salaCita2);

        // Mostrar citas programadas
        System.out.println("Citas Programadas:");
        consultorio.getPacientes().forEach(paciente -> {
            paciente.getCitasProgramadas().forEach(System.out::println);
        });

        // Cancelar una cita
        adminCitas.cancelarCita(cita1);

        // Finalizar una cita
        adminCitas.finalizarCita(cita2);

        // Mostrar historial de citas
        System.out.println("\nHistorial de Citas:");
        consultorio.getHistorialCitas().forEach(System.out::println);

        // Verificar próximas citas
        System.out.println("\nVerificar próximas citas para paciente1:");
        adminCitas.verficarProximasCitasPaciente(paciente1);
    }
    
}
