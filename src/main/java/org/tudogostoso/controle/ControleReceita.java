package org.tudogostoso.controle;

import org.tudogostoso.modelo.Avaliacao;
import org.tudogostoso.modelo.Receita;
import org.tudogostoso.modelo.Usuario;
import org.tudogostoso.repositorios.IRepositorioReceitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    public List<Receita> buscarReceitaPorAutor(Usuario autor) {
        return repositorioReceita.buscarPorAutor(autor.getNome());
    }
    public List<Receita> buscarReceitaPorAutor(String autor) {
        return repositorioReceita.buscarPorAutor(autor);
    }
    public List<Receita> buscarReceitaPorTitulo(String nome) {
        return repositorioReceita.buscarPorTitulo(nome);
    }
    public List<Receita> buscarReceitaPorAvaliacao(Avaliacao avaliacao) {
        return repositorioReceita.buscarPorAvaliacao(avaliacao);
    }
    public List<Receita> buscarReceitaPorAvaliacao(int avaliacao) {
        return repositorioReceita.buscarPorAvaliacao(avaliacao);
    }
    public List<Receita> buscarReceitaPorIngrediente(String ingredietne){
        return repositorioReceita.buscarPorIngrediente(ingredietne);
    }
    public List<Receita> buscarReceitaPorCategoria(String categoria){
        return repositorioReceita.buscarPorCategoria(categoria);
    }
    public List<Receita> buscarReceitasAleatorias() {
        List<Receita> receitasAleatorias = repositorioReceita.buscar();
        Collections.shuffle(receitasAleatorias);
        return receitasAleatorias;
    }

    public Receita buscarReceitaPorAutorETitulo(Usuario autor, String nome) {
        List<Receita> receitasAutor = repositorioReceita.buscarPorAutor(autor.getNome());
        Receita receitaDesejada = null;
        for (Receita receita : receitasAutor) {
            if (receita.getTitulo().equals(nome)) {
                receitaDesejada = receita;
            }
        }
        return receitaDesejada;
    }
    public Receita buscarReceitaPorAutorETitulo(String autor, String nome) {
        List<Receita> receitasAutor = repositorioReceita.buscarPorAutor(autor);
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
