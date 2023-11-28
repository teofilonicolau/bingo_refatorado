import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Cartela {
    // Restante do código...

    public static String[] gerarCartelaManual(String jogador, Scanner scanner) {
        String[] cartela = new String[5];

        Set<Integer> numerosGerados = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            System.out.println("Digite o " + (i + 1) + "º número para " + jogador + ":");
            int numeroDigitado;

            do {
                numeroDigitado = scanner.nextInt();

                // Verifica se o número está no intervalo permitido (1 a 60)
                if (numeroDigitado < 1 || numeroDigitado > 60) {
                    System.out.println("Número inválido. Digite novamente.");
                } else if (!numerosGerados.add(numeroDigitado)) {
                    System.out.println("Número já escolhido. Digite novamente.");
                } else {
                    cartela[i] = String.valueOf(numeroDigitado);
                }
            } while (cartela[i] == null);
        }

        return cartela;
    }

    // Método para gerar automaticamente uma cartela
    public static String[] gerarCartelaAutomatica() {
        String[] cartela = new String[5];
        Set<Integer> numerosGerados = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int numeroAleatorio;

            do {
                numeroAleatorio = random.nextInt(60) + 1;
            } while (!numerosGerados.add(numeroAleatorio));

            cartela[i] = String.valueOf(numeroAleatorio);
        }

        return cartela;
    }

    public static boolean verificarCartela(String[] cartela, int[] numerosSorteados) {
        for (String numero : cartela) {
            if (!numeroSorteado(Integer.parseInt(numero), numerosSorteados)) {
                return false;
            }
        }
        return true;
    }

    private static boolean numeroSorteado(int numero, int[] numerosSorteados) {
        if (numero < 1 || numero > 60) {
            return false;  // Números fora do intervalo não são sorteados
        }
        // Adicione aqui a lógica restante do método se necessário

        // Adicione um retorno padrão no final do método, caso não atenda às condições acima
        return true;  // ou false, dependendo da lógica que você deseja
    }

    public static int calcularPontuacao(String[] cartela, int[] numerosSorteados) {
        int pontuacao = 0;

        for (String numero : cartela) {
            if (numeroSorteado(Integer.parseInt(numero), numerosSorteados)) {
                pontuacao++;
            }
        }

        return pontuacao;
    }
}
