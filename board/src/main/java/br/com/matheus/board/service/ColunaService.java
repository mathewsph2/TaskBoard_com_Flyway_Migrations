package br.com.matheus.board.service;

import br.com.matheus.board.model.*;
import br.com.matheus.board.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColunaService {

    private final ColunaRepository colunaRepository;
    private final BoardRepository boardRepository;

    public ColunaService(ColunaRepository colunaRepository,
                         BoardRepository boardRepository) {
        this.colunaRepository = colunaRepository;
        this.boardRepository = boardRepository;
    }

    // ============================================================
    // 1. Criar coluna
    // ============================================================
    public Coluna criarColuna(Long boardId, String nome, int ordem, TipoColuna tipo) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));

        Coluna coluna = new Coluna();
        coluna.setBoard(board);
        coluna.setNome(nome);
        coluna.setOrdem(ordem);
        coluna.setTipo(tipo);

        return colunaRepository.save(coluna);
    }

    // ============================================================
    // 2. Listar colunas do board
    // ============================================================
    public List<Coluna> listarPorBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));

        return colunaRepository.findByBoardOrderByOrdemAsc(board);
    }

    // ============================================================
    // 3. Buscar coluna por ID
    // ============================================================
    public Coluna buscarPorId(Long id) {
        return colunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coluna não encontrada"));
    }
}
