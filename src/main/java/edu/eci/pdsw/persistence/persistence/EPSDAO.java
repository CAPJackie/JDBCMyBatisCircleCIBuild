/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.persistence;

import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;

/**
 *
 * @author 2110805
 */
public interface EPSDAO {
    public  List<Eps> loadAll();
    
    public Eps load();
    
    public Eps loadByID();
    
    public void save();
    
    public void update();
    
}
