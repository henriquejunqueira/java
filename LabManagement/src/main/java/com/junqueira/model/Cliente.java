package com.junqueira.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "cliente", length = 100, nullable = false)
    private String cliente;
    
    @Column(name = "numeroOS", length = 100, nullable = false)
    private Long numeroOS;
    
    @Column(name = "solicitacaoServico", length = 100, nullable = false)
    private String solicitacaoServico;
    
    @OneToOne
    private Usuario usuario;
    
    @Column(name = "statusServico", length = 100, nullable = false)
    private String statusServico;
    
    @Column(name = "averiguacao", length = 100, nullable = false)
    private String averiguacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getNumeroOS() {
        return numeroOS;
    }

    public void setNumeroOS(Long numeroOS) {
        this.numeroOS = numeroOS;
    }

    public String getSolicitacaoServico() {
        return solicitacaoServico;
    }

    public void setSolicitacaoServico(String solicitacaoServico) {
        this.solicitacaoServico = solicitacaoServico;
    }

    public String getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(String statusServico) {
        this.statusServico = statusServico;
    }

    public String getAveriguacao() {
        return averiguacao;
    }

    public void setAveriguacao(String averiguacao) {
        this.averiguacao = averiguacao;
    }
    
}
