package br.com.matheus.board.service;

import br.com.matheus.board.model.*;
import br.com.matheus.board.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ColunaRepository colunaRepository;
    private final CardMovimentacaoRepository movimentacaoRepository;
    private final CardBloqueioRepository bloqueioRepository;

    public CardService(CardRepository cardRepository,
                       ColunaRepository colunaRepository,
                       CardMovimentacaoRepository movimentacaoRepository,
                       CardBloqueioRepository bloqueioRepository) {
        this.cardRepository = cardRepository;
        this.colunaRepository = colunaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    // ============================================================
    // 1. Criar Card
    // ============================================================
    @Transactional
    public Card criarCard(Board board, String titulo, String descricao) {

        Coluna colunaInicial = colunaRepository
                .findByBoardAndTipo(board, TipoColuna.INICIAL)
                .orElseThrow(() -> new RuntimeException("Coluna inicial não encontrada"));

        Card card = new Card();
        card.setBoard(board);
        card.setTitulo(titulo);
        card.setDescricao(descricao);
        card.setColunaAtual(colunaInicial);
        card.setBloqueado(false);

        card = cardRepository.save(card);

        // Registrar movimentação inicial
        CardMovimentacao mov = new CardMovimentacao();
        mov.setCard(card);
        mov.setColuna(colunaInicial);
        mov.setDataEntrada(LocalDateTime.now());
        movimentacaoRepository.save(mov);

        return card;
    }

    // ============================================================
    // 2. Mover Card para próxima coluna
    // ============================================================
    @Transactional
    public Card moverParaProximaColuna(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        if (card.isBloqueado()) {
            throw new RuntimeException("Card está bloqueado e não pode ser movido");
        }

        // ❗ Impedir mover se estiver em FINAL ou CANCELADO
        TipoColuna tipoAtual = card.getColunaAtual().getTipo();
        if (tipoAtual == TipoColuna.FINAL || tipoAtual == TipoColuna.CANCELAMENTO) {
            throw new RuntimeException("Card já está em uma coluna terminal e não pode ser movido");
        }

        List<Coluna> colunas = colunaRepository.findByBoardOrderByOrdemAsc(card.getBoard());

        int indexAtual = colunas.indexOf(card.getColunaAtual());

        if (indexAtual == -1 || indexAtual == colunas.size() - 1) {
            throw new RuntimeException("Card já está na última coluna");
        }

        Coluna proxima = colunas.get(indexAtual + 1);

        // Fechar movimentação atual
        movimentacaoRepository.findByCardAndColunaOrderByDataEntradaAsc(card, card.getColunaAtual())
                .stream()
                .filter(m -> m.getDataSaida() == null)
                .findFirst()
                .ifPresent(m -> {
                    m.setDataSaida(LocalDateTime.now());
                    movimentacaoRepository.save(m);
                });

        // Registrar entrada na nova coluna
        CardMovimentacao mov = new CardMovimentacao();
        mov.setCard(card);
        mov.setColuna(proxima);
        mov.setDataEntrada(LocalDateTime.now());
        movimentacaoRepository.save(mov);

        card.setColunaAtual(proxima);
        return cardRepository.save(card);
    }

    // ============================================================
    // 3. Cancelar Card
    // ============================================================
    @Transactional
    public Card cancelarCard(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        if (card.getColunaAtual().getTipo() == TipoColuna.FINAL) {
            throw new RuntimeException("Card finalizado não pode ser cancelado");
        }

        Coluna colunaCancelamento = colunaRepository
                .findByBoardAndTipo(card.getBoard(), TipoColuna.CANCELAMENTO)
                .orElseThrow(() -> new RuntimeException("Coluna de cancelamento não encontrada"));

        // Fechar movimentação atual
        movimentacaoRepository.findByCardAndColunaOrderByDataEntradaAsc(card, card.getColunaAtual())
                .stream()
                .filter(m -> m.getDataSaida() == null)
                .findFirst()
                .ifPresent(m -> {
                    m.setDataSaida(LocalDateTime.now());
                    movimentacaoRepository.save(m);
                });

        // Registrar movimentação de cancelamento
        CardMovimentacao mov = new CardMovimentacao();
        mov.setCard(card);
        mov.setColuna(colunaCancelamento);
        mov.setDataEntrada(LocalDateTime.now());
        movimentacaoRepository.save(mov);

        card.setColunaAtual(colunaCancelamento);
        return cardRepository.save(card);
    }

    // ============================================================
    // 4. Bloquear Card
    // ============================================================
    @Transactional
    public Card bloquearCard(Long cardId, String motivo) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        if (card.isBloqueado()) {
            throw new RuntimeException("Card já está bloqueado");
        }

        card.setBloqueado(true);
        cardRepository.save(card);

        CardBloqueio bloqueio = new CardBloqueio();
        bloqueio.setCard(card);
        bloqueio.setMotivoBloqueio(motivo);
        bloqueio.setDataBloqueio(LocalDateTime.now());

        bloqueioRepository.save(bloqueio);

        return card;
    }

    // ============================================================
    // 5. Desbloquear Card
    // ============================================================
    @Transactional
    public Card desbloquearCard(Long cardId, String motivo) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        if (!card.isBloqueado()) {
            throw new RuntimeException("Card não está bloqueado");
        }

        card.setBloqueado(false);
        cardRepository.save(card);

        // Pega o último bloqueio aberto
        CardBloqueio bloqueio = bloqueioRepository.findByCardOrderByDataBloqueioAsc(card)
                .stream()
                .filter(b -> b.getDataDesbloqueio() == null)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("Nenhum bloqueio aberto encontrado"));

        bloqueio.setDataDesbloqueio(LocalDateTime.now());
        bloqueio.setMotivoDesbloqueio(motivo);

        bloqueioRepository.save(bloqueio);

        return card;
    }

    // ============================================================
    // 6. Buscar Card por ID
    // ============================================================
    public Card buscarPorId(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));
    }

    // ============================================================
    // 7. Listar cards por board
    // ============================================================
    public List<Card> listarPorBoard(Board board) {
        return cardRepository.findByBoard(board);
    }
}
