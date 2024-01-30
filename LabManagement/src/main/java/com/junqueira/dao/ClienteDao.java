package com.junqueira.dao;

import com.junqueira.model.Cliente;
import com.junqueira.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class ClienteDao {
   Alerta alerta = new Alerta();
    
    public void salvar(Cliente cliente){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(cliente);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro gravado com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar salvar: " + erro);
        }
    }
    
    public void excluir(Cliente cliente){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(cliente);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro excluído com sucesso");
        }catch(Exception erro){
            System.out.println("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
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
