package com.junqueira.dao;

import com.junqueira.model.Usuario;
import com.junqueira.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class UsuarioDao {
    Alerta alerta = new Alerta();
    
    public void salvar(Usuario usuario){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(usuario);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro gravado com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar salvar: " + erro);
        }
    }
    
    public void excluir(Usuario usuario){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro excluído com sucesso");
        }catch(Exception erro){
            System.out.println("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    public List<Usuario> consultar(String usuario){
        List<Usuario> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(usuario.length() == 0){
            lista = session.createQuery(" from Usuario ").getResultList();
        }else{
            lista = session.createQuery( "from Usuario u where u.usuario like "+"'"+usuario+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
