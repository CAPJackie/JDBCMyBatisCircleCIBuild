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
import javax.persistence.PersistenceException;

/**
 *
 * @author 2110805
 */
public class PacienteDAOMyBatis implements PacienteDAO{

    
    @Inject
    PacienteMapper pacientemap;
    
    @Override
    public List<Paciente> loadAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Paciente load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paciente loadByID(int id, String tipoId) throws PersistenceException{
        try{
            Paciente paciente = pacientemap.loadPacienteById(id, tipoId);
            return paciente;
        }catch(Exception e){
            throw new PersistenceException("Paciente " + id + " no esta registrado",e);
        }
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Paciente p) throws PersistenceException{
        try{
            pacientemap.actualizarPaciente(p);
        }catch(Exception e){
            throw new PersistenceException("Paciente " + p.getId() + " no se puede actualizar porque no esta registrado",e);
        }
    }
    
}
