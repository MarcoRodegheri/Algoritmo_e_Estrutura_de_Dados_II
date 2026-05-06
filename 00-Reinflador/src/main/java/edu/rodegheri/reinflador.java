package edu.rodegheri;

import java.util.HashMap;
import java.util.Map;

public class reinflador {

    public static void main(String args[]) {

        for (int i = 0; i < 11; i++) {

            // Le arquivo por arquivo
            String numeroArquivo = String.format("%02d", i);

            String caminhoArquivo = "/workspaces/Algoritmo_e_Estrutura_de_Dados_II/00-Reinflador/src/main/Entradas/t11_"
                   + numeroArquivo + ".txt";

            leitorArquivos leitor = new leitorArquivos();

            Map<Character, String> dicEntradas = leitor.ler(caminhoArquivo);

            // Descobre o caracter inicial
            char caracterInicial = descobriCaracterInicial(dicEntradas);

            System.out.println("Entrada " + numeroArquivo + " - Caracter inicial é: " + caracterInicial);
            

            // Quantidade de carateres finais
            Map<Character, Long> dicTamannhos = new HashMap<>();

            long tamanhoFinal = calcularTamanhoReinflado(caracterInicial, dicEntradas, dicTamannhos);

            System.out.println(tamanhoFinal + " caracteres no total\n");

            // Valor de cara caracter
            for (Map.Entry<Character, Long> caracter : dicTamannhos.entrySet()) {
                System.out
                        .println(caracter.getKey() + " = " + caracter.getValue()
                                + " caracteres");
            }
            System.out.println("\n\n\n");
        
        }

    }

    public static long calcularTamanhoReinflado(char letraAtual, Map<Character, String> dicEntradas,
            Map<Character, Long> dicTamannhos) {

        // Verifica se o tamanho da letra está guardado
        if (dicTamannhos.containsKey(letraAtual)) {
            return dicTamannhos.get(letraAtual);
        }

        // Pega o valor do Caracter
        String substituicao = dicEntradas.get(letraAtual);

        // Verifica se a letra é vazia
        if (substituicao == null || substituicao.isEmpty()) {
            dicTamannhos.put(letraAtual, 1L);
            return 1L;
        }

        long tamanhoTotalDessaLetra = 0;

        // Calcula o tamanaho de cada letra filha, e soma total
        for (int i = 0; i < substituicao.length(); i++) {

            char letraFilha = substituicao.charAt(i);
            tamanhoTotalDessaLetra += calcularTamanhoReinflado(letraFilha, dicEntradas, dicTamannhos);
        }

        // Salva valor no mapa para não calcular denovo
        dicTamannhos.put(letraAtual, tamanhoTotalDessaLetra);

        return tamanhoTotalDessaLetra;
    }

    public static char descobriCaracterInicial(Map<Character, String> dicEntradas) {

        // Pega chave a chave do Map
        for (Character candidata : dicEntradas.keySet()) {

            boolean apareceuEmAlgumLugar = false;

            // Pega substituição por substituição do Map, e verifica se a letra candidata
            // está lá
            for (String substituicao : dicEntradas.values()) {

                // Se não estiver, retorna -1
                if (substituicao.indexOf(candidata) != -1) {

                    apareceuEmAlgumLugar = true;
                    break;
                }
            }

            // Não apareceu, terá valor Falso e Não é vazia, terá valor Falso -> tem de retornar
            if (!apareceuEmAlgumLugar && !dicEntradas.get(candidata).isEmpty()) {
                return candidata;
            }
        }
        return ' ';
    }
}