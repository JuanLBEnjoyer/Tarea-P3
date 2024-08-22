package com.example.model.ClasesConsultorio;

import java.time.LocalDateTime;

public class Cita {

    private LocalDateTime fechaHoraCita;
    private Paciente paciente;
    private Medico medico;
    private String motivo;
    private EstadoCita estadoCita;
    private String salaCita;

    public Cita(LocalDateTime fechaHoraCita, Paciente paciente, Medico doctor, String motivo, String salaCita) {
        this.fechaHoraCita = fechaHoraCita;
        this.paciente = paciente;
        this.medico = doctor;
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

    public void setMedico(Medico doctor) {
        this.medico = doctor;
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

    public void actualizarEstado() {
        LocalDateTime ahora = LocalDateTime.now();
        if (estadoCita == EstadoCita.PROGRAMADA && ahora.isAfter(fechaHoraCita)) {
            this.estadoCita = EstadoCita.EN_CURSO;
        }
    }

    public void finalizarCita() {
        this.estadoCita = EstadoCita.FINALIZADA;
        Medico doctorAsociado = getMedico();
        Paciente pacienteAsociado = getPaciente();
        if (doctorAsociado != null) {
            doctorAsociado.eliminarCitaPendiente(this);
        }
        if (pacienteAsociado != null) {
            pacienteAsociado.eliminarCitaProgramada(this);
        }
    }

    @Override
    public String toString() {
        return "Cita [fechaHoraCita=" + fechaHoraCita + ", paciente=" + paciente + ", medico=" + medico + ", motivo="
                + motivo + ", estadoCita=" + estadoCita + ", salaCita=" + salaCita + "]";
    }

    
    
}
