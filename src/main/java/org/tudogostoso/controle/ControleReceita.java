package org.tudogostoso.controle;

import org.tudogostoso.modelo.Avaliacao;
import org.tudogostoso.modelo.ItemIngrediente;
import org.tudogostoso.modelo.Receita;
import org.tudogostoso.modelo.Usuario;
import org.tudogostoso.repositorios.IRepositorioReceitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControleReceita {

    private final IRepositorioReceitas repositorioReceita;

    public ControleReceita(IRepositorioReceitas repositorioReceita) {
        this.repositorioReceita = repositorioReceita;
    }

    public void salvarReceita(Receita receita) {
        repositorioReceita.salvar(receita);
    }
    public void excluirReceita(Receita receita) {
        repositorioReceita.excluir(receita);
    }
    public void atualizarReceita(Receita receita) {
        repositorioReceita.update(receita);
    }
    public List<Receita> buscarTodasRecetias(){
     return repositorioReceita.buscar();
    }

    public List<Receita> buscarReceitaPorAutor(Usuario autor) {
        List<Receita> items = repositorioReceita.buscar();
        return items.stream()
                .filter(receita -> receita.getAutor().getNome().equalsIgnoreCase(autor.getNome()))
                .collect(Collectors.toList());
    }
    public List<Receita> buscarReceitaPorAutor(String autor) {
        List<Receita> items = repositorioReceita.buscar();
        return items.stream()
                .filter(receita -> receita.getAutor().getNome().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }
    public List<String> sugestaoReceitaPorAutor (String nomeAutor){
        List<Receita> items = repositorioReceita.buscar();
        List<String> sugestoes = new ArrayList<>();
        for (Receita item : items) {
            if(item.getAutor().getNome().indexOf(nomeAutor) == 0){
                sugestoes.add(item.getAutor().getNome());
            }
        }
        return sugestoes;
    }

    public List<Receita> buscarReceitaPorTitulo(String nome) {
        List<Receita> items = repositorioReceita.buscar();
        return items.stream()
                .filter(receita -> receita.getTitulo().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }
    public List<String> sugestaoReceitaPorTitulo(String titulo){
        List<Receita> items = repositorioReceita.buscar();
        List<String> sugestoes = new ArrayList<>();
        for (Receita item : items) {
            if(item.getTitulo().indexOf(titulo) == 0){
                sugestoes.add(item.getTitulo());
            }
        }
        return sugestoes;
    }

    public List<Receita> buscarReceitaPorAvaliacao(Avaliacao avaliacao) {
        List<Receita> items = repositorioReceita.buscar();
        return items.stream()
                .filter(receita -> receita.getAvaliacoes().stream()
                        .anyMatch(a -> a.equals(avaliacao)))
                .collect(Collectors.toList());
    }
    public List<Receita> buscarReceitaPorAvaliacao(int avaliacao) {
        List<Receita> items = repositorioReceita.buscar();
        List<Receita> receitasDesejadas = new ArrayList<>();

        for (Receita item : items) {
            if (item.getNota() == avaliacao){
                receitasDesejadas.add(item);
            }
        }
        return receitasDesejadas;
    }

    public List<Receita> buscarReceitaPorIngrediente(String ingrediente){
        List<Receita> items = repositorioReceita.buscar();
        List<Receita> receitasDesejadas = new ArrayList<>();

        for (Receita item : items) {
            for (ItemIngrediente ing : item.getIngredientes()) {
                if (ing.getIngrediente().getNome().equals(ingrediente)){
                    receitasDesejadas.add(item);
                }
            }
        }
        return receitasDesejadas;
    }
    public List<String> sugestaoReceitaPorIngrediente(String ingrediente) {
        List<Receita> items = repositorioReceita.buscar();
        List<String> sugestoes = new ArrayList<>();
        for (Receita item : items) {
            for (ItemIngrediente ing : item.getIngredientes()) {
                if(ing.getIngrediente().getNome().indexOf(ingrediente)== 0){
                    sugestoes.add(ing.getIngrediente().getNome());
                }
            }
        }
        return sugestoes;
    }

    public List<Receita> buscarReceitaPorCategoria(String categoria){
        List<Receita> items = repositorioReceita.buscar();
        List<Receita> receitasDesejadas = new ArrayList<>();
        for (Receita item : items) {
            if (item.getCategoria().equals(categoria)){
                receitasDesejadas.add(item);
            }
        }
        return receitasDesejadas;
    }
    public List<String> sugestaoReceitaPorCategoria(String categoria){
        List<Receita> items = repositorioReceita.buscar();
        List<String> sugestoes = new ArrayList<>();
        for (Receita item : items) {
            if(item.getCategoria().indexOf(categoria) == 0){
                sugestoes.add(item.getCategoria());
            }
        }
        return sugestoes;
    }

    public List<Receita> buscarReceitasAleatorias() {
        List<Receita> receitasAleatorias = repositorioReceita.buscar();
        Collections.shuffle(receitasAleatorias);
        return receitasAleatorias;
    }

    public Receita buscarReceitaPorAutorETitulo(Usuario autor, String nome) {
        List<Receita> receitasAutor = buscarReceitaPorAutor(autor.getNome());
        Receita receitaDesejada = null;
        for (Receita receita : receitasAutor) {
            if (receita.getTitulo().equals(nome)) {
                receitaDesejada = receita;
            }
        }
        return receitaDesejada;
    }
    public Receita buscarReceitaPorAutorETitulo(String autor, String nome) {
        List<Receita> receitasAutor = buscarReceitaPorAutor(autor);
        Receita receitaDesejada = null;
        for (Receita receita : receitasAutor) {
            if (receita.getTitulo().equals(nome)) {
                receitaDesejada = receita;
            }
        }
        return receitaDesejada;
    }

    public Receita buscarReceitaPorId(int id) {
        List<Receita> receitas = repositorioReceita.buscar();

        for (Receita receita : receitas) {
            if (receita.getId() == id) {
                return receita;
            }
        }
        return null;
    }
    public int getLastId() {
        int id;
        try {
            id = repositorioReceita.getLastId();
        } catch (NullPointerException e){
            id = 0;
        }
        return id;
    }
}
