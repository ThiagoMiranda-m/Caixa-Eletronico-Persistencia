package Banco.Service;

import Banco.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserService {
    private static final String USER_FILE = "Cadastro.json";
    private final Gson gson = new Gson();

    // Gera hash SHA-256 da senha
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

    public int gerarNovoID(){
        List<User> users = carregarUsuarios();
        int maxId = 0;
        for (User user : users){
            int id = Integer.parseInt(user.getId());
            if (id > maxId) maxId = id;
        }
        return maxId + 1;
    }

    private List<User> carregarUsuarios() {
        try (Reader reader = new FileReader(USER_FILE)) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
            } catch (IOException e){
            return new ArrayList<>();
        }
    }

    private void salvarUsuarios(List<User> users){
        try(Writer writer = new FileWriter(USER_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    // Registro de usuário
    public void register() {
        Scanner scan = new Scanner(System.in);
        User user = new User();

        System.out.print("Digite o seu CPF: ");
        user.setCPF(scan.next());

        System.out.print("Digite o seu nome de usuário: ");
        user.setUserName(scan.next());

        System.out.print("Digite a sua senha: ");
        String senhaOriginal = scan.next();
        user.setSenhaHash(hashSenha(senhaOriginal));

        // Gera um ID único com UUID
        int novoID = gerarNovoID();
        user.setId(String.valueOf(novoID));

        // Lê e adiciona à lista
        List<User> users =  carregarUsuarios();
        users.add(user);
        salvarUsuarios(users);

        System.out.println("✅ Conta cadastrada com sucesso!");
        new AccountServices().criarContaBanco(user.getId()); // Cria a conta bancária sincronizada
        login();
    }

    // Login do usuário
    public void login() {
        Scanner scan = new Scanner(System.in);
        AccountServices services = new AccountServices();

        System.out.print("Digite o seu CPF: ");
        String cpf = scan.next();

        System.out.print("Digite a sua senha: ");
        String senhaHash = hashSenha(scan.next());

        List<User> users = carregarUsuarios();
        for (User user : users) {
            if (user.getCPF().equals(cpf) && user.getSenhaHash().equals(senhaHash)) {
                System.out.println("✅ Login realizado com sucesso!");
                services.menuUsuario(user.getId());
                return;
            }
        }

        System.out.println("❌ CPF ou senha incorretos.");
    }
}
