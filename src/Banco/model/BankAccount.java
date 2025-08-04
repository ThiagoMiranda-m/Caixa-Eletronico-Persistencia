package Banco.model;

public class BankAccount {
    private String ID;
    private double AccountBalance;
    private boolean StatusAccount;

    public double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        AccountBalance = accountBalance;
    }

    public boolean isStatusAccount() {
        return StatusAccount;
    }

    public void setStatusAccount(boolean statusAccount) {
        StatusAccount = statusAccount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
