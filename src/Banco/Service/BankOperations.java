package Banco.Service;
import java.io.*;
import java.util.*;


public class BankOperations {
    Scanner scan = new Scanner(System.in);

    public void depositar(String idUsuario, double valor) {
        if (valor <= 0) {
            System.out.println("❌ Valor inválido para saque.");
            return;
        }

        List<String> contasAtualizadas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader("Accounts.txt"))) {
            String linha;
            boolean encontrado = false;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 2 && partes[0].equals(idUsuario)) {
                    double saldoAtual = Double.parseDouble(partes[1]);
                    double novoSaldo = saldoAtual + valor;
                    contasAtualizadas.add(idUsuario + ", " + novoSaldo);
                    encontrado = true;
                } else {
                    contasAtualizadas.add(linha); // Mantém outras contas
                }
            }

            if (!encontrado) {
                System.out.println("❌ Conta não encontrada.");
                return;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Accounts.txt", false))) {
            for (String conta : contasAtualizadas) {
                escritor.write(conta);
                escritor.newLine();
            }
            System.out.println("✅ Depósito realizado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o saldo: " + e.getMessage());
        }
    }

    public void sake(String idUsuario, double valor){
        if (valor <= 0) {
            System.out.println("❌ Valor inválido para saque.");
            return;
        }

        List<String> contasAtualizadas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader("Accounts.txt"))) {
            String linha;
            boolean encontrado = false;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 2 && partes[0].equals(idUsuario)) {
                    double saldoAtual = Double.parseDouble(partes[1]);
                    if (valor <= saldoAtual) {
                        double novoSaldo = saldoAtual - valor;
                        String saldoFormatado = String.format("%.2f", novoSaldo);
                        contasAtualizadas.add(idUsuario + ", " + saldoFormatado);
                        encontrado = true;
                    } else {
                        System.out.println("❌ Saldo indisponível para saque.");
                        return;
                    }
                } else {
                    contasAtualizadas.add(linha); // Mantém outras contas
                }
            }

            if (!encontrado) {
                System.out.println("❌ Conta não encontrada.");
                return;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Accounts.txt", false))) {
            for (String conta : contasAtualizadas) {
                escritor.write(conta);
                escritor.newLine();
            }
            System.out.println("✅ Saque realizado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o saldo: " + e.getMessage());
        }

    }

    public void transferirPorID (String IdEnviado, String idRecebe, double valor){
        if (valor <= 0) {
            System.out.println("❌ Valor inválido para saque.");
            return;
        }

        List<String> contasAtualizada = new ArrayList<>();
        boolean contaEnviouEncontrada = false;
        boolean contaEnviouRecebida = false;

        try {
            BufferedReader leitor = new BufferedReader(new FileReader("Accounts.txt"));
            String linha;


            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                String id = partes[0];
                double saldo = Double.parseDouble(partes[1]);

                if (id.equals(IdEnviado)) {
                    if (valor > saldo) {
                        System.out.println("❌ Saldo indisponível para saque.");
                        return;
                    }
                    double novoSaldo = saldo - valor;
                    contasAtualizada.add(IdEnviado + ", " + novoSaldo);
                    contaEnviouEncontrada = true;
                } else if (id.equals(idRecebe)) {
                    double novoSaldo = saldo + valor;
                    contasAtualizada.add(idRecebe + ", " + novoSaldo);
                    contaEnviouRecebida = true;
                } else {
                    contasAtualizada.add(linha);
                }

            }
        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        if (!contaEnviouEncontrada || !contaEnviouRecebida) {
            System.out.println("❌ Uma das contas não foi encontrada.");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Accounts.txt", false))) {
            for (String conta : contasAtualizada) {
                escritor.write(conta);
                escritor.newLine();
            }
            System.out.println("✅ Transferência realizada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao realizar a transferência: " + e.getMessage());
        }
    }




}
