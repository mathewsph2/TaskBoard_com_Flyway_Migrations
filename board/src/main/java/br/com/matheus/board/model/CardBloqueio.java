package br.com.matheus.board.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards_bloqueios")
public class CardBloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bloqueio")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_card", nullable = false)
    private Card card;

    @Column(name = "data_bloqueio", nullable = false)
    private LocalDateTime dataBloqueio;

    @Column(name = "motivo_bloqueio", nullable = false, length = 255)
    private String motivoBloqueio;

    @Column(name = "data_desbloqueio")
    private LocalDateTime dataDesbloqueio;

    @Column(name = "motivo_desbloqueio", length = 255)
    private String motivoDesbloqueio;

    @PrePersist
    public void prePersist() {
        this.dataBloqueio = LocalDateTime.now();
    }
    
    
    
    // Getters e Setters
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public LocalDateTime getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(LocalDateTime dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public LocalDateTime getDataDesbloqueio() {
		return dataDesbloqueio;
	}

	public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) {
		this.dataDesbloqueio = dataDesbloqueio;
	}

	public String getMotivoDesbloqueio() {
		return motivoDesbloqueio;
	}

	public void setMotivoDesbloqueio(String motivoDesbloqueio) {
		this.motivoDesbloqueio = motivoDesbloqueio;
	}

   
    
}
