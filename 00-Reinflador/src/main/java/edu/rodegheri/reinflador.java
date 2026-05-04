package edu.rodegheri;

import java.util.Map;

public class reinflador{
public static void main (String args[]){

    String nomeArquivo = "t11_01.txt";

    LeitorDeArquivos leitor = new LeitorDeArquivos();
    Map<Character, String> regras = leitor.ler(nomeArquivo);

    char letraInicial = descobrirLetraInicial(regras);

}



// Método para descobrir quem é a letra raiz de tudo
    public static char descobrirLetraInicial(Map<Character, String> regras) {
        
        // 1. Vamos testar cada letra (chave) que existe no nosso mapa
        for (Character candidata : regras.keySet()) {
            
            boolean apareceuEmAlgumLugar = false;
            
            // 2. Olhamos todas as strings de substituição (valores) para ver se ela está lá dentro
            for (String substituicao : regras.values()) {
                
                // Se a string contiver a nossa candidata, ela não é a raiz!
                // O método indexOf retorna -1 se o caractere NÃO for encontrado.
                if (substituicao.indexOf(candidata) != -1) {
                    apareceuEmAlgumLugar = true;
                    break; // Já vimos que ela é gerada por alguém, paramos de testar essa
                }
            }
            
            // 3. Se vasculhamos todas as regras e ela NÃO apareceu em lugar nenhum...
            // (E garantimos que ela não é uma letra vazia/morta)
            if (!apareceuEmAlgumLugar && !regras.get(candidata).isEmpty()) {
                return candidata; // Eureca! Achamos a letra inicial!
            }
        }
        
        // Retorno de segurança caso dê algo muito errado
        return ' '; 
    }
}