package org.tudogostoso.fxcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.tudogostoso.controle.Controle;
import org.tudogostoso.controle.ControleFactory;
import org.tudogostoso.exceptions.ObjetoJaExiste;
import org.tudogostoso.modelo.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FxEditarReceitasController {
    @FXML
    private Button arquivos;

    @FXML
    private Text textIngrediente, textUnidadeDeMedida;

    @FXML
    private ImageView imagemEscolhida;

    @FXML
    private ChoiceBox<Ingrediente> choicheBoxIngrediente;
    @FXML
    private ChoiceBox<UnidadeMedida> choiceBoxUnidadeDeMedida;

    @FXML
    private TextField textFieldQuantidade, textFieldTitulo, textFieldTempodDePreapro,textFieldCateogira, textFieldNomeIngrediente;

    @FXML
    private TextArea textAreaItemnsIngredientes, textAreaPreparo;

    List<ItemIngrediente> itemIngredientes = new ArrayList<>();
    private final Controle controle = ControleFactory.criarControleGeral();
    private File caminhoArquivoUsuario;
    private final FxGerenciadorTelas gerenciadorTelas = FxGerenciadorTelas.getInstance();
    private final Receita receita = Sessao.getReceitaSessao();

    @FXML
    public void initialize() {

        //configura as listas de ingredientes e unidades de medidas
        List<Ingrediente> ingredientes = controle.buscarIngrediente();
        ObservableList<Ingrediente> observableIngredientes = FXCollections.observableArrayList(ingredientes);
        List<UnidadeMedida> unidadesDeMedida = controle.buscarUnidadeDeMedida();
        ObservableList<UnidadeMedida> observableUnidadesDeMedida = FXCollections.observableArrayList(unidadesDeMedida);

        choicheBoxIngrediente.setItems(observableIngredientes);
        choiceBoxUnidadeDeMedida.setItems(observableUnidadesDeMedida);

        choicheBoxIngrediente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Verifica se um valor foi selecionado
            if (newValue != null) {
                textIngrediente.setVisible(false); // Oculta o texto
            }
        });
        choiceBoxUnidadeDeMedida.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Verifica se um valor foi selecionado
            if (newValue != null) {
                textUnidadeDeMedida.setVisible(false); // Oculta o texto
            }
        });

        //Seta a imagem da receita
        imagemEscolhida.setImage(new Image(new File(receita.getCaminhoImagem()).getAbsolutePath()));

        //seta o texto de preparo
        String preparo = "";
        for (String linhaPreparo : receita.getPreparo()) {
            preparo = preparo.concat(linhaPreparo + "\n");
        }
        textAreaPreparo.setText(preparo);

        //seta a categoria, o titulo e tempo de preparo

        textFieldCateogira.setText(receita.getCategoria());
        textFieldTitulo.setText(receita.getTitulo());
        textFieldTempodDePreapro.setText(receita.getTempoDePreparo());

        String ingredientesReceita = "";
        for (ItemIngrediente linhaIngrediente : receita.getIngredientes()){
            ingredientesReceita = ingredientesReceita.concat(linhaIngrediente + "\n");
        }
        textAreaItemnsIngredientes.setText(ingredientesReceita);
        itemIngredientes.addAll(receita.getIngredientes());

    }

    @FXML
    void HandllerButtonVoltar(ActionEvent event) {
        gerenciadorTelas.mudarTela("minhasReceitas",event);
    }

    @FXML
    void handllerButtonAdicionarItemIngrediente() {
        Ingrediente ingrediente = choicheBoxIngrediente.getValue();
        UnidadeMedida unidadeMedida = choiceBoxUnidadeDeMedida.getValue();
        try {
            double quantidade = Double.parseDouble(textFieldQuantidade.getText());
            ItemIngrediente itemIngrediente = controle.criarItemIngrediente(ingrediente, quantidade, unidadeMedida);

            String textoItem = textAreaItemnsIngredientes.getText();
            textoItem = textoItem + itemIngrediente + "\n";
            textAreaItemnsIngredientes.setText(textoItem);
            itemIngredientes.add(itemIngrediente);
            choicheBoxIngrediente.setValue(null);
            choiceBoxUnidadeDeMedida.setValue(null);
            textFieldQuantidade.clear();
            textUnidadeDeMedida.setVisible(true);
            textIngrediente.setVisible(true);

        }catch (NullPointerException e ){
            mostrarAlerta(Alert.AlertType.ERROR, "Campo vazio", "Algum dos campos para adicionar item esta vazio");
        } catch (NumberFormatException e){
            mostrarAlerta(Alert.AlertType.ERROR, "Dado invalido", "Preencha a quantidade com um numero valido");
        }


    }

    @FXML
    void handllerButtonCriarIngrediente() {
        try {
            String nomeIngrediente = textFieldNomeIngrediente.getText();
            Ingrediente ingrediente = controle.criarIngrediente(nomeIngrediente);
            ObservableList<Ingrediente> lista = choicheBoxIngrediente.getItems();
            lista.add(ingrediente);
            textFieldNomeIngrediente.clear();

        } catch (ObjetoJaExiste e ) {
            mostrarAlerta(Alert.AlertType.ERROR, "Ingrediente Já existe", "Esse ingrediente ja existe, procure na lista de ingrediente a cima");
        }catch (NullPointerException e){
            mostrarAlerta(Alert.AlertType.ERROR, "Campo vazio", "Preencha o campo com o nome do ingrediente");
        }
    }

    @FXML
    void arquivosHandle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione um Arquivo");

        // Filtrar apenas para arquivos de imagem
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );

        // Abre o FileChooser e obtém o arquivo selecionado
        Stage stage = (Stage) arquivos.getScene().getWindow(); // Obtém a janela atual
        caminhoArquivoUsuario = fileChooser.showOpenDialog(stage);
        if (caminhoArquivoUsuario != null) {
            Image imagem = new Image(caminhoArquivoUsuario.toURI().toString());
            imagemEscolhida.setImage(imagem);
        }


    }
    @FXML
    void handllerButtonSalvarEdicao(ActionEvent event) {
        UsuarioChef usuarioChef = (UsuarioChef) Sessao.getUsuarioSessao();
        String titulo = textFieldTitulo.getText();
        String tempoPreparo = textFieldTempodDePreapro.getText();
        String categoria = textFieldCateogira.getText();

        // Verifica se todos os campos obrigatórios estão preenchidos
        if (titulo == null || titulo.trim().isEmpty() ||
                tempoPreparo == null || tempoPreparo.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty() ||
                itemIngredientes.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campo Vazio", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        // Separa o preparo em linhas
        List<String> preparo = Arrays.asList(textAreaPreparo.getText().split("\n"));

        try {
            // Se alterou a foto
            if (caminhoArquivoUsuario != null) {
                String caminhoImagemAtual = receita.getCaminhoImagem();
                if (caminhoImagemAtual != null && !caminhoImagemAtual.equals("src/main/resources/org/tudogostoso/Imagens/fotoDefaultReceitas.jpg")) {
                    controle.excluirImagem(caminhoImagemAtual);
                }
                String nomeArquivoImagem = usuarioChef.getNome() + "_" + titulo.replace(" ", "") + ".jpg";
                String caminhoImagem = controle.salvarImagem(caminhoArquivoUsuario, nomeArquivoImagem);
                receita.setCaminhoImagem(caminhoImagem);
            }

            receita.setCategoria(categoria);
            receita.setTempoDePreparo(tempoPreparo);
            receita.setTitulo(titulo);
            receita.setPreparo(preparo);
            receita.setIngredientes(itemIngredientes);

            controle.atualizarReceita(receita);

            mostrarAlerta(Alert.AlertType.CONFIRMATION, "Mudanças salvas com sucesso", "Sua Receita \"" + titulo + "\" foi alterada com sucesso");
            gerenciadorTelas.mudarTela("minhasReceitas", event);

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao salvar nova imagem", "Erro ao salvar imagem: " + e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao atualizar receita", "Erro ao atualizar receita: " + e.getMessage());
        }
    }
    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String mensagem) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
    //Vou testar ainda e ajustar
    @FXML
    void handleBuscarImagemWeb() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Imagem no Google");
        dialog.setHeaderText("Digite o termo de pesquisa para buscar imagens:");
        dialog.setContentText("Pesquisa:");

        dialog.showAndWait().ifPresent(termoPesquisa -> {
            try {
                // Número de imagens que você deseja buscar
                int numImagens = 5;

                // Chama a classe GoogleImagens para buscar múltiplas imagens
                List<String> urlsImagens = GoogleImagens.buscarImagens(termoPesquisa, numImagens);

                // Criar uma lista de escolha para o usuário selecionar uma das imagens
                ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(urlsImagens.get(0), urlsImagens);
                choiceDialog.setTitle("Escolha uma Imagem");
                choiceDialog.setHeaderText("Selecione a imagem desejada:");
                choiceDialog.setContentText("Imagens:");

                choiceDialog.showAndWait().ifPresent(urlImagem -> {
                    // Nome do arquivo para salvar a imagem
                    String nomeArquivo = "imagemSelecionada_" + System.currentTimeMillis() + ".jpg"; // Nome único baseado no timestamp

                    // Salva a imagem no diretório especificado
                    try {
                        String caminhoImagem = GoogleImagens.salvarImagem(urlImagem, nomeArquivo);
                        Image imagem = new Image(new File(caminhoImagem).toURI().toString());
                        imagemEscolhida.setImage(imagem);
                        caminhoArquivoUsuario = new File(caminhoImagem); // Atualiza o caminho da imagem na variável
                    } catch (IOException e) {
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro ao salvar imagem", "Erro ao salvar imagem: " + e.getMessage());
                    }
                });

            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao buscar imagem", "Erro ao buscar imagem: " + e.getMessage());
            }
        });
    }



}
