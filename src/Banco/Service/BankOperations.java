package Banco.Service;
import Banco.log.TransactionLogger;
import Banco.model.BankAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BankOperations {
    private final String ACCOUNT_FILE = "Accounts.txt";
    private final Gson gson = new Gson();

    private boolean isValorInvalido(double valor) {
        if (valor <= 0) {
            System.out.println("❌ Valor inválido.");
            return true;
        }
        return false;
    }

    public void depositar(String idUsuario, double valor) {
        if (isValorInvalido(valor)) return;

        List<BankAccount> contas = carregarContas();
        boolean encontrado = false;

        for (BankAccount conta : contas) {
            if (conta.getId().equals(idUsuario)) {
                conta.setSaldo(conta.getSaldo() + valor);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            salvarContas(contas);
            System.out.println("✅ Depósito realizado com sucesso.");
        } else {
            System.out.println("❌ Conta não encontrada.");
        }
    }

    public void saque(String idUsuario, double valor) {
        if (isValorInvalido(valor)) return;

        List<BankAccount> contas = carregarContas();
        boolean encontrado = false;

        for (BankAccount conta : contas){
            if (conta.getId().equals(idUsuario)) {
                if (valor > conta.getSaldo()) {
                    System.out.println("❌ Saldo insuficiente.");
                    return;
                }
                conta.setSaldo(conta.getSaldo() - valor);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            salvarContas(contas);
            System.out.println("✅ Saque realizado com sucesso.");
        } else {
            System.out.println("❌ Conta não encontrada.");
        }
    }

    public void transferirPorID(String idEnviou, String idRecebeu, double valor) {
        if (isValorInvalido(valor)) return;

        List<BankAccount> contas = carregarContas();
        BankAccount Remetente = null;
        BankAccount Destinatario = null;

        for (BankAccount conta : contas) {
            if (conta.getId().equals(idEnviou)) {
                Remetente = conta;
            } else if (conta.getId().equals(idRecebeu)) {
                Destinatario = conta;
            }
        }

        if (Remetente == null || Destinatario == null) {
            System.out.println("❌ Uma das contas não foi encontrada.");
            return;
        }

        if ( Remetente.getSaldo() < valor) {
            System.out.println("❌ Saldo insuficiente para transferência.");
            return;
        }

        Remetente.setSaldo(Remetente.getSaldo() - valor);
        Destinatario.setSaldo(Destinatario.getSaldo() + valor);
        salvarContas(contas);
        System.out.println("✅ Transferência realizada com sucesso.");
    }


    /*============METODOS AUXILIARES==============*/

    private List<BankAccount> carregarContas(){
        try (Reader reader = new FileReader(ACCOUNT_FILE)) {
            Type tipoLista = new TypeToken<List<BankAccount>>(){}.getType();
            List<BankAccount> contas = gson.fromJson(reader, tipoLista);
            return contas != null ? contas : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarContas (List<BankAccount> contas) {
        try (Writer writer = new FileWriter(ACCOUNT_FILE)) {
            gson.toJson(contas, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }
}
