package Banco.log;
import Banco.model.Transacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionLogger {
    private static final String LOG_FILE = "Historico.json";
    private final Gson gson = new Gson();

    public  void registrarTransacao(String idUsuario, String tipo , double valor) {
        List<Transacao> historico = carregarHistorico();

        String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        Transacao transacao = new Transacao(idUsuario, tipo, valor , dataHora);

        historico.add(transacao);
        salvarHistorico(historico);
    }

    public  void registrarTransferencia(String idUsuario, String idUsuario2, String tipo , double valor) {
        List<Transacao> historico = carregarHistorico();

        String dataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        Transacao transacao = new Transacao(idUsuario, idUsuario2, tipo, valor , dataHora);

        historico.add(transacao);
        salvarHistorico(historico);
    }

    private List<Transacao> carregarHistorico(){
        try (Reader reader = new FileReader(LOG_FILE)){
            Type tipoLista = new TypeToken<List<Transacao>>() {}.getType();
            List<Transacao> historico = gson.fromJson(reader, tipoLista);
            return historico != null ? historico : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarHistorico(List<Transacao> historico) {
        try (Writer writer = new FileWriter(LOG_FILE)){
            gson.toJson(historico, writer);
        } catch (IOException e){
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }

    public  void mostrarHistorcico(String idUsuario){
    List<Transacao> historico = carregarHistorico();
    boolean encontrado = false;

    for (Transacao transacao : historico) {
        if (transacao.getIdUsuario().equals(idUsuario)) {
            System.out.printf("[%] %s de R$%.2f\n", transacao.getDataHora(), transacao.getTipo(), transacao.getValor());
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Nenhuma transação encontrada");
    }

    }
}
