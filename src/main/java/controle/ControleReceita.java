package controle;

import modelo.Receita;

import java.util.List;

public class ControleReceita {

    private final ControleRepositorioReceita controleRepositorioReceita;

    public ControleReceita(ControleRepositorioReceita controleRepositorioReceita) {
        this.controleRepositorioReceita = controleRepositorioReceita;
    }

    public Receita cadastrarReceita(Receita receita) {
        controleRepositorioReceita.salvarReceita(receita);
        return receita;
    }

}
