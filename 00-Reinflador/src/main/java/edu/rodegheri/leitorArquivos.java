package edu.rodegheri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class leitorArquivos {

    public Map<Character, String> ler(String nomeArquivo) {

        Map<Character, String> dicEntradas = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            String linha;

            while ((linha = br.readLine()) != null) {

                // Tira espaços extras no começo e fim
                linha = linha.trim();

                // Ignora linhas vazias
                if (linha.isEmpty()) {
                    continue;
                }

                // Primeira posição é a chave
                char letraOriginal = linha.charAt(0);

                // Pega a string de substituição
                String substituicao = "";
                if (linha.length() > 1) {
                    substituicao = linha.substring(1).trim();
                }

                dicEntradas.put(letraOriginal, substituicao);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }

        return dicEntradas;
    }
}