package br.com.matheus.board.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "colunas")
public class Coluna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coluna")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_board", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoColuna tipo;

    @OneToMany(mappedBy = "colunaAtual")
    private List<Card> cards;

    
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public TipoColuna getTipo() {
		return tipo;
	}

	public void setTipo(TipoColuna tipo) {
		this.tipo = tipo;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

   
    
    
    
}
