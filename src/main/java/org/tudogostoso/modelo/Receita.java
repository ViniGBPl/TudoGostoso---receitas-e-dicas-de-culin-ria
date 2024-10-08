package org.tudogostoso.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter@Setter@ToString
public class Receita implements Serializable, Comparable<Receita>{
    private final int id;
    private String titulo;
    private final UsuarioChef autor;
    private List<ItemIngrediente> ingredientes;
    private List<String> preparo;
    private List<Avaliacao> avaliacoes;
    private int nota = 0;
    private LocalDateTime horario;
    private String tempoDePreparo;
    private String categoria;
    private String caminhoImagem;

    public Receita(int id, String titulo, UsuarioChef autor, List<ItemIngrediente> ingredientes, List<String> preparo, String tempoDePreparo, String categoria) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.ingredientes = ingredientes;
        this.preparo = preparo;
        this.horario = LocalDateTime.now();
        this.avaliacoes = new ArrayList<>();
        this.tempoDePreparo= tempoDePreparo;
        this.categoria = categoria;
        this.caminhoImagem = "src/main/resources/org/tudogostoso/Imagens/fotoDefaultReceitas.jpg";
    }
    public Receita(int id, String titulo, UsuarioChef autor, List<ItemIngrediente> ingredientes, List<String> preparo, String tempoDePreparo, String categoria, String caminhoImagem) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.ingredientes = ingredientes;
        this.preparo = preparo;
        this.horario = LocalDateTime.now();
        this.avaliacoes = new ArrayList<>();
        this.tempoDePreparo= tempoDePreparo;
        this.categoria = categoria;
        this.caminhoImagem = caminhoImagem;
    }


    //ordem natural de receitas é baseado no horario
    @Override
    public int compareTo(Receita o){
        return this.horario.compareTo(o.getHorario());
    }

    //para ser diferente o id tem que ser diferente, ou o autor e titulo tem que ser diferente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receita receita = (Receita) o;
        if ((id == receita.id) == false || (autor.equals(receita.getAutor()) == false && titulo.equals(receita.getTitulo()) == false)){
            return false;
        } else {
            return true;
        }
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }
    public List<ItemIngrediente> getIngredientes() {
        return ingredientes;
    }

    public List<String> getPreparo() {
        return preparo;
    }
    public void atualizarNota(){
        int soma = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            soma += avaliacao.getNota(); //soma todas as avaliações
        }
        this.nota = soma/this.avaliacoes.size();
    }


    public void setIngredientes(List<ItemIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setPreparo(List<String> preparo) {
        this.preparo = preparo;
    }

    public List<Avaliacao> getAvaliacoes(){
        return this.avaliacoes;
    }

    public void adicioarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
        atualizarNota();
    }

    public  void removerAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.remove(avaliacao);
    }


}

