package com.junqueira.dao;

import com.junqueira.model.Cliente;
import com.junqueira.model.Servico;
import com.junqueira.model.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Conexao {
    private static SessionFactory conexao = null;
    private static Configuration configuracao;
    
    private static SessionFactory buildSessionFactory(){
        //----> Objeto que armazena as configurações de conexão
        configuracao = new Configuration().configure();
        
        configuracao.setProperty("hibernate.connection.username", "root");
        configuracao.setProperty("hibernate.connection.password", "");
        
        configuracao.addPackage("com.junqueira.model").addAnnotatedClass(Usuario.class);
        configuracao.addPackage("com.junqueira.model").addAnnotatedClass(Cliente.class);
        //configuracao.addPackage("com.junqueira.model").addAnnotatedClass(Servico.class);
        
        conexao = configuracao.buildSessionFactory();
        return conexao;
    }
    
    public static SessionFactory getSessionFactory(){
        if(conexao == null){
            conexao = buildSessionFactory();
        }
        return conexao;
    }
}
