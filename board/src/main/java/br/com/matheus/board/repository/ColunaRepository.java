package br.com.matheus.board.repository;

import br.com.matheus.board.model.Coluna;
import br.com.matheus.board.model.Board;
import br.com.matheus.board.model.TipoColuna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColunaRepository extends JpaRepository<Coluna, Long> {

    List<Coluna> findByBoardOrderByOrdemAsc(Board board);

    Optional<Coluna> findByBoardAndTipo(Board board, TipoColuna tipo);
}
