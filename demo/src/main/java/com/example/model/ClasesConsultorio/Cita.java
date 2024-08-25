package com.example.model.ClasesConsultorio;

import java.time.LocalDateTime;

public class Cita {

    private LocalDateTime fechaHoraCita;
    private Paciente paciente;
    private Medico medico;
    private String motivo;
    private EstadoCita estadoCita;
    private String salaCita;

    public Cita(LocalDateTime fechaHoraCita, Paciente paciente, Medico medico, String motivo, String salaCita) {
        this.fechaHoraCita = fechaHoraCita;
        this.paciente = paciente;
        this.medico = medico;
        this.motivo = motivo;
        this.estadoCita = EstadoCita.PROGRAMADA;
        this.salaCita = salaCita;
    }

    public LocalDateTime getFechaHoraCita() {
        return fechaHoraCita;
    }

    public void setFechaHoraCita(LocalDateTime fechaHoraCita) {
        this.fechaHoraCita = fechaHoraCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getSalaCita() {
        return salaCita;
    }

    public void setSalaCita(String salaCita) {
        this.salaCita = salaCita;
    }

    //Metodo que actualiza el estado de la cita de "PROGRAMADA" a "EN_CURSO" utilizando fehca y hora

    public void actualizarEstado() {
        LocalDateTime ahora = LocalDateTime.now();
        if (estadoCita == EstadoCita.PROGRAMADA && ahora.isAfter(fechaHoraCita)) {
            this.estadoCita = EstadoCita.EN_CURSO;
        }
    }
}
