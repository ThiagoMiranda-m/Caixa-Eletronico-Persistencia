package Banco.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> List<T> lerLista(String caminho, Class<T> clazz){
        try(Reader reader = new FileReader(caminho)) {
            Type tipoLista = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, tipoLista);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e){
            throw new RuntimeException("Erro ao ler arquivo JSON: " + e.getMessage());
        }
    }

    public static <T> void escreverLista(String caminho, List<T> lista){
        try(Writer writer = new FileWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever arquivo JSON: " + e.getMessage());
        }
    }
}
