/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.persistence.persistence.PacienteDAO;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import java.util.List;

/**
 *
 * @author 2110805
 */
public class PacienteDAOMyBatis implements PacienteDAO{

    
    @Inject
    PacienteMapper pacientemap;
    
    @Override
    public List<Paciente> loadAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paciente load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paciente loadByID(int id, String tipoId) throws ExcepcionServiciosPacientes{
        Paciente paciente = pacientemap.loadPacienteById(id, tipoId);
        if (paciente == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + id + " no esta registrado");
        } else {
            return paciente;
        }
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Paciente p){
        pacientemap.actualizarPaciente(p);
    }
    
}
