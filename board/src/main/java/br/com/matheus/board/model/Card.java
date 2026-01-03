package br.com.matheus.board.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_board", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "id_coluna_atual", nullable = false)
    private Coluna colunaAtual;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private boolean bloqueado = false;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<CardMovimentacao> movimentacoes;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<CardBloqueio> bloqueios;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    
    
    // Getters e Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Coluna getColunaAtual() {
		return colunaAtual;
	}

	public void setColunaAtual(Coluna colunaAtual) {
		this.colunaAtual = colunaAtual;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public List<CardMovimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<CardMovimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public List<CardBloqueio> getBloqueios() {
		return bloqueios;
	}

	public void setBloqueios(List<CardBloqueio> bloqueios) {
		this.bloqueios = bloqueios;
	}

   
    
    
    
    
}
