/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managebeans;


import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosHistorialPacientesFactory;
import edu.eci.pdsw.samples.services.ServiciosPacientes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "HistorialPacientes")
@SessionScoped
public class RegistroConsultaBean implements Serializable {

    private final ServiciosPacientes servicepacientes = ServiciosHistorialPacientesFactory.getInstance().getServiciosPaciente();
    private Paciente pacienteActual= new Paciente(3, "CC", "Pedro Martinez", java.sql.Date.valueOf("1956-05-01"), new Eps("Sura", "798273892-0"));
    private int idPaciente;
    private String tipoIdPaciente;
    private String nombrePaciente;
    private Date fechaNacimientoCliente;
    private String epsCliente;
    private String resumenCitaNueva;
    private Date fechaYHoraNueva;
    private long costoNuevo;  
    


    public ServiciosPacientes getServicepacientes() {
        return servicepacientes;
    }
    
    public RegistroConsultaBean(){
        
    }

    
    
    public Paciente getPacienteActual(){    
        return pacienteActual;
    }

    public Date getFechaYHoraNueva() {
        return fechaYHoraNueva;
    }

    public long getCostoNuevo() {
        return costoNuevo;
    }

    public void setFechaYHoraNueva(Date fechaYHoraNueva) {
        this.fechaYHoraNueva = fechaYHoraNueva;
    }

    public void setCostoNuevo(long costoNuevo) {
        this.costoNuevo = costoNuevo;
    }
    
    
    
    public Set<Consulta> getCitasPaciente(Paciente p){
        return p.getConsultas();
    }
    
    public String getResumenCitaNueva(){
        return resumenCitaNueva;
    }

    public void setPacienteActual(Paciente PacienteActual) {
        this.pacienteActual = PacienteActual;
    }

    public void setResumenCitaNueva(String resumenCitaNueva) {
        this.resumenCitaNueva = resumenCitaNueva;
    }
    
    
    
    public String getTipoIdPaciente() {
        return tipoIdPaciente;
    }

    public void setTipoIdPaciente(String tipoIdPaciente) {
        this.tipoIdPaciente = tipoIdPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Date getFechaNacimientoCliente() {
        return fechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(Date fechaNacimientoCliente) {
        this.fechaNacimientoCliente = fechaNacimientoCliente;
    }

    public String getEpsCliente() {
        return epsCliente;
    }

    public void setEpsCliente(String epsCliente) {
        this.epsCliente = epsCliente;
    }
    
    
    public int getIdPaciente(){
        return idPaciente;
    }
    
    public void setIdPaciente(int idPaciente){
        this.idPaciente = idPaciente;
    }
    
    
    public List<Eps> getEpsRegistradas() throws ExcepcionServiciosPacientes{
        return servicepacientes.obtenerEPSsRegistradas();
    }
    
    public void registrarPaciente() throws ExcepcionServiciosPacientes{
        int i = 0;
        while (!getEpsRegistradas().get(i).getNombre().equals(epsCliente)){
            i++;
        }
        servicepacientes.registrarNuevoPaciente(new Paciente(idPaciente, tipoIdPaciente, nombrePaciente, fechaNacimientoCliente, getEpsRegistradas().get(i)));
        System.out.println(pacienteActual.getId());
    }
    
    public void registrarConsultaAPaciente() throws ExcepcionServiciosPacientes{
        servicepacientes.agregarConsultaPaciente(pacienteActual.getId(), pacienteActual.getTipoId(),
                                                new Consulta(fechaYHoraNueva, resumenCitaNueva, costoNuevo ));
    }
    
    public String irARegistroDeConsultas(){
        return "registroconsultas.xhtml";
    }
    
    public String irARegistroDePacientes(){
        return "registropacientes.xhtml";
    }

    public void showMessage(String estado, String mensaje) {
        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, estado, mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }


}
