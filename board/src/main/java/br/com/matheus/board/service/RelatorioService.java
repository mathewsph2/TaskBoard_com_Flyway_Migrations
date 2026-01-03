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
    // Função utilitária para formatar Duration
    // ============================================================
    private String formatarDuration(Duration d) {
        long horas = d.toHours();
        long minutos = d.minusHours(horas).toMinutes();
        long segundos = d.minusHours(horas).minusMinutes(minutos).getSeconds();

        StringBuilder sb = new StringBuilder();

        if (horas > 0) sb.append(horas).append("h ");
        if (minutos > 0) sb.append(minutos).append("m ");
        if (segundos > 0 || sb.isEmpty()) sb.append(segundos).append("s");

        return sb.toString().trim();
    }

    // ============================================================
    // 1. Relatório de tempo por coluna (versão amigável)
    // ============================================================
    public Map<String, String> tempoPorColuna(Card card) {

        List<CardMovimentacao> movs =
                movimentacaoRepository.findByCardOrderByDataEntradaAsc(card);

        Map<String, String> resultado = new LinkedHashMap<>();

        for (CardMovimentacao mov : movs) {
            if (mov.getDataSaida() != null) {
                Duration dur = Duration.between(mov.getDataEntrada(), mov.getDataSaida());
                resultado.put(mov.getColuna().getNome(), formatarDuration(dur));
            }
        }

        return resultado;
    }

    // ============================================================
    // 2. Relatório de bloqueios (versão amigável)
    // ============================================================
    public List<Map<String, Object>> relatorioBloqueios(Card card) {

        List<CardBloqueio> bloqueios =
                bloqueioRepository.findByCardOrderByDataBloqueioAsc(card);

        List<Map<String, Object>> resultado = new ArrayList<>();

        for (CardBloqueio b : bloqueios) {

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("motivo_bloqueio", b.getMotivoBloqueio());
            item.put("data_bloqueio", b.getDataBloqueio());
            item.put("motivo_desbloqueio", b.getMotivoDesbloqueio());
            item.put("data_desbloqueio", b.getDataDesbloqueio());

            if (b.getDataDesbloqueio() != null) {
                Duration dur = Duration.between(b.getDataBloqueio(), b.getDataDesbloqueio());
                item.put("tempo_bloqueado", formatarDuration(dur));
            } else {
                item.put("tempo_bloqueado", "Ainda bloqueado");
            }

            resultado.add(item);
        }

        return resultado;
    }
}
