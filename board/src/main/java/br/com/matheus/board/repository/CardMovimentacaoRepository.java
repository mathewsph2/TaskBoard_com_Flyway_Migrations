package br.com.matheus.board.repository;

import br.com.matheus.board.model.Card;
import br.com.matheus.board.model.CardMovimentacao;
import br.com.matheus.board.model.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardMovimentacaoRepository extends JpaRepository<CardMovimentacao, Long> {

    List<CardMovimentacao> findByCardOrderByDataEntradaAsc(Card card);

    List<CardMovimentacao> findByCardAndColunaOrderByDataEntradaAsc(Card card, Coluna coluna);
}
