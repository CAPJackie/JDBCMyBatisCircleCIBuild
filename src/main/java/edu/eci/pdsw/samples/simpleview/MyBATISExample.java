/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBATISExample {

/**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage(),e);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        
        
        Eps eps = new Eps("SaludTotal", "8456986");
        Paciente p = new Paciente(1019138859, "CC", "Juan David Ramirez Mendoza", new Date(1998,03,16), eps);
        
        registrarNuevoPaciente(pmapper, p);
        
        sqlss.commit();
        /**/

        //List<Paciente> pacientes=pmapper.loadPacientes();
       
       
        /*Eps eps = new Eps("Compensar", "8456986");
        Paciente p = new Paciente(1019138849, "CC", "Juan David Ramirez Mendoza", new Date(1998), eps);
        pmapper.insertarPaciente(p);

        Paciente paciente = pmapper.loadPacienteById(1019138849,"CC");
        
        System.out.println(paciente);*/
        /*for(int i= 0; i<pacientes.size();i++){
            System.out.println(pacientes.get(i));*/
        }     
    

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        pmap.insertarPaciente(p);
        
        Consulta c = new Consulta(new Date(2017,10,12),"Jaquecas muy fuertes, sintomas de migraña", 56000);
        Consulta c2 = new Consulta(new Date(2018,01,05),"Cancer", 100000);
        Consulta c3 = new Consulta(new Date(2018,02,20),"Ya puedo sentir la demencia que se esta viniendo", 1234565);
        
        pmap.insertarConsulta(c, 1019138859, "CC", c.getCosto());
        pmap.insertarConsulta(c2, 1019138859, "CC", c2.getCosto());
        pmap.insertarConsulta(c3, 1019138859, "CC", c3.getCosto());
    }
    
    
    /**
    * @obj Actualizar los datos básicos del paciente, con sus * respectivas consultas.
    * @pre El paciente p ya existe
    * @param pmap mapper a traves del cual se hará la operacion
    * @param p paciente a ser registrado
    */
   public void actualizarPaciente(PacienteMapper pmap, Paciente p){
       pmap.actualizarPaciente(p);
   }
    
}
