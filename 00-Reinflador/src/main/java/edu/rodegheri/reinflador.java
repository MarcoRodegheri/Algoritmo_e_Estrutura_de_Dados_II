package edu.rodegheri;

import java.util.HashMap;
import java.util.Map;

public class reinflador {

    public static void main(String args[]) {

        String numeroArquivo = "00";

        String caminhoArquivo = "/workspaces/Algoritmo_e_Estrutura_de_Dados_II/00-Reinflador/src/main/Entradas/t11_"
                + numeroArquivo + ".txt";

        leitorArquivos leitor = new leitorArquivos();

        Map<Character, String> regras = leitor.ler(caminhoArquivo);

        // 1. Descobre a letra inicial
        char letraInicial = descobrirLetraInicial(regras);
        System.out.println(numeroArquivo + " A letra raiz (inicial) é: " + letraInicial);
        System.out.println("--------------------------------------------------");

        // ==========================================================
        // PARTE 2: CALCULAR O TAMANHO REINFLADO COM UM NOVO MAP
        // ==========================================================

        // Criamos o mapa que vai guardar o "valor" (tamanho) de cada letra.
        // DICA DE OURO: Usamos 'Long' no lugar de 'Integer', porque palavras
        // que se multiplicam assim crescem de forma absurda e podem estourar o limite
        // do Integer!
        Map<Character, Long> tamanhos = new HashMap<>();

        // Chamamos a função mágica que vai calcular tudo
        long tamanhoFinal = calcularTamanhoReinflado(letraInicial, regras, tamanhos);

        System.out.println("Se reinflarmos a letra '" + letraInicial + "', a palavra final terá:");
        System.out.println(tamanhoFinal + " caracteres no total!");

        System.out.println("--------------------------------------------------");
        System.out.println("Dando uma espiada no Map de tamanhos calculados:");
        // Mostra o valor calculado que ficou guardado para algumas letras
        for (Map.Entry<Character, Long> entrada : tamanhos.entrySet()) {
            System.out
                    .println("A letra '" + entrada.getKey() + "' sozinha gera " + entrada.getValue() + " caracteres.");
        }
    }

    public static long calcularTamanhoReinflado(char letraAtual, Map<Character, String> regras,
            Map<Character, Long> tamanhos) {

        // Verifica se o tamanho da letra está guardado
        if (tamanhos.containsKey(letraAtual)) {
            return tamanhos.get(letraAtual);
        }

        // Pega o valor do Caracter
        String substituicao = regras.get(letraAtual);

        // Verifica se a letra é vazia
        if (substituicao == null || substituicao.isEmpty()) {
            tamanhos.put(letraAtual, 1L);
            return 1L;
        }

        long tamanhoTotalDessaLetra = 0;

        // Calcula o tamanaho de cada letra filha, e soma total
        for (int i = 0; i < substituicao.length(); i++) {

            char letraFilha = substituicao.charAt(i);
            tamanhoTotalDessaLetra += calcularTamanhoReinflado(letraFilha, regras, tamanhos);
        }

        // Salva valor no mapa para não calcular denovo
        tamanhos.put(letraAtual, tamanhoTotalDessaLetra);

        return tamanhoTotalDessaLetra;
    }

    public static char descobrirLetraInicial(Map<Character, String> regras) {

        // Pega chave a chave do Map
        for (Character candidata : regras.keySet()) {

            boolean apareceuEmAlgumLugar = false;

            // Pega substituição por substituição do Map, e verifica se a letra candidata
            // está lá
            for (String substituicao : regras.values()) {

                // Se não estiver, retorna -1
                if (substituicao.indexOf(candidata) != -1) {

                    apareceuEmAlgumLugar = true;
                    break;
                }
            }

            // Não apareceu, terá valor Falso e Não é vazia, terá valor Falso -> Inverter
            // para retornar
            if (!apareceuEmAlgumLugar && !regras.get(candidata).isEmpty()) {
                return candidata;
            }
        }
        return ' ';
    }
}