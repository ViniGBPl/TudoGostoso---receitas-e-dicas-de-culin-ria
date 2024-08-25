package org.tudogostoso.modelo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioChef extends Usuario {

    private List<Receita> minhasReceitas;

    public UsuarioChef(Usuario usuario) {
        super(usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuario.getCpf(), usuario.getReceitasFav(), usuario.getId());
        this.minhasReceitas = new ArrayList<>();
    }

    public void criarReceita(Receita receita) {
        this.minhasReceitas.add(receita);
    }

    public void removerMinhasReceitas(Receita receita) {
        this.minhasReceitas.remove(receita);
    }

    public List<Receita> getMinhasReceitas() {
        return minhasReceitas;
    }

    public void setMinhasReceitas(List<Receita> minhasReceitas) {
        this.minhasReceitas = minhasReceitas;
    }
}
