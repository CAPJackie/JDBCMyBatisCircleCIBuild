/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.persistence.EPSDAO;
import edu.eci.pdsw.persistence.persistence.PacienteDAO;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.mybatis.guice.transactional.Transactional;

/**
 *
 * @author 2110805
 */
public class ServiciosPacientesImpl implements ServiciosPacientes{
    
    @Inject
    PacienteDAO pacDAO;
    
    @Inject
    EPSDAO epsDAO;
    
    private final Map<Tupla<Integer, String>, Paciente> pacientes;
    private List<Eps> epsregistradas;
    private int idconsulta = 1;

    public ServiciosPacientesImpl() {
        this.pacientes = new LinkedHashMap<>();
        epsregistradas = new LinkedList<>();
        cargarDatosEstaticos(pacientes);
    }

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente paciente = pacientes.get(new Tupla<>(idPaciente, tipoid));
        if (paciente == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        } else {
            return paciente;
        }

    }

    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        List<Paciente> temp = new ArrayList<>();
        temp.addAll(pacientes.values());
        return temp;
    }

    @Override
    public void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes {
        
        pacientes.put(new Tupla<>(paciente.getId(), paciente.getTipoId()), paciente);
        //System.out.println("reg:"+new Tupla<>(paciente.getId(), paciente.getTipoId()));
    }

    @Transactional
    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoId, Consulta consulta) throws ExcepcionServiciosPacientes {
        
        try{
            pacDAO.save();
        }catch(PersistenceException ex){
            Logger.getLogger(ServiciosPacientesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("add:"+new Tupla<>(idPaciente, tipoid));
        Paciente paciente = pacientes.get(new Tupla<>(idPaciente, tipoId));
        if (paciente != null) {
            consulta.setId(idconsulta);
            idconsulta++;
            paciente.getConsultas().add(consulta);
        } else {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        }
    }

    @Override
    public List<Consulta> obtenerConsultasEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if (consulta.getFechayHora().before(fechaFin) && consulta.getFechayHora().after(fechaInicio)) {
                        temp.add(consulta);
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public long obtenerCostoEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        long deuda = 0;
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if (consulta.getFechayHora().after(fechaInicio) && consulta.getFechayHora().before(fechaFin)) {
                        deuda += consulta.getCosto();
                    }
                }
            }
        }
        return deuda;
    }

    @Override
    public List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                temp.addAll(paciente.getConsultas());
            }
        }
        if (temp.isEmpty()) {
            throw new ExcepcionServiciosPacientes("La EPS " + nameEps + " no se encuentra asociada a ningun paciente o no existe ");
        }
        return temp;
    }

    @Override
    public List<Eps> obtenerEPSsRegistradas() throws ExcepcionServiciosPacientes {
        return epsregistradas;
    }
    
    
    
    private void cargarDatosEstaticos(Map<Tupla<Integer, String>, Paciente> pacientes) {
        try {
            Eps eps1 = new Eps("Compensar", "7289374982-0");
            Eps eps2 = new Eps("Sanitas", "2728748323-0");
            Eps eps3 = new Eps("Sura", "798273892-0");
            Eps eps4 = new Eps("Coomeva", "482738221-1");
            Eps eps5 = new Eps("Medimas", "78239842232-2");
            Eps eps6 = new Eps("SaludTotal", "8439009323-9");

            epsregistradas.add(eps1);
            epsregistradas.add(eps2);
            epsregistradas.add(eps3);
            epsregistradas.add(eps4);
            epsregistradas.add(eps5);
            epsregistradas.add(eps6);

            Paciente paciente1 = new Paciente(1, "CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01"), eps1);
            Paciente paciente2 = new Paciente(2, "CC", "Maria Rodriguez", java.sql.Date.valueOf("2000-01-01"), eps2);
            Paciente paciente3 = new Paciente(3, "CC", "Pedro Martinez", java.sql.Date.valueOf("1956-05-01"), eps3);
            Paciente paciente4 = new Paciente(4, "CC", "Martin Hernandez", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente paciente5 = new Paciente(5, "CC", "Cristian Pinzon", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente paciente6 = new Paciente(6, "CC", "Daniel Beltran", java.sql.Date.valueOf("1956-05-01"), eps5);
            Paciente paciente7 = new Paciente(7, "CC", "Ricardo Pinto", java.sql.Date.valueOf("1956-05-01"), eps6);

            registrarNuevoPaciente(paciente1);
            registrarNuevoPaciente(paciente2);
            registrarNuevoPaciente(paciente3);
            registrarNuevoPaciente(paciente4);
            registrarNuevoPaciente(paciente5);
            registrarNuevoPaciente(paciente6);
            registrarNuevoPaciente(paciente7);

            Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Dolor de cabeza", 454);
            Consulta consulta2 = new Consulta(java.sql.Date.valueOf("2000-01-02"), "Dolor de estomago", 271);
            Consulta consulta3 = new Consulta(java.sql.Date.valueOf("2000-04-01"), "Dolor de garganta", 222);
            Consulta consulta4 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Gripa", 54);
            Consulta consulta5 = new Consulta(java.sql.Date.valueOf("2000-03-11"), "Fiebre", 71);
            Consulta consulta6 = new Consulta(java.sql.Date.valueOf("2000-02-07"), "Malestar y mareo", 322);
            Consulta consulta7 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Vomito", 322);
            Consulta consulta8 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Alergia", 322);
            Consulta consulta9 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Resfriado", 322);

            agregarConsultaPaciente(1, "CC", consulta1);
            agregarConsultaPaciente(1, "CC", consulta2);
            agregarConsultaPaciente(1, "CC", consulta3);
            agregarConsultaPaciente(2, "CC", consulta4);
            agregarConsultaPaciente(3, "CC", consulta5);
            agregarConsultaPaciente(4, "CC", consulta6);
            agregarConsultaPaciente(5, "CC", consulta7);
            agregarConsultaPaciente(6, "CC", consulta8);
            agregarConsultaPaciente(7, "CC", consulta5);
            agregarConsultaPaciente(7, "CC", consulta9);

        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesMock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}


