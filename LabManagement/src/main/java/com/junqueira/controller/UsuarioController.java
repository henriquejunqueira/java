package com.junqueira.controller;

import com.jfoenix.controls.JFXTextField;
import com.junqueira.dao.UsuarioDao;
import com.junqueira.model.Usuario;
import com.junqueira.util.Alerta;
import com.junqueira.util.ValidaCampos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class UsuarioController implements Initializable, ICadastro {

    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private JFXTextField tfId;
    @FXML
    private JFXTextField tfUsuario;

    Long id;
    private UsuarioDao dao = new UsuarioDao();
    private ObservableList<Usuario> olUsuario = FXCollections.observableArrayList();
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado = new Usuario();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
    }    

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    private void novoCadastro(ActionEvent event) {
        limparCamposFormulario();
    }

    @FXML
    private void salvarCadastro(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Usuario usuario = new Usuario();
        if(ValidaCampos.checarCampoVazio(tfUsuario)){
            if(usuarioSelecionado.getId() != null){
                usuario.setId(usuarioSelecionado.getId());
            }
            usuario.setUsuario(tfUsuario.getText());
            
            dao.salvar(usuario);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void excluirCadastro(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfUsuario.getText())){
            dao.excluir(usuarioSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Usuario, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Usuario, String> colunaUsuario = new TableColumn<>("USUÁRIO");
        
        tvUsuario.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tvUsuario.getColumns().addAll(colunaId, colunaUsuario);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
    }

    @Override
    public void atualizarTabela() {
        // limpa a lista
        olUsuario.clear();
        
        // puxa as informações do bd
        listaUsuarios = dao.consultar(tfPesquisa.getText());
        
        // coloca os dados do bd na lista
        for(Usuario u : listaUsuarios){
            
            olUsuario.add(u);
            
        }
        
        tvUsuario.getItems().setAll(olUsuario);
        
        // localiza o primeiro item da lista
        tvUsuario.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        usuarioSelecionado = tvUsuario.getItems().get(tvUsuario.getSelectionModel().getSelectedIndex());
        tfId.setText(usuarioSelecionado.getId().toString());
        tfUsuario.setText(usuarioSelecionado.getUsuario());
    }

    @Override
    public void limparCamposFormulario() {
        usuarioSelecionado.setId(null);
        tfId.clear();
        tfUsuario.clear();
    }

    @Override
    public void adicionarTooltip() {
        
        Tooltip ttId = new Tooltip("Id do técnico ou atendente. Não é possível editar!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttTecnicoAtendente = new Tooltip("Nome do técnico ou atendente que deu entrada no equipamento. Campo obrigatório!");
        ttTecnicoAtendente.setFont(new Font("Arial", 14));
        tfUsuario.setTooltip(ttTecnicoAtendente);
        
        Tooltip ttPesquisa = new Tooltip("Pesquise pelo nome do técnico ou atendente...");
        ttPesquisa.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisa);
    }
    
}
