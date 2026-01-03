package br.com.matheus.board.service;

import br.com.matheus.board.model.*;
import br.com.matheus.board.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ColunaRepository colunaRepository;

    public BoardService(BoardRepository boardRepository,
                        ColunaRepository colunaRepository) {
        this.boardRepository = boardRepository;
        this.colunaRepository = colunaRepository;
    }

    // ============================================================
    // 1. Criar Board com colunas
    // ============================================================
    @Transactional
    public Board criarBoard(String nome, List<Coluna> colunas) {

        validarColunas(colunas);

        Board board = new Board();
        board.setNome(nome);
        board = boardRepository.save(board);

        for (Coluna coluna : colunas) {
            coluna.setBoard(board);
            colunaRepository.save(coluna);
        }

        return board;
    }

    // ============================================================
    // 2. Validar regras das colunas
    // ============================================================
    private void validarColunas(List<Coluna> colunas) {

        boolean temInicial = colunas.stream().anyMatch(c -> c.getTipo() == TipoColuna.INICIAL);
        boolean temFinal = colunas.stream().anyMatch(c -> c.getTipo() == TipoColuna.FINAL);
        boolean temCancelamento = colunas.stream().anyMatch(c -> c.getTipo() == TipoColuna.CANCELAMENTO);

        if (!temInicial || !temFinal || !temCancelamento) {
            throw new RuntimeException("Board deve ter colunas: INICIAL, FINAL e CANCELAMENTO");
        }

        long countInicial = colunas.stream().filter(c -> c.getTipo() == TipoColuna.INICIAL).count();
        long countFinal = colunas.stream().filter(c -> c.getTipo() == TipoColuna.FINAL).count();
        long countCancelamento = colunas.stream().filter(c -> c.getTipo() == TipoColuna.CANCELAMENTO).count();

        if (countInicial > 1 || countFinal > 1 || countCancelamento > 1) {
            throw new RuntimeException("Board só pode ter 1 coluna INICIAL, FINAL e CANCELAMENTO");
        }

        // Validar ordem
        colunas.sort((a, b) -> a.getOrdem() - b.getOrdem());

        if (colunas.get(0).getTipo() != TipoColuna.INICIAL) {
            throw new RuntimeException("A primeira coluna deve ser INICIAL");
        }

        if (colunas.get(colunas.size() - 1).getTipo() != TipoColuna.CANCELAMENTO) {
            throw new RuntimeException("A última coluna deve ser CANCELAMENTO");
        }

        if (colunas.get(colunas.size() - 2).getTipo() != TipoColuna.FINAL) {
            throw new RuntimeException("A penúltima coluna deve ser FINAL");
        }
    }

    // ============================================================
    // 3. Listar boards
    // ============================================================
    public List<Board> listarBoards() {
        return boardRepository.findAll();
    }

    // ============================================================
    // 4. Buscar board por ID
    // ============================================================
    public Board buscarPorId(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));
    }

    // ============================================================
    // 5. Excluir board
    // ============================================================
    @Transactional
    public void excluirBoard(Long id) {
        Board board = buscarPorId(id);
        boardRepository.delete(board);
    }
}
