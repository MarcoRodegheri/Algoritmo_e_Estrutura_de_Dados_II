package edu.rodegheri;

import java.util.HashMap;
import java.util.Map;

public class reinflador {

    public static void main(String args[]) {

        String nomeArquivo = "/workspaces/Algoritmo_e_Estrutura_de_Dados_II/00-Reinflador/src/main/java/edu/rodegheri/t11_04.txt";

        LeitorDeArquivos leitor = new LeitorDeArquivos();
        Map<Character, String> regras = leitor.ler(nomeArquivo);

        // 1. Descobre a letra inicial
        char letraInicial = descobrirLetraInicial(regras);
        System.out.println("A letra raiz (inicial) é: " + letraInicial);
        System.out.println("--------------------------------------------------");

        // ==========================================================
        // PARTE 2: CALCULAR O TAMANHO REINFLADO COM UM NOVO MAP
        // ==========================================================
        
        // Criamos o mapa que vai guardar o "valor" (tamanho) de cada letra.
        // DICA DE OURO: Usamos 'Long' no lugar de 'Integer', porque palavras 
        // que se multiplicam assim crescem de forma absurda e podem estourar o limite do Integer!
        Map<Character, Long> tamanhos = new HashMap<>();
        
        // Chamamos a função mágica que vai calcular tudo
        long tamanhoFinal = calcularTamanhoReinflado(letraInicial, regras, tamanhos);
        
        System.out.println("Se reinflarmos a letra '" + letraInicial + "', a palavra final terá:");
        System.out.println(tamanhoFinal + " caracteres no total!");
        
        System.out.println("--------------------------------------------------");
        System.out.println("Dando uma espiada no Map de tamanhos calculados:");
        // Mostra o valor calculado que ficou guardado para algumas letras
        for (Map.Entry<Character, Long> entrada : tamanhos.entrySet()) {
            System.out.println("A letra '" + entrada.getKey() + "' sozinha gera " + entrada.getValue() + " caracteres.");
        }
    }

    // ==========================================================
    // MÉTODOS AUXILIARES
    // ==========================================================

    // Novo método recursivo que usa o Map para somar os tamanhos
    public static long calcularTamanhoReinflado(char letraAtual, Map<Character, String> regras, Map<Character, Long> tamanhos) {
        
        // 1. O Pulo do Gato: Se a gente JÁ calculou o tamanho dessa letra antes, 
        // não perdemos tempo. Só pegamos o valor guardado no Map e retornamos!
        if (tamanhos.containsKey(letraAtual)) {
            return tamanhos.get(letraAtual);
        }

        // 2. Pega a regra de substituição dessa letra
        String substituicao = regras.get(letraAtual);

        // 3. Se a regra for vazia (ex: e, z, f), ela é uma letra final que vale 1!
        if (substituicao == null || substituicao.isEmpty()) {
            tamanhos.put(letraAtual, 1L); // Guarda no map que essa vale 1
            return 1L;
        }

        // 4. Se ela tem uma string, vamos somar o tamanho de cada letra filha!
        long tamanhoTotalDessaLetra = 0;
        
        // Quebra a string de substituição e verifica letra por letra
        for (char letraFilha : substituicao.toCharArray()) {
            // A mágica acontece aqui: a função chama ela mesma para descobrir o tamanho do filho
            tamanhoTotalDessaLetra += calcularTamanhoReinflado(letraFilha, regras, tamanhos);
        }

        // 5. Antes de devolver a resposta, SALVA no map para o futuro!
        tamanhos.put(letraAtual, tamanhoTotalDessaLetra);

        return tamanhoTotalDessaLetra;
    }

    // Método para descobrir quem é a letra raiz de tudo (mantido intacto)
    public static char descobrirLetraInicial(Map<Character, String> regras) {
        for (Character candidata : regras.keySet()) {
            boolean apareceuEmAlgumLugar = false;
            for (String substituicao : regras.values()) {
                if (substituicao.indexOf(candidata) != -1) {
                    apareceuEmAlgumLugar = true;
                    break;
                }
            }
            if (!apareceuEmAlgumLugar && !regras.get(candidata).isEmpty()) {
                return candidata;
            }
        }
        return ' '; 
    }
}