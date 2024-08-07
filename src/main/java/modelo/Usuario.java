package modelo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.mail.internet.InternetAddress;


public class Usuario implements Serializable {
    private static int numIds = 0;
    protected final int id;
    private String nome;
    private String senha;
    private InternetAddress email;
    private final String cpf;
    private List<Receita> receitasFav;

    //usuario não vai ser intanciado diretamente, quem vai intanciar vai ser controle, depois criar um factory
    public Usuario(String nome, String senha, InternetAddress email, String cpf  ) {
        this.id = gerarId();
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
        this.receitasFav = new ArrayList<Receita>();
    }
    //construtor que so vai ser usado para poder Criar a UsuarioChef
    public Usuario(String nome, String senha, InternetAddress email, String cpf, List<Receita> receitasFav, int id){
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
        this.receitasFav = receitasFav;
    }

    public int gerarId(){
        return ++numIds;
    }
    public boolean equals(Receita receita) {
        return receita.getId() == getId();
    }

    public String toString(){
        return(id + ": " + nome + ", " + email);
    }

    public void addReceitasFav(Receita receita) {
        this.receitasFav.add(receita);
    }

    public void removerReceitasFav(Receita receita) {
        this.receitasFav.remove(receita);
    }

    //gets e sets

    public static int getNumIds() {
        return numIds;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public InternetAddress getEmail() {
        return email;
    }

    public void setEmail(InternetAddress email) {
        this.email = email;
    }

    public String getCpf(){return cpf;}

    public List<Receita> getReceitasFav() {
        return receitasFav;
    }

    public void setReceitasFav(List<Receita> receitasFav) {
        this.receitasFav = receitasFav;
    }

}
