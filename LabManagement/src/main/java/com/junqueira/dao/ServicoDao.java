package com.junqueira.dao;

import com.junqueira.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class ServicoDao {
    public List<Cliente> consultar(String cliente){
        List<Cliente> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(cliente.length() == 0){
            lista = session.createQuery(" from Cliente ").getResultList();
        }else{
            lista = session.createQuery( "from Cliente c where c.numeroOS like "+"'"+cliente+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
