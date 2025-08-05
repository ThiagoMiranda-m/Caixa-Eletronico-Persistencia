package Banco.model;

public class User {
    private String id;
    private String userName;
    private String CPF;
    private String senhaHash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
    public void setSenhaHash(String senha) {
        this.senhaHash = senha;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}
