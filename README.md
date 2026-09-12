<div align="center">

# Reinflador Quântico Redundante

### Trabalho 1 da disciplina de Algoritmos e Estrutura de Dados II (PUCRS)

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

</div>

---

## Sobre o projeto

Programa em **Java** desenvolvido como trabalho da disciplina de **Algoritmos e Estrutura de Dados II (PUCRS)**. O problema simula a descompressão de um "reinflador de textos": cada letra do alfabeto possui uma regra de substituição por uma sequência de outras letras, e essas substituições se aplicam recursivamente até não haver mais nada a substituir — como uma gramática de expansão (semelhante a um L-system). Uma entrada de poucos bytes pode gerar um texto final com trilhões de caracteres.

Dado um conjunto de regras, o programa precisa:
1. Descobrir qual letra é a **raiz** da expansão (a letra que nunca é usada como substituição de nenhuma outra, mas possui uma regra própria).
2. Calcular **quantos caracteres** o texto final teria, sem precisar gerar essa string explicitamente — o que seria inviável para entradas grandes (algumas entradas de teste geram mais de 10¹⁶ caracteres).

## Como funciona

- **Leitura das regras** — cada linha do arquivo de entrada é lida no formato `letra substituição`, montando um `HashMap<Character, String>` com a regra de expansão de cada letra. Optou-se por `HashMap` em vez de `TreeMap`: como o hashing de caracteres ASCII minúsculos já preserva a ordem alfabética na prática, o `HashMap` entrega os dados na mesma ordem com menor custo computacional.
- **Descoberta da letra raiz** — percorre todas as letras candidatas e verifica, para cada uma, se ela aparece dentro de alguma substituição das demais. A que nunca aparece (e possui uma regra não vazia) é a raiz.
- **Cálculo do tamanho final** — em vez de expandir a string de verdade, o tamanho de cada letra é calculado por **recursão com memoização**: o tamanho de uma letra é a soma dos tamanhos de cada letra da sua substituição, e cada resultado já calculado é guardado em um mapa para nunca ser recalculado — mesma lógica de percorrer subárvores usada em problemas de árvores binárias.

## Complexidade

| Etapa | Complexidade | Motivo |
|---|---|---|
| Leitura de arquivos | O(N) | percorre o arquivo linha a linha uma única vez |
| Descoberta da letra raiz | O(N²) | para cada letra candidata, verifica sua presença em todas as demais substituições |
| Cálculo do tamanho final | O(N) | cada letra tem seu tamanho calculado uma única vez, graças à memoização |
| Programa completo (por entrada) | O(N²) | dominado pela etapa de descoberta da letra raiz |

*N = número de caracteres nas regras de entrada.*

## Dificuldades encontradas

- O uso inicial de `int` para acumular o tamanho final causava overflow silencioso (valores chegando a `-2.147.483.648`) — resolvido trocando para `long`.
- Definir a recursão do cálculo de tamanho exigiu pensar no problema como uma árvore: resolver completamente uma letra (todos os seus "filhos") antes de somar o resultado e seguir para a próxima.

## Resultados

O programa processa 11 arquivos de teste, cada um com uma letra inicial e um conjunto de regras diferentes. Os resultados variam de dezenas de caracteres até mais de **10¹⁶ caracteres** nas entradas mais densas — evidenciando o crescimento explosivo típico de gramáticas de expansão. A tabela completa por entrada e por letra está disponível no relatório do trabalho.

## Estrutura do projeto

```
00-Reinflador/
├── pom.xml
├── relatorio.pdf                      # Relatório completo do trabalho
└── src/
    ├── main/
    │   ├── java/edu/rodegheri/
    │   │   ├── reinflador.java        # Lógica principal (raiz + cálculo de tamanho)
    │   │   └── leitorArquivos.java    # Parser dos arquivos de entrada
    │   └── Entradas/                  # Casos de teste fornecidos pela disciplina
    │       └── t11_00.txt ... t11_10.txt
    └── test/
        └── java/edu/rodegheri/AppTest.java
```

## Como rodar

```bash
mvn compile
mvn exec:java -Dexec.mainClass="edu.rodegheri.reinflador"
```

## Como rodar os testes

```bash
mvn test
```

## Autores

- Marco Antônio De Carli Rodegheri
- Luiz Confortin
