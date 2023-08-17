import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Guerra {

    private static final int NUM_JOGADORES = 2;
    private static final int TAMANHO_BARALHO = 2;

    private static List<Carta> baralho;
    private static List<Carta>[] maosJogadores;
    private static Carta[] cartasJogadas;
    private static int[] placar;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Jogo da Guerra de Cartas!");

        inicializarJogo();

        while (!jogoTerminou()) {
            System.out.println("Nova Rodada!");
            mostrarPlacar();

            while (!jogoTerminou()) {
                // Processar jogadas dos jogadores
                processarJogadas(scanner);

                // Comparar cartas jogadas e atualizar placar
                atualizarPlacar();

                if (!jogoTerminou()) {
                    System.out.println("Pressione Enter para continuar...");
                    scanner.nextLine();
                }
            }
        }

        // Determinar e mostrar o vencedor
        int vencedor = determinarVencedor();
        if (vencedor == -1) {
            System.out.println("O jogo terminou em empate!");
        } else {
            System.out.println("Jogador " + (vencedor + 1) + " venceu o jogo com " + placar[vencedor] + " pontos!");
        }
    }

    // Inicializa o jogo
    private static void inicializarJogo() {
        baralho = criarBaralho();
        Collections.shuffle(baralho);

        maosJogadores = new List[NUM_JOGADORES];
        for (int i = 0; i < NUM_JOGADORES; i++) {
            maosJogadores[i] = new ArrayList<>();
        }

        for (int i = 0; i < TAMANHO_BARALHO; i++) {
            maosJogadores[i % NUM_JOGADORES].add(baralho.get(i));
        }

        placar = new int[NUM_JOGADORES];
        cartasJogadas = new Carta[NUM_JOGADORES];
    }

    // Processa as jogadas dos jogadores
    private static void processarJogadas(Scanner scanner) {
        for (int i = 0; i < NUM_JOGADORES; i++) {
            if (maosJogadores[i].isEmpty()) {
                continue; // Pular jogador sem cartas
            }

            System.out.println("\nJogador " + (i + 1) + ", é a sua vez! Suas cartas:");
            mostrarCartas(maosJogadores[i]);

            System.out.print("Escolha o número da carta a jogar: ");
            int escolha = scanner.nextInt();
            cartasJogadas[i] = maosJogadores[i].get(escolha - 1);
            maosJogadores[i].remove(escolha - 1);

            System.out.println("Você jogou: " + cartasJogadas[i]);

            scanner.nextLine(); // Limpar o buffer
        }
    }

    // Atualiza o placar após comparar as cartas jogadas
    private static void atualizarPlacar() {
        Carta cartaJogador1 = cartasJogadas[0];
        Carta cartaJogador2 = cartasJogadas[1];

        System.out.println("Jogador 1: " + cartaJogador1);
        System.out.println("Jogador 2: " + cartaJogador2);

        int pesoCartaJogador1 = cartaJogador1.getValor();
        int pesoCartaJogador2 = cartaJogador2.getValor();

        if (pesoCartaJogador1 > pesoCartaJogador2) {
            placar[0]++;
            System.out.println("Jogador 1 venceu a rodada!");
        } else if (pesoCartaJogador1 < pesoCartaJogador2) {
            placar[1]++;
            System.out.println("Jogador 2 venceu a rodada!");
        } else {
            System.out.println("Empate! Vai ter uma Guerra!");
            realizarGuerra();
        }
    }

    // Implementa a lógica da Guerra
    private static void realizarGuerra() {
        System.out.println("Jogadores em Guerra!");

        // Jogadores jogam uma rodada adicional
        Scanner scanner = new Scanner(System.in);
        processarJogadas(scanner);

        // Comparar cartas jogadas e determinar vencedor da Guerra
        Carta cartaJogador1 = cartasJogadas[0];
        Carta cartaJogador2 = cartasJogadas[1];

        System.out.println("Jogador 1: " + cartaJogador1);
        System.out.println("Jogador 2: " + cartaJogador2);

        int pesoCartaJogador1 = cartaJogador1.getValor();
        int pesoCartaJogador2 = cartaJogador2.getValor();

        if (pesoCartaJogador1 > pesoCartaJogador2) {
            System.out.println("Jogador 1 venceu a Guerra!");
            placar[0] += 2; // Jogador 1 ganha 1 ponto

        } else if (pesoCartaJogador1 < pesoCartaJogador2) {
            System.out.println("Jogador 2 venceu a Guerra!");
            placar[1] += 2; // Jogador 2 ganha 1 ponto

        } else {
            System.out.println("Empate na Guerra! Nenhuma rodada ganha.");
        }
    }

    // Verifica se o jogo terminou
    private static boolean jogoTerminou() {
        for (int i = 0; i < NUM_JOGADORES; i++) {
            if (maosJogadores[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Determina o vencedor do jogo
    private static int determinarVencedor() {
        int rodadasJogador1 = placar[0];
        int rodadasJogador2 = placar[1];

        if (rodadasJogador1 > rodadasJogador2) {
            return 0;
        } else if (rodadasJogador1 < rodadasJogador2) {
            return 1;
        } else {
            return -1; // Empate
        }
    }

    // Cria um novo baralho de cartas
    private static List<Carta> criarBaralho() {
        List<Carta> baralho = new ArrayList<>();
        for (Naipe naipe : Naipe.values()) {
            for (int valor = 2; valor <= 14; valor++) {
                baralho.add(new Carta(valor, naipe));
            }
        }
        return baralho;
    }

    // Mostra as cartas na mão do jogador
    private static void mostrarCartas(List<Carta> cartas) {
        for (int i = 0; i < cartas.size(); i++) {
            System.out.println((i + 1) + ". " + cartas.get(i));
        }
    }

    // Mostra o placar atual
    private static void mostrarPlacar() {
        System.out.println("Placar: Jogador 1 (" + placar[0] + ") - Jogador 2 (" + placar[1] + ")");
    }
}

class Carta implements Comparable<Carta> {

    private int valor;
    private Naipe naipe;

    public Carta(int valor, Naipe naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public int compareTo(Carta outraCarta) {
        return Integer.compare(outraCarta.valor, this.valor); // Compara do maior para o menor
    }

    @Override
    public String toString() {
        String nomeValor = String.valueOf(valor);
        if (valor == 11)
            nomeValor = "J";
        else if (valor == 12)
            nomeValor = "Q";
        else if (valor == 13)
            nomeValor = "K";
        else if (valor == 14)
            nomeValor = "A";

        return nomeValor + " de " + naipe;
    }
}

enum Naipe {
    PAUS, COPAS, ESPADAS, OUROS
}
