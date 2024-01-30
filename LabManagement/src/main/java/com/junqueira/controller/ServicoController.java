package com.junqueira.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.junqueira.dao.ClienteDao;
import com.junqueira.model.Cliente;
import com.junqueira.model.Usuario;
import com.junqueira.util.Alerta;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ElementTags;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class ServicoController implements Initializable, ICadastro {

    @FXML
    private TextField tfPesquisa;
    @FXML
    private JFXTextField tfId;
    @FXML
    private JFXTextField tfCliente;
    @FXML
    private JFXTextField tfOS;
    @FXML
    private JFXTextField tfSolicitacaoServico;
    @FXML
    private JFXTextField tfTecnicoAtendente;
    @FXML
    private JFXTextField tfStatusServico;
    @FXML
    private JFXTextArea taAveriguacao;
    @FXML
    private TableView<Cliente> tvServicos;
    @FXML
    private Button btnPDF;
    
    Long id;
    private ClienteDao dao = new ClienteDao();
    private ObservableList<Cliente> olClientes = FXCollections.observableArrayList();
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado = new Cliente();
    private Alerta alerta = new Alerta();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        tfId.setText("");
        tfTecnicoAtendente.setText("");
        tfCliente.setText("");
        tfOS.setText("");
        tfSolicitacaoServico.setText("");
        tfStatusServico.setText("");
        taAveriguacao.setText("");
    }    

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
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
        
        tvServicos.getColumns().addAll(colunaId, colunaCliente, colunaNumeroOS, colunaSolicitacaoServico, colunaTecnicoAtendente, colunaStatus, colunaAveriguacao);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colunaNumeroOS.setCellValueFactory(new PropertyValueFactory("numeroOS"));
        colunaSolicitacaoServico.setCellValueFactory(new PropertyValueFactory("solicitacaoServico"));
        colunaTecnicoAtendente.setCellValueFactory(new PropertyValueFactory("usuario"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory("statusServico"));
        colunaAveriguacao.setCellValueFactory(new PropertyValueFactory("averiguacao"));
        
        tvServicos.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
    }

    @Override
    public void atualizarTabela() {
        olClientes.clear();
        listaClientes = dao.consultar(tfPesquisa.getText());
        
        for(Cliente c : listaClientes){
            olClientes.add(c);
        }
        
        tvServicos.getItems().setAll(olClientes);
        tvServicos.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        clienteSelecionado = tvServicos.getItems().get(tvServicos.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(clienteSelecionado.getId()));
        tfCliente.setText(clienteSelecionado.getCliente().toString());
        tfOS.setText(clienteSelecionado.getNumeroOS().toString());
        tfSolicitacaoServico.setText(clienteSelecionado.getSolicitacaoServico().toString());
        tfTecnicoAtendente.setText(clienteSelecionado.getUsuario().toString());
        tfStatusServico.setText(clienteSelecionado.getStatusServico().toString());
        taAveriguacao.setText(clienteSelecionado.getAveriguacao().toString());
    }

    @Override
    public void limparCamposFormulario() {
        tfId.setText("");
        tfTecnicoAtendente.setText("");
        tfCliente.setText("");
        tfOS.setText("");
        tfSolicitacaoServico.setText("");
        tfStatusServico.setText("");
        taAveriguacao.setText("");
    }

    @Override
    public void adicionarTooltip() {
        
        Tooltip ttId = new Tooltip("Id do cadastro do cliente. Não pode ser editado aqui!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttCliente = new Tooltip("Nome do cliente consultado. Não pode ser editado aqui!");
        ttCliente.setFont(new Font("Arial", 14));
        tfCliente.setTooltip(ttCliente);
        
        Tooltip ttNumeroOS = new Tooltip("Número da Ordem de Serviço do cliente consultado. Não pode ser editado aqui!");
        ttNumeroOS.setFont(new Font("Arial", 14));
        tfOS.setTooltip(ttNumeroOS);
        
        Tooltip ttSolicitacaoServico = new Tooltip("Serviço solicitado pelo cliente. Não pode ser editado aqui!");
        ttSolicitacaoServico.setFont(new Font("Arial", 14));
        tfSolicitacaoServico.setTooltip(ttSolicitacaoServico);
        
        Tooltip ttStatusServico = new Tooltip("Status da Ordem de Serviço consultada. Não pode ser editado aqui!");
        ttStatusServico.setFont(new Font("Arial", 14));
        tfStatusServico.setTooltip(ttStatusServico);
        
        Tooltip ttAveriguacao = new Tooltip("Descrição do processo de verificação do equipamento. Não pode ser editado aqui!");
        ttAveriguacao.setFont(new Font("Arial", 14));
        taAveriguacao.setTooltip(ttAveriguacao);
        
        Tooltip ttPesquisa = new Tooltip("Pesquise pelo número da Ordem de Serviço do cliente...");
        ttPesquisa.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisa);
    }

    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @FXML
    private void gerarPDF(ActionEvent event) {
        imprimirRelatorioPDF();
        msgImpressao();
    }
    
    public void imprimirRelatorioPDF(){ 
        try{
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            String usuario = System.getProperty("user.name");
            File file = new File("C:\\Users\\"+ usuario +"\\Desktop\\Relatorios");  

            if (!file.exists()) {
                file.mkdirs();
            }
            
            PdfWriter.getInstance(document, new FileOutputStream(file+ "\\"+ clienteSelecionado.getNumeroOS()+".pdf"));
            document.open();

            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES, 16, com.lowagie.text.Font.NORMAL);

            Paragraph p1 = new Paragraph("OS: " + clienteSelecionado.getNumeroOS(), font);
            p1.setSpacingAfter(10);
            document.add( p1 );

            Paragraph p2 = new Paragraph("Cliente: " + clienteSelecionado.getCliente(), font   );
            p2.setSpacingAfter(10);
            document.add( p2 );

            Paragraph p3 = new Paragraph("Solicitação de Serviço: " + clienteSelecionado.getSolicitacaoServico() , font   );
            p3.setSpacingAfter(10);
            document.add( p3 );
            
            Paragraph p4 = new Paragraph("Técnico/Atendente: " + clienteSelecionado.getUsuario() , font   );
            p3.setSpacingAfter(10);
            document.add( p4 );
            
            Paragraph p5 = new Paragraph("Status do Serviço: " + clienteSelecionado.getStatusServico() , font   );
            p3.setSpacingAfter(10);
            document.add( p5 );
            
            Paragraph p6 = new Paragraph("Averiguação: " + clienteSelecionado.getAveriguacao(), font   );
            p3.setSpacingAfter(10);
            document.add( p6 );

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    
    }
    
    // Método para mensagem de informação
    public void msgImpressao(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informação sobre geração de relatório");
        alert.setContentText("Gerado relatório da OS: " + clienteSelecionado.getNumeroOS());
        alert.showAndWait();
    }
    
}
