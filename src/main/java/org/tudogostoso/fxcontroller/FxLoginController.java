package org.tudogostoso.fxcontroller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.tudogostoso.controle.Controle;
import org.tudogostoso.controle.ControleFactory;
import java.util.HashMap;
import java.util.Map;


public class FxLoginController {

    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private Button botaoLogin;
    @FXML
    private Button botaoIrParaCadastro;

    private static Controle controle = ControleFactory.criarControleGeral();
    private Stage stage;
    private Scene scene;
    private Parent root;

    private FxGerenciadorTelas gerenciadorTelas = FxGerenciadorTelas.getInstance();

    // Armazenamento simulado de usuários e senhas
    private static final Map<String, String> usuarios = new HashMap<>();

    static {
        // Usuários e senhas pré-cadastrados
        usuarios.put("admin", "1234");
        usuarios.put("user", "abcd");
    }

    @FXML
    private void fazerLogin(ActionEvent event) {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        // Verifica se o usuário existe e se a senha está correta
        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(senha)) {
            mostrarAlerta(AlertType.INFORMATION, "Login Sucesso", "Bem-vindo, " + usuario + "!");
            //Sessao.setUsuarioSessao(usuario); tem que fazer a verificação e intanciar o objeto para essa linha funcionar
           gerenciadorTelas.mudarTela("feed",event);

        } else {
            mostrarAlerta(AlertType.ERROR, "Falha no Login", "Usuário ou senha incorretos.");
        }

        limparCampos();
    }

    @FXML
    private void irParaTelaCadastro (ActionEvent event) {
       gerenciadorTelas.mudarTela("cadastro",event);
    }

    private void mostrarAlerta(AlertType tipoAlerta, String titulo, String mensagem) {
    Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
}

    private void limparCampos() {
        campoUsuario.clear();
        campoSenha.clear();
    }
}

