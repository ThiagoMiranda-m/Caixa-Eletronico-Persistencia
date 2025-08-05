package Banco.Service;

import Banco.model.BankAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccountServices {
    private static final String ACCOUNT_FILE = "Accounts.json";
    private final Gson gson = new Gson();

    public void criarContaBanco(String idUsuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.txt", true))) {
            writer.write(idUsuario + ", 0.0");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao criar conta bancária: " + e.getMessage());
        }
    }

    // Menu principal do usuário
    public void menuUsuario(String idLogado) {
        Scanner scan = new Scanner(System.in);
        BankOperations operations = new BankOperations();
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
                    operations.depositar(idLogado, valor1);
                }
                case 4 -> {
                    System.out.println("Para quem vocẽ irá transferir? ");
                    String idEnviado = scan.next();
                    System.out.println("Digite o valor da transferência: ");
                    double valor1 = scan.nextDouble();
                    operations.transferirPorID(idLogado, idEnviado, valor1);
                }
                case 5 -> System.out.println("Em construção...");
                case 6 -> deletarConta(idLogado);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("❌ Opção inválida.");
            }
        } while (option != 0);
    }

    // Mostra o saldo da conta pelo ID
    public void mostrarSaldo(String id) {
        try (BufferedReader leitor = new BufferedReader(new FileReader("Accounts.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes[0].equals(id)) {
                    System.out.println("💰 Saldo atual: R$" + partes[1]);
                    return;
                }
            }
            System.out.println("Conta não encontrada.");
        } catch (IOException e) {
            System.out.println("Erro ao verificar saldo: " + e.getMessage());
        }
    }

    // Exclui a conta do usuário nos dois arquivos
    public void deletarConta(String id) {
        excluirLinhaPorID("Cadastro.txt", id);
        excluirLinhaPorID("Accounts.txt", id);
        System.out.println("✅ Conta removida com sucesso!");
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
