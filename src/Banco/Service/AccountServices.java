package Banco.Service;

import java.io.*;
import java.util.*;

public class AccountServices {

    public void criarContaBanco(int idUsuario) {
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
            System.out.println("1 - Verificar saldo");
            System.out.println("2 - Fazer transferência (em construção)");
            System.out.println("3 - Excluir conta");
            System.out.println("4 - Depósito");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            option = scan.nextInt();

            switch (option) {
                case 1 -> mostrarSaldo(idLogado);
                case 2 -> System.out.println("🚧 Em construção...");
                case 3 -> deletarConta(idLogado);
                case 4 -> {
                    System.out.println("Digite o valor do depósito: ");
                    double valor = scan.nextDouble();
                    operations.depositar(idLogado, valor);
                }
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
