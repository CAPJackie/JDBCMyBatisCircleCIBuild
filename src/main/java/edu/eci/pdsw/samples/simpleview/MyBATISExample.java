/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.EpsMapper;
import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
        
        EpsMapper pm = sqlss.getMapper(EpsMapper.class);
        
        /*Eps eps = new Eps("SaludTotal", "8456986");
        Paciente p = new Paciente(113, "CC", "Jackie", new Date(1998,03,16), eps);
        
        registrarNuevoPaciente(pmapper, p);
        
        Paciente p1 = new Paciente(113, "CC", "Lee Sang-hyeok", new Date(98,04,13), eps);
        
        p1.setConsultas(p.getConsultas());
        
        actualizarPaciente(pmapper, p1);*/
        
        List<Eps> eps= pm.loadAllEPS();
        for(int i=0; i< eps.size(); i++){
            System.out.println("nit: "+eps.get(i).getNit()+" name: "+eps.get(i).getNombre());
        }
        
        sqlss.commit();
        
        }     
    

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        pmap.insertarPaciente(p);
        
<<<<<<< HEAD
        Consulta c = new Consulta(new Date(2017,10,12),"Jaquecas muy fuertes, sintomas de migraña", 56000);
=======
        Iterator<Consulta> consultas = p.getConsultas().iterator();
        while(consultas.hasNext()){
           Consulta c = consultas.next();
           pmap.insertarConsulta(c, p.getId(), p.getTipoId(), c.getCosto());
        }
    }
        
        
        
        /*Consulta c = new Consulta(new Date(2017,10,12),"Jaquecas muy fuertes, sintomas de migraña", 56000);
>>>>>>> mybatis-persistence
        Consulta c2 = new Consulta(new Date(2018,01,05),"Cancer", 100000);
        Consulta c3 = new Consulta(new Date(2018,02,20),"Ya puedo sentir la demencia que se esta viniendo", 1234565);
        
        pmap.insertarConsulta(c, 113, "CC", c.getCosto());
        pmap.insertarConsulta(c2, 113, "CC", c2.getCosto());
        pmap.insertarConsulta(c3, 113, "CC", c3.getCosto());
        
        p.getConsultas().add(c);
        p.getConsultas().add(c2);
<<<<<<< HEAD
        p.getConsultas().add(c3);
        
    }
=======
        p.getConsultas().add(c3);*/

        
>>>>>>> mybatis-persistence
    
    
    /**
    * @obj Actualizar los datos básicos del paciente, con sus * respectivas consultas.
    * @pre El paciente p ya existe
    * @param pmap mapper a traves del cual se hará la operacion
    * @param p paciente a ser registrado
    */
   public static void actualizarPaciente(PacienteMapper pmap, Paciente p){
       pmap.actualizarPaciente(p);
       
       Iterator<Consulta> consultas = p.getConsultas().iterator();
       while(consultas.hasNext()){
           Consulta c = consultas.next();
           if(c.getId()==0){
               pmap.insertarConsulta(c, p.getId(), p.getTipoId(), c.getCosto());
           }
       }
   }
    
}
