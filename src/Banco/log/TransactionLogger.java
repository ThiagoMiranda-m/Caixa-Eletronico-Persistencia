package Banco.log;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLogger {
    private static final String HISTORICO_PATH = "Historico.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void registrar(String mensagem) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String linha = "[" + timestamp + "] " + mensagem;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORICO_PATH, true))) {
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao registrar histórico: " + e.getMessage());
        }
    }

    public static void registrarTransferencia(String deID, String paraID, double valor){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORICO_PATH, true))){
            writer.write("[TRANSFERÊNCIA] DE: " + deID + " | PARA: " + paraID + " | Valor: " + valor);
        } catch (IOException e) {
            System.out.println("Erro ao registrar a transferência: " + e.getMessage());
        }

    }
}
