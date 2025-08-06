package Banco.Service;

import Banco.log.TransactionLogger;
import Banco.model.BankAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountServices {
    private static final String ACCOUNT_FILE = "Accounts.json";
    private static final String CADASTRO_FILE = "Cadastro.json";
    private final Gson gson = new Gson();

    public void criarContaBanco(String idUsuario) {
        List<BankAccount> contas = carregarContas();
        contas.add(new BankAccount(idUsuario, 0.0));
        salvarContas(contas);
    }

    // Mostra o saldo da conta pelo ID
    public void mostrarSaldo(String id) {
        for(BankAccount conta : carregarContas()) {
            if(conta.getId().equals(id)) {
                System.out.printf("💰 Saldo atual: R$ %.2f%n", conta.getSaldo());
                return;
            }
        }
        System.out.println("❌ Conta não encontrada.");
    }

    // Exclui a conta do usuário nos dois arquivos
    public void deletarConta(String id) {
        excluirPorID(ACCOUNT_FILE, id, BankAccount.class);
        excluirLinhaPorID(CADASTRO_FILE, id);
        System.out.println("✅ Conta removida com sucesso!");
    }

    // Menu principal do usuário
    public void menuUsuario(String idLogado) {
        Scanner scan = new Scanner(System.in);
        BankOperations operations = new BankOperations();
        TransactionLogger logger = new TransactionLogger();
        int option;

        do {
            System.out.println("\n====== Menu do Usuário ======");
            System.out.println("1 - Ver Saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Ver histórico");
            System.out.println("6 - Excluir conta");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            option = scan.nextInt();

            switch (option) {
                case 1 -> mostrarSaldo(idLogado);
                case 2 -> {
                    System.out.println("Digite o valor do depósito: ");
                    double valor1 = scan.nextDouble();
                    operations.depositar(idLogado, valor1);
                }
                case 3 ->{
                    System.out.println("Digite o valor do saque: ");
                    double valor1 = scan.nextDouble();
                    operations.saque(idLogado, valor1);
                }
                case 4 -> {
                    System.out.println("Para quem vocẽ irá transferir? ");
                    String idEnviado = scan.next();
                    System.out.println("Digite o valor da transferência: ");
                    double valor1 = scan.nextDouble();
                    operations.transferirPorID(idLogado, idEnviado, valor1);
                }
                case 5 -> logger.mostrarHistorcico(idLogado);
                case 6 -> deletarConta(idLogado);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("❌ Opção inválida.");
            }
        } while (option != 0);
    }

    /*===========AUXILIAR METHODS===============*/

    private List<BankAccount> carregarContas(){
        try(Reader reader = new FileReader(ACCOUNT_FILE)){
            Type listType = new TypeToken<List<BankAccount>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            System.out.printf("Erro ao carregar contas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarContas(List<BankAccount> contas) {
        try (Writer writer = new FileWriter(ACCOUNT_FILE)) {
            gson.toJson(contas, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    private <T> void excluirPorID(String arquivo, String id, Class<T> clazz){
        try{
            List<T> objetos;
            try(Reader reader = new FileReader(arquivo)) {
                Type tipo = TypeToken.getParameterized(List.class, clazz).getType();
                objetos = gson.fromJson(reader, tipo);
            }

            if(objetos != null){
                objetos.removeIf(obj -> {
                    if (clazz == BankAccount.class) {
                        return ((BankAccount) obj).getId().equals(id);
                    }
                    return false;
                });
                try (Writer writer = new FileWriter(arquivo)){
                    gson.toJson(objetos, writer);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao excluir do arquivo JSON: " + e.getMessage());
        }
    }

    // Função genérica para excluir linha de um arquivo
    private void excluirLinhaPorID(String arquivo, String id) {
        List<String> linhasMantidas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.startsWith(id + ", ")) {
                    linhasMantidas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + arquivo + ": " + e.getMessage());
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, false))) {
            for (String linha : linhasMantidas) {
                escritor.write(linha);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo " + arquivo + ": " + e.getMessage());
        }
    }
}
