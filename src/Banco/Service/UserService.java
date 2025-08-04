package Banco.Service;

import Banco.model.User;
import java.io.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class UserService {

    // Método para hashear a senha com SHA-256
    public static String hashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao hashear a senha.", e);
        }
    }

    // Gera um novo ID baseado no maior ID já existente
    public int gerarNovoID() {
        int maxId = 0;
        try (BufferedReader leitor = new BufferedReader(new FileReader("Cadastro.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length > 0 && partes[0].matches("\\d+")) {
                    int idAtual = Integer.parseInt(partes[0]);
                    if (idAtual > maxId) maxId = idAtual;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de cadastro: " + e.getMessage());
        }
        return maxId + 1;
    }

    // Registro de novo usuário
    public void register() {
        Scanner scan = new Scanner(System.in);
        User user = new User();

        System.out.print("Digite o seu CPF: ");
        user.setCPF(scan.next());

        System.out.print("Digite o seu nome de usuário: ");
        user.setUserName(scan.next());

        System.out.print("Digite a sua senha: ");
        user.setSenha(scan.next());

        int novoID = gerarNovoID();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Cadastro.txt", true))) {
            writer.write(novoID + ", " + user.getUserName() + ", " + user.getCPF() + ", " + hashSenha(user.getSenha()));
            writer.newLine();
            System.out.println("✅ Conta cadastrada com sucesso!");
            new AccountServices().criarContaBanco(novoID); // Cria conta bancária vinculada
            login();
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // Login do usuário
    public void login() {
        Scanner scan = new Scanner(System.in);
        AccountServices services = new AccountServices();

        System.out.print("Digite o seu CPF: ");
        String cpfUser = scan.next();

        System.out.print("Digite a sua senha: ");
        String senhaHashed = hashSenha(scan.next());

        boolean encontrado = false;
        String idLogado = "";

        try (BufferedReader leitor = new BufferedReader(new FileReader("Cadastro.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                if (partes.length == 4) {
                    String id = partes[0];
                    String cpf = partes[2];
                    String senha = partes[3];

                    if (cpfUser.equals(cpf) && senhaHashed.equals(senha)) {
                        encontrado = true;
                        idLogado = id;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        if (encontrado) {
            System.out.println("✅ Login realizado com sucesso!");
            services.menuUsuario(idLogado);
        } else {
            System.out.println("❌ CPF ou senha inválidos.");
        }
    }
}
