import java.util.*;

public class MultiplayerBingo1 {
    private static String[] jogadores;
    private static int numRodadas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Multiplayer Bingo!");
        System.out.println("Opções de comando: ");
        System.out.println("1. Iniciar jogo");
        System.out.println("2. Sair");

        int opcao = scanner.nextInt();

        if (opcao == 1) {
            jogadores = obterJogadores();
            jogarBingo();
        } else {
            System.out.println("Jogo encerrado. Até mais!");
        }
    }

    private static void jogarBingo() {
        Scanner scanner = new Scanner(System.in);

        String[][] cartelas = gerarCartelas(jogadores.length, scanner);
        int[] numerosSorteados = new int[60];
        Arrays.fill(numerosSorteados, -1);
        int rodada = 1;

        while (!bingo(cartelas)) {
            realizarRodada(rodada, cartelas, numerosSorteados, scanner);
            rodada++;
        }

        exibirEstatisticas(rodada - 1, cartelas, numerosSorteados);
    }

    private static void realizarRodada(int rodada, String[][] cartelas, int[] numerosSorteados, Scanner scanner) {
        exibirEstadoAtual(rodada, cartelas);
        int opcao = obterOpcaoSorteio(scanner);

        if (opcao == 1) {
            sortearAutomatico(numerosSorteados, cartelas);
        } else if (opcao == 2) {
            sortearManual(numerosSorteados, scanner);
        } else {
            System.out.println("Comando inválido. Tente novamente.");
            return;
        }

        exibirResultados(rodada, jogadores, cartelas, numerosSorteados);
    }

    private static String[] obterJogadores() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe os nicknames dos jogadores separados por hífen (até 27 jogadores):");
        String jogadoresInput = scanner.nextLine();

        // Limita o número de jogadores a 27
        String[] jogadores = jogadoresInput.split("-", 27);

        // Se o número de jogadores exceder 27, imprime uma mensagem e corta o array
        if (jogadores.length > 27) {
            System.out.println("Número máximo de jogadores atingido (27 jogadores). Os jogadores excedentes serão ignorados.");
            return Arrays.copyOf(jogadores, 27);
        }

        return jogadores;
    }


    private static void sortearAutomatico(int[] numerosSorteados, String[][] cartelas) {
        Random random = new Random();
        int numeroSorteado;

        do {
            numeroSorteado = random.nextInt(60) + 1;
        } while (numerosSorteados[numeroSorteado - 1] != -1);

        numerosSorteados[numeroSorteado - 1] = 1;

        // Atualiza cartelas dos jogadores
        for (String[] cartela : cartelas) {
            for (int i = 0; i < cartela.length; i++) {
                if (cartela[i].equals(String.valueOf(numeroSorteado))) {
                    cartela[i] = "-1";
                    break;
                }
            }
        }
    }

    private static void sortearManual(int[] numerosSorteados, Scanner scanner) {
        int numeroSorteado;

        do {
            System.out.println("Digite o número sorteado:");
            numeroSorteado = scanner.nextInt();
            if (numeroSorteado < 1 || numeroSorteado > 60) {
                System.out.println("Número inválido. Digite novamente.");
            } else if (numerosSorteados[numeroSorteado - 1] != -1) {
                System.out.println("Número já sorteado. Digite novamente.");
            }
        } while (numeroSorteado < 1 || numeroSorteado > 60 || numerosSorteados[numeroSorteado - 1] != -1);

        numerosSorteados[numeroSorteado - 1] = 1;
    }

    private static boolean verificarCondicoesVitoria(String[] cartela) {
        // Verificar linhas
        for (int i = 0; i < 5; i++) {
            if (i + 4 < 5 &&
                    cartela[i].equals("-1") && cartela[i + 1].equals("-1") &&
                    cartela[i + 2].equals("-1") && cartela[i + 3].equals("-1") &&
                    cartela[i + 4].equals("-1")) {
                return true;  // Linha completa
            }
        }

        // Verificar colunas
        for (int i = 0; i < 5; i++) {
            if (i + 4 * 5 < 25 &&
                    cartela[i].equals("-1") && cartela[i + 5].equals("-1") &&
                    cartela[i + 10].equals("-1") && cartela[i + 15].equals("-1") &&
                    cartela[i + 20].equals("-1")) {
                return true;  // Coluna completa
            }
        }

        // Verificar diagonais
        if (cartela[0].equals("-1") && cartela[6].equals("-1") &&
                cartela[12].equals("-1") && cartela[18].equals("-1") &&
                cartela[24].equals("-1")) {
            return true;  // Diagonal principal completa
        }

        if (cartela[4].equals("-1") && cartela[8].equals("-1") &&
                cartela[12].equals("-1") && cartela[16].equals("-1") &&
                cartela[20].equals("-1")) {
            return true;  // Diagonal secundária completa
        }

        // Verificar cartela cheia
        for (String numero : cartela) {
            if (!numero.equals("-1")) {
                return false;  // Cartela não está completa
            }
        }

        return true;  // Todas as condições atendidas
    }

    private static void exibirResultados(int rodada, String[] jogadores, String[][] cartelas, int[] numerosSorteados) {
        System.out.println("Resultados da Rodada " + rodada + ":");
        exibirCartelas(cartelas);

        for (int i = 0; i < jogadores.length; i++) {
            String[] cartela = cartelas[i];
            System.out.println("Jogador " + (i + 1) + " - " + jogadores[i] + ":");
            System.out.println("Bingo: " + (Cartela.verificarCartela(cartelas[i], numerosSorteados) ? "Sim" : "Não"));

            // Novo código para rastrear os números acertados
            System.out.println("Números Acertados: " + obterNumerosAcertados(cartela));

            System.out.println();
        }

        // Exibe ranking dos top 3 mais bem

        exibirRanking(cartelas, numerosSorteados);

        /// Solicitação para pressionar a tecla para continuar
        System.out.println("Pressione Enter para continuar ou X para abortar o jogo:");
        Scanner scanner = new Scanner(System.in);
        String continuar = scanner.nextLine().toUpperCase();  // Converte para maiúsculas

        while (!continuar.equals("X")) {
            System.out.println("Comando inválido. Digite X para sair.");
            continuar = scanner.nextLine().toUpperCase();
        }

        System.out.println("Jogo abortado. Até mais!");
        System.exit(0);
    }

    private static String obterNumerosAcertados(String[] cartela) {
        StringBuilder numerosAcertados = new StringBuilder();
        for (String numero : cartela) {
            if (numero.equals("-1")) {
                numerosAcertados.append("1 ");
            } else {
                numerosAcertados.append("0 ");
            }
        }
        return numerosAcertados.toString().trim();
    }

    private static void exibirEstatisticas(int rodadas, String[][] cartelas, int[] numerosSorteados) {
        System.out.println("Fim do jogo! Estatísticas finais:");

        exibirCartelas(cartelas);

        System.out.println("Número total de rodadas: " + rodadas);

        System.out.println("Cartela premiada com números ordenados e nome do vencedor:");
        String vencedor = obterVencedor(cartelas, numerosSorteados);
        System.out.println("Vencedor: " + vencedor);

        System.out.println("Quantidade e números sorteados em ordem:");
        exibirNumerosSorteados(numerosSorteados);
    }

    private static void exibirNumerosSorteados(int[] numerosSorteados) {
        System.out.println("Números sorteados em ordem:");
        for (int i = 0; i < numerosSorteados.length; i++) {
            if (numerosSorteados[i] == 1) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
    }

    private static String obterVencedor(String[][] cartelas, int[] numerosSorteados) {
        for (int i = 0; i < cartelas.length; i++) {
            if (verificarCondicoesVitoria(cartelas[i])) {
                return jogadores[i];
            }
        }
        return "Nenhum vencedor";
    }

    private static void exibirRanking(String[][] cartelas, int[] numerosSorteados) {
        System.out.println("Exibindo Ranking dos Top 3:");
        Map<String, Integer> pontuacoes = new HashMap<>();

        for (int i = 0; i < jogadores.length; i++) {
            int pontuacao = Cartela.calcularPontuacao(cartelas[i], numerosSorteados);
            pontuacoes.put(jogadores[i], pontuacao);
        }

        pontuacoes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " pontos"));
    }

    private static void exibirEstadoAtual(int rodada, String[][] cartelas) {
        System.out.println("Rodada " + rodada);
        exibirCartelas(cartelas);

        System.out.println("Opções de comando: ");
        System.out.println("1. Sorteio automático");
        System.out.println("2. Sorteio manual");
    }

    private static int obterOpcaoSorteio(Scanner scanner) {
        return scanner.nextInt();
    }

    private static String[][] gerarCartelas(int numJogadores, Scanner scanner) {
        String[][] cartelas = new String[numJogadores][5];

        for (int i = 0; i < numJogadores; i++) {
            gerarCartelaParaJogador(i, scanner, cartelas);
        }
        return cartelas;
    }


    private static void gerarCartelaParaJogador(int indiceJogador, Scanner scanner, String[][] cartelas) {
        System.out.println("Gerar cartela para " + jogadores[indiceJogador]);
        System.out.println("Opções de comando: ");
        System.out.println("1. Cartela automática");
        System.out.println("2. Cartela manual");

        int opcao = scanner.nextInt();

        if (opcao == 1) {
            cartelas[indiceJogador] = Cartela.gerarCartelaAutomatica();
        } else if (opcao == 2) {
            cartelas[indiceJogador] = Cartela.gerarCartelaManual(jogadores[indiceJogador], scanner);
        } else {
            System.out.println("Comando inválido. Tente novamente.");
            gerarCartelaParaJogador(indiceJogador, scanner, cartelas);
        }
    }

    private static boolean bingo(String[][] cartelas) {
        for (String[] cartela : cartelas) {
            if (verificarCondicoesVitoria(cartela)) {
                return true;
            }
        }
        return false;
    }

    private static void exibirCartelas(String[][] cartelas) {
        for (int i = 0; i < cartelas.length; i++) {
            System.out.println("Cartela do jogador " + (i + 1) + ":");
            for (int j = 0; j < 5; j++) {
                System.out.print(cartelas[i][j] + " ");
            }
            System.out.println();
        }
    }
}
