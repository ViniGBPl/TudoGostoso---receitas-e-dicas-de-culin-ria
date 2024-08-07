package modelo;

public enum UnidadeMedida {

    CHICARA("xícara"),
    COLHER_DE_CHA("colher de chá"),
    COLHER_DE_SOPA("colher de sopa"),
    GRAMAS("gramas"),
    LITROS("litros"),
    MINILITROS("minilitros");

    private final String tipo;

    UnidadeMedida(String tipo){
        this.tipo = tipo;
    }
    public String getTipo(){return this.tipo;}
}
