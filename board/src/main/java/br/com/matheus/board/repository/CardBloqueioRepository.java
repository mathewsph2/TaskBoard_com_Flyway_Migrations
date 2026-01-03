package br.com.matheus.board.repository;

import br.com.matheus.board.model.Card;
import br.com.matheus.board.model.CardBloqueio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardBloqueioRepository extends JpaRepository<CardBloqueio, Long> {

    List<CardBloqueio> findByCardOrderByDataBloqueioAsc(Card card);
}
