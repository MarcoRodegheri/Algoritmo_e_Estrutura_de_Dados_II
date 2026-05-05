package edu.rodegheri;

/*
a memimomu
e mimomu
i mooo
u mimimi
o // Opcional! Podia não estar aqui!
m // Opcional! Podia não estar aqui!
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeitorDeArquivos {

    // Método que faz o trabalho de leitura e retorna o Map pronto
    public Map<Character, String> ler(String nomeArquivo) {
        Map<Character, String> regras = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim(); // Limpa espaços extras
                
                // Ignora linhas vazias ou comentários (que começam com //)
                if (linha.isEmpty() || linha.startsWith("//")) {
                    continue;
                }

                // A primeira letra é a chave
                char letraOriginal = linha.charAt(0);
                
                // Pega a string de substituição (se for só a letra, fica vazio "")
                String substituicao = "";
                if (linha.length() > 1) {
                    substituicao = linha.substring(1).trim();
                }
                
                regras.put(letraOriginal, substituicao);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }

        // Devolve o mapa preenchido para quem chamou a função
        return regras;
    }
}