package br.com.matheus.board.ui;

import br.com.matheus.board.model.*;
import br.com.matheus.board.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApplicationRunner implements CommandLineRunner {

    private final BoardService boardService;
    private final ColunaService colunaService;
    private final CardService cardService;
    private final RelatorioService relatorioService;

    public ConsoleApplicationRunner(BoardService boardService,
                                    ColunaService colunaService,
                                    CardService cardService,
                                    RelatorioService relatorioService) {
        this.boardService = boardService;
        this.colunaService = colunaService;
        this.cardService = cardService;
        this.relatorioService = relatorioService;
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Criar novo board");
            System.out.println("2 - Selecionar board");
            System.out.println("3 - Excluir board");
            System.out.println("4 - Listar boards");
            System.out.println("5 - Sair");
            System.out.print("Escolha: ");

            int opcao = lerInt(sc);

            switch (opcao) {
                case 1 -> criarBoard(sc);
                case 2 -> selecionarBoard(sc);
                case 3 -> excluirBoard(sc);
                case 4 -> listarBoards(sc);
                case 5 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // ============================================================
    // Funções utilitárias
    // ============================================================
    private int lerInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Valor inválido. Digite um número: ");
            }
        }
    }

    private long lerLong(Scanner sc) {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Valor inválido. Digite um número: ");
            }
        }
    }

    // ============================================================
    // 1. Criar Board
    // ============================================================
    private void criarBoard(Scanner sc) {
        try {
            System.out.print("Nome do board: ");
            String nome = sc.nextLine();

            System.out.println("Criando colunas...");

            Coluna inicial = new Coluna();
            inicial.setNome("Inicial");
            inicial.setOrdem(1);
            inicial.setTipo(TipoColuna.INICIAL);

            System.out.print("Quantas etapas extras deseja criar? ");
            int qtdPendentes = lerInt(sc);

            List<Coluna> colunas = new java.util.ArrayList<>();
            colunas.add(inicial);

            for (int i = 0; i < qtdPendentes; i++) {
                System.out.print("Nome da etapa " + (i + 1) + ": ");
                String nomeCol = sc.nextLine();

                Coluna pend = new Coluna();
                pend.setNome(nomeCol);
                pend.setOrdem(i + 2);
                pend.setTipo(TipoColuna.PENDENTE);

                colunas.add(pend);
            }

            Coluna finalCol = new Coluna();
            finalCol.setNome("Final");
            finalCol.setOrdem(colunas.size() + 1);
            finalCol.setTipo(TipoColuna.FINAL);
            colunas.add(finalCol);

            Coluna cancel = new Coluna();
            cancel.setNome("Cancelado");
            cancel.setOrdem(colunas.size() + 1);
            cancel.setTipo(TipoColuna.CANCELAMENTO);
            colunas.add(cancel);

            boardService.criarBoard(nome, colunas);

            System.out.println("Board criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar board: " + e.getMessage());
        }
    }

    // ============================================================
    // 2. Selecionar Board
    // ============================================================
    private void selecionarBoard(Scanner sc) {
        try {
            List<Board> boards = boardService.listarBoards();

            if (boards.isEmpty()) {
                System.out.println("Nenhum board cadastrado.");
                return;
            }

            System.out.println("\n=== BOARDS DISPONÍVEIS ===");
            boards.forEach(b -> System.out.println(b.getId() + " - " + b.getNome()));

            System.out.print("Digite o ID do board: ");
            long id = lerLong(sc);

            Board board = boardService.buscarPorId(id);

            menuBoard(sc, board);

        } catch (Exception e) {
            System.out.println("Erro ao selecionar board: " + e.getMessage());
        }
    }

    // ============================================================
    // 3. Excluir Board
    // ============================================================
    private void excluirBoard(Scanner sc) {
        try {
            System.out.print("ID do board para excluir: ");
            long id = lerLong(sc);

            boardService.excluirBoard(id);
            System.out.println("Board excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir board: " + e.getMessage());
        }
    }

    // ============================================================
    // 4. Listar Boards
    // ============================================================
    private void listarBoards(Scanner sc) {
        try {
            List<Board> boards = boardService.listarBoards();

            if (boards.isEmpty()) {
                System.out.println("Nenhum board cadastrado.");
                return;
            }

            System.out.println("\n=== LISTA DE BOARDS ===");
            for (Board b : boards) {
                System.out.println("ID: " + b.getId());
                System.out.println("Nome: " + b.getNome());
                System.out.println("=====================");
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar boards: " + e.getMessage());
        }
    }

    // ============================================================
    // MENU DO BOARD
    // ============================================================
    private void menuBoard(Scanner sc, Board board) {

        while (true) {
            System.out.println("=====================");
        	System.out.println("\n=== BOARD SELECIONADO: " + board.getNome() + " ===");
            System.out.println("1 - Criar card");
            System.out.println("2 - Mover card");
            System.out.println("3 - Cancelar card");
            System.out.println("4 - Bloquear card");
            System.out.println("5 - Desbloquear card");
            System.out.println("6 - Relatório de tempo por coluna");
            System.out.println("7 - Relatório de bloqueios");
            System.out.println("8 - Listar cards");
            System.out.println("9 - Voltar");
            System.out.print("Escolha: ");
           

            int opcao = lerInt(sc);

            switch (opcao) {
                case 1 -> criarCard(sc, board);
                case 2 -> moverCard(sc);
                case 3 -> cancelarCard(sc);
                case 4 -> bloquearCard(sc);
                case 5 -> desbloquearCard(sc);
                case 6 -> relatorioTempo(sc);
                case 7 -> relatorioBloqueios(sc);
                case 8 -> listarCards(sc, board);
                case 9 -> { return; }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // ============================================================
    // AÇÕES DO BOARD
    // ============================================================
    private void criarCard(Scanner sc, Board board) {
        try {
            System.out.print("Título: ");
            String titulo = sc.nextLine();

            System.out.print("Descrição: ");
            String desc = sc.nextLine();

            cardService.criarCard(board, titulo, desc);

            System.out.println("Card criado!");
        } catch (Exception e) {
            System.out.println("Erro ao criar card: " + e.getMessage());
        }
    }

    private void moverCard(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            cardService.moverParaProximaColuna(id);

            System.out.println("Card movido!");
        } catch (Exception e) {
            System.out.println("Erro ao mover card: " + e.getMessage());
        }
    }

    private void cancelarCard(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            cardService.cancelarCard(id);

            System.out.println("Card cancelado!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar card: " + e.getMessage());
        }
    }

    private void bloquearCard(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            System.out.print("Motivo do bloqueio: ");
            String motivo = sc.nextLine();

            cardService.bloquearCard(id, motivo);

            System.out.println("Card bloqueado!");
        } catch (Exception e) {
            System.out.println("Erro ao bloquear card: " + e.getMessage());
        }
    }

    private void desbloquearCard(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            System.out.print("Motivo do desbloqueio: ");
            String motivo = sc.nextLine();

            cardService.desbloquearCard(id, motivo);

            System.out.println("Card desbloqueado!");
        } catch (Exception e) {
            System.out.println("Erro ao desbloquear card: " + e.getMessage());
        }
    }

    
    
    
    private void relatorioTempo(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            Card card = cardService.buscarPorId(id);

            var rel = relatorioService.tempoPorColuna(card);

            System.out.println("\n=== TEMPO POR COLUNA ===");
            rel.forEach((col, duracaoFormatada) ->
                    System.out.println(col + ": " + duracaoFormatada)
            );
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    
    
    
    

    private void relatorioBloqueios(Scanner sc) {
        try {
            System.out.print("ID do card: ");
            long id = lerLong(sc);

            Card card = cardService.buscarPorId(id);

            var rel = relatorioService.relatorioBloqueios(card);

            System.out.println("\n=== RELATÓRIO DE BLOQUEIOS ===");

            if (rel.isEmpty()) {
                System.out.println("Nenhum bloqueio registrado para este card.");
                return;
            }

            rel.forEach(item -> {
                System.out.println("Bloqueado em:      " + item.get("data_bloqueio"));
                System.out.println("Motivo bloqueio:   " + item.get("motivo_bloqueio"));
                System.out.println("Desbloqueado em:   " + item.get("data_desbloqueio"));
                System.out.println("Motivo desbloqueio:" + item.get("motivo_desbloqueio"));
                System.out.println("Tempo bloqueado:   " + item.get("tempo_bloqueado"));
                System.out.println("===============================");
            });

        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    
    
    
    
    

    private void listarCards(Scanner sc, Board board) {
        try {
            List<Card> cards = cardService.listarPorBoard(board);

            if (cards.isEmpty()) {
                System.out.println("Nenhum card encontrado neste board.");
                return;
            }

            System.out.println("\n=== CARDS DO BOARD ===");
            for (Card c : cards) {
                System.out.println("ID: " + c.getId());
                System.out.println("Título: " + c.getTitulo());
                System.out.println("Descrição: " + c.getDescricao());
                System.out.println("Coluna atual: " + c.getColunaAtual().getNome());
                System.out.println("Bloqueado: " + (c.isBloqueado() ? "Sim" : "Não"));
                System.out.println("=====================");
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar cards: " + e.getMessage());
        }
    }
}
