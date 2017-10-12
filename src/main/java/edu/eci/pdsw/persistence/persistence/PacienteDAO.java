/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.persistence;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import java.util.List;

/**
 *
 * @author 2110805
 */
public interface PacienteDAO {
    public  List<Paciente> loadAll();
    
    public Paciente load();
    
    public Paciente loadByID(int id, String tipoId)throws ExcepcionServiciosPacientes;
    
    public void save();
    
    public void update(Paciente paciente);
}
