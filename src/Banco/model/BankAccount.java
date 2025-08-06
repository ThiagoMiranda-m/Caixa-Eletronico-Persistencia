package Banco.model;

public class BankAccount {

    public BankAccount(String id, double saldo){
        this.id = id;
        this.saldo = saldo;
    }

    private String id;
    private double saldo;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
