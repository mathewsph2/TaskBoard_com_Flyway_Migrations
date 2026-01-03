package br.com.matheus.board.service;

import br.com.matheus.board.model.*;
import br.com.matheus.board.repository.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class RelatorioService {

    private final CardMovimentacaoRepository movimentacaoRepository;
    private final CardBloqueioRepository bloqueioRepository;

    public RelatorioService(CardMovimentacaoRepository movimentacaoRepository,
                            CardBloqueioRepository bloqueioRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    // ============================================================
    // 1. Relatório de tempo por coluna
    // ============================================================
    public Map<String, Duration> tempoPorColuna(Card card) {

        List<CardMovimentacao> movs =
                movimentacaoRepository.findByCardOrderByDataEntradaAsc(card);

        Map<String, Duration> resultado = new LinkedHashMap<>();

        for (CardMovimentacao mov : movs) {
            if (mov.getDataSaida() != null) {
                Duration dur = Duration.between(mov.getDataEntrada(), mov.getDataSaida());
                resultado.put(mov.getColuna().getNome(), dur);
            }
        }

        return resultado;
    }

    // ============================================================
    // 2. Relatório de bloqueios
    // ============================================================
    public List<Map<String, Object>> relatorioBloqueios(Card card) {

        List<CardBloqueio> bloqueios =
                bloqueioRepository.findByCardOrderByDataBloqueioAsc(card);

        List<Map<String, Object>> resultado = new ArrayList<>();

        for (CardBloqueio b : bloqueios) {

            Map<String, Object> item = new HashMap<>();
            item.put("motivo_bloqueio", b.getMotivoBloqueio());
            item.put("data_bloqueio", b.getDataBloqueio());
            item.put("motivo_desbloqueio", b.getMotivoDesbloqueio());
            item.put("data_desbloqueio", b.getDataDesbloqueio());

            if (b.getDataDesbloqueio() != null) {
                Duration dur = Duration.between(b.getDataBloqueio(), b.getDataDesbloqueio());
                item.put("tempo_bloqueado", dur);
            }

            resultado.add(item);
        }

        return resultado;
    }
}
