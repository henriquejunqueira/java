package com.junqueira.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.junqueira.dao.ClienteDao;
import com.junqueira.dao.Conexao;
import com.junqueira.model.Cliente;
import com.junqueira.model.Usuario;
import com.junqueira.util.Alerta;
import com.junqueira.util.ValidaCampos;
import java.net.URL;
import java.util.ArrayList;
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
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class ClienteController implements Initializable, ICadastro {

    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private JFXTextField tfId;
    @FXML
    private JFXTextField tfCliente;
    @FXML
    private JFXTextField tfOS;
    @FXML
    private JFXTextField tfSolicitacaoServico;
    @FXML
    private JFXComboBox<String> cbStatusServico;
    @FXML
    private JFXTextArea taAveriguacao;
    @FXML
    private JFXComboBox<Usuario> cbTecnicoAtendente;

    Long id;
    private ClienteDao clienteDao = new ClienteDao();
    private ObservableList<Cliente> olClientes = FXCollections.observableArrayList();
    private ObservableList<Usuario> olUsuario = FXCollections.observableArrayList();
    private ObservableList<String> listaStatus = FXCollections.observableArrayList("Verificando", "Aguardando Aprovação", "Aguardando Peça", "Aprovado Serviço", "Finalizado");
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado = new Cliente();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        popularComboBoxUsuario();
        cbStatusServico.setItems(listaStatus);
        cbStatusServico.setValue("Verificando");
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
        Cliente cliente = new Cliente();
        if(ValidaCampos.checarCampoVazio(tfCliente, tfOS, tfSolicitacaoServico, cbStatusServico, taAveriguacao, cbTecnicoAtendente)){
            if(clienteSelecionado.getId() != null){
                cliente.setId(clienteSelecionado.getId());
            }
            cliente.setCliente(tfCliente.getText());
            cliente.setNumeroOS(Long.parseLong(tfOS.getText()));
            cliente.setSolicitacaoServico(tfSolicitacaoServico.getText());
            cliente.setStatusServico(cbStatusServico.getSelectionModel().getSelectedItem());
            cliente.setAveriguacao(taAveriguacao.getText());
            cliente.setUsuario(cbTecnicoAtendente.getSelectionModel().getSelectedItem());
            
            clienteDao.salvar(cliente);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void excluirCadastro(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfCliente.getText())){
            clienteDao.excluir(clienteSelecionado);

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
        TableColumn<Cliente, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Cliente, String> colunaCliente = new TableColumn<>("CLIENTE");
        TableColumn<Cliente, String> colunaNumeroOS = new TableColumn<>("OS");
        TableColumn<Cliente, String> colunaSolicitacaoServico = new TableColumn<>("SOLICITAÇÂO");
        TableColumn<Cliente, Usuario> colunaTecnicoAtendente = new TableColumn<>("TÉCNICO/ATENDENTE");
        TableColumn<Cliente, String> colunaStatus = new TableColumn<>("STATUS");
        TableColumn<Cliente, String> colunaAveriguacao = new TableColumn<>("AVERIGUAÇÂO");
        
        tvClientes.getColumns().addAll(colunaId, colunaCliente, colunaNumeroOS, colunaSolicitacaoServico, colunaTecnicoAtendente, colunaStatus, colunaAveriguacao);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colunaNumeroOS.setCellValueFactory(new PropertyValueFactory("numeroOS"));
        colunaSolicitacaoServico.setCellValueFactory(new PropertyValueFactory("solicitacaoServico"));
        colunaTecnicoAtendente.setCellValueFactory(new PropertyValueFactory("usuario"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory("statusServico"));
        colunaAveriguacao.setCellValueFactory(new PropertyValueFactory("averiguacao"));
        
        tvClientes.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public void atualizarTabela() {
        olClientes.clear();
        listaClientes = clienteDao.consultar(tfPesquisa.getText());
        
        for(Cliente c : listaClientes){
            olClientes.add(c);
        }
        
        tvClientes.getItems().setAll(olClientes);
        tvClientes.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        clienteSelecionado = tvClientes.getItems().get(tvClientes.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(clienteSelecionado.getId()));
        tfCliente.setText(clienteSelecionado.getCliente().toString());
        tfOS.setText(clienteSelecionado.getNumeroOS().toString());
        tfSolicitacaoServico.setText(clienteSelecionado.getSolicitacaoServico().toString());
        cbTecnicoAtendente.setValue(clienteSelecionado.getUsuario());
        cbStatusServico.setValue(clienteSelecionado.getStatusServico());
        taAveriguacao.setText(clienteSelecionado.getAveriguacao().toString());
    }

    @Override
    public void limparCamposFormulario() {
        tfId.setText("");
        tfCliente.setText("");
        tfOS.setText("");
        tfSolicitacaoServico.setText("");
        cbTecnicoAtendente.setValue(null);
        cbStatusServico.setValue("");
        taAveriguacao.setText("");
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttId = new Tooltip("Id do cadastro do cliente. Não pode ser editado!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttCliente = new Tooltip("Digite o nome do cliente...");
        ttCliente.setFont(new Font("Arial", 14));
        tfCliente.setTooltip(ttCliente);
        
        Tooltip ttNumeroOS = new Tooltip("Digite o número da ordem de serviço...");
        ttNumeroOS.setFont(new Font("Arial", 14));
        tfOS.setTooltip(ttNumeroOS);
        
        Tooltip ttSolicitacaoServico = new Tooltip("Digite o serviço solicitado pelo cliente...");
        ttSolicitacaoServico.setFont(new Font("Arial", 14));
        tfSolicitacaoServico.setTooltip(ttSolicitacaoServico);
        
        Tooltip ttStatusServico = new Tooltip("Selecione o status do serviço...");
        ttStatusServico.setFont(new Font("Arial", 14));
        cbStatusServico.setTooltip(ttStatusServico);
        
        Tooltip ttAveriguacao = new Tooltip("Digite o que foi feito e o que falta ser feito no equipamento...");
        ttAveriguacao.setFont(new Font("Arial", 14));
        taAveriguacao.setTooltip(ttAveriguacao);
        
        Tooltip ttPesquisa = new Tooltip("Pesquise pelo número da Ordem de Serviço do cliente...");
        ttPesquisa.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisa);
    }
    
    private void popularComboBoxUsuario(){
        List<Usuario> list = new ArrayList<>();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery(" from Usuario").getResultList();
        session.getTransaction().commit();
        session.close();
        
        for(Usuario usuario : list){
            olUsuario.add(usuario);
        }
        cbTecnicoAtendente.setItems(olUsuario);
    }
    
}
