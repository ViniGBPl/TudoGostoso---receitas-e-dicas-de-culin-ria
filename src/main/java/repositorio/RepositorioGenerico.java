package repositorio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioGenerico <T> {


    //as subclasses podem ter acesso a path, assim mudando o caminho para cada repositorio
    protected Path path;

    public RepositorioGenerico(String filePath) {
        this.path = Paths.get(filePath);
    }
    public List<T> buscar(){

        List<T> objetos = new ArrayList<>();
        try(ObjectInputStream input = new ObjectInputStream( Files.newInputStream(this.path))){
            try{
                objetos = (List<T>) input.readObject();
            }catch (ClassNotFoundException e){
                System.out.println("erro ao ler arquivo");
            }
        }catch (IOException e){
            System.out.println("Erro ao abrir arquivo");
        }
        return objetos;
    }

    //salva usurio novo
    public void salvar(T objeto) {

        List<T> objetos = buscar();
        objetos.add(objeto);
        try(ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(this.path))){
            output.writeObject(objetos);
        }catch (IOException e){
            System.out.println("Erro ao abrir arquivo para escrever");
        }
    }
    //remove usuario existente
    public void excluir(T objeto) {
        List<T> objetos = buscar();
        int hUsuarios = objetos.size();
        objetos.remove(objeto);

        //verefica se a lista foi alterada, se ela for, significa que existe o usuario e foi excluido
        if (objetos.size() != hUsuarios){
            try(ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(this.path))){
                output.writeObject(objetos);
            }catch (IOException e){
                System.out.println("Erro ao abrir arquivo para escrever");
            }
        }
    }
}


