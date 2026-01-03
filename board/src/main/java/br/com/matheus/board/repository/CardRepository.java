package br.com.matheus.board.repository;

import br.com.matheus.board.model.Card;
import br.com.matheus.board.model.Board;
import br.com.matheus.board.model.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByBoard(Board board);

    List<Card> findByColunaAtual(Coluna coluna);
}
