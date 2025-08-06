package Banco.model;

public class Transacao {
    private String idUsuario;

    private String idUsuario2;

    private String tipo;
    private double valor;

    private String dataHora;

    public Transacao(String idUsuario, String tipo, double valor, String dataHora) {
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.valor = valor;
        this.idUsuario = idUsuario;
    }

    public Transacao(String idUsuario, String idUsuario2, String tipo, double valor, String dataHora) {
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.valor = valor;
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(String idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }
}
