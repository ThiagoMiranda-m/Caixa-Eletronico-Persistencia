package Banco.Service;
import java.io.*;
import java.util.*;

public class BankOperations {
    private final String ACCOUNT_FILE = "Accounts.txt";

    private boolean isValorInvalido(double valor) {
        if (valor <= 0) {
            System.out.println("❌ Valor inválido.");
            return true;
        }
        return false;
    }

    public void depositar(String idUsuario, double valor) {
        if (isValorInvalido(valor)) return;

        List<String> contasAtualizadas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 2 && partes[0].equals(idUsuario)) {
                    double saldoAtual = Double.parseDouble(partes[1]);
                    double novoSaldo = saldoAtual + valor;
                    contasAtualizadas.add(idUsuario + ", " + String.format("%.2f", novoSaldo));
                    encontrado = true;
                } else {
                    contasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        if (!encontrado) {
            System.out.println("❌ Conta não encontrada.");
            return;
        }

        escreverContas(contasAtualizadas, "✅ Depósito realizado com sucesso.");
    }

    public void sake(String idUsuario, double valor) {
        if (isValorInvalido(valor)) return;

        List<String> contasAtualizadas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 2 && partes[0].equals(idUsuario)) {
                    double saldoAtual = Double.parseDouble(partes[1]);
                    if (valor > saldoAtual) {
                        System.out.println("❌ Saldo indisponível para saque.");
                        return;
                    }
                    double novoSaldo = saldoAtual - valor;
                    contasAtualizadas.add(idUsuario + ", " + String.format("%.2f", novoSaldo));
                    encontrado = true;
                } else {
                    contasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        if (!encontrado) {
            System.out.println("❌ Conta não encontrada.");
            return;
        }

        escreverContas(contasAtualizadas, "✅ Saque realizado com sucesso.");
    }

    public void transferirPorID(String idEnviou, String idRecebeu, double valor) {
        if (isValorInvalido(valor)) return;

        List<String> contasAtualizadas = new ArrayList<>();
        boolean encontrouRemetente = false;
        boolean encontrouDestinatario = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                String id = partes[0];
                double saldo = Double.parseDouble(partes[1]);

                if (id.equals(idEnviou)) {
                    if (valor > saldo) {
                        System.out.println("❌ Saldo indisponível para transferência.");
                        return;
                    }
                    double novoSaldo = saldo - valor;
                    contasAtualizadas.add(id + ", " + String.format("%.2f", novoSaldo));
                    encontrouRemetente = true;
                } else if (id.equals(idRecebeu)) {
                    double novoSaldo = saldo + valor;
                    contasAtualizadas.add(id + ", " + String.format("%.2f", novoSaldo));
                    encontrouDestinatario = true;
                } else {
                    contasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler contas: " + e.getMessage());
            return;
        }

        if (!encontrouRemetente || !encontrouDestinatario) {
            System.out.println("❌ Uma das contas não foi encontrada.");
            return;
        }

        escreverContas(contasAtualizadas, "✅ Transferência realizada com sucesso.");
    }

    private void escreverContas(List<String> contas, String mensagemSucesso) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ACCOUNT_FILE, false))) {
            for (String conta : contas) {
                escritor.write(conta);
                escritor.newLine();
            }
            System.out.println(mensagemSucesso);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o saldo: " + e.getMessage());
        }
    }
}
