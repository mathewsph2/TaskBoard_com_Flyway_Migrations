CREATE TABLE movimentacao_cartao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cartao_id BIGINT NOT NULL,
    coluna_origem_id BIGINT,
    coluna_destino_id BIGINT NOT NULL,
    entrou_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    saiu_em DATETIME DEFAULT NULL,

    FOREIGN KEY (cartao_id) REFERENCES cartao(id),
    FOREIGN KEY (coluna_origem_id) REFERENCES coluna_board(id),
    FOREIGN KEY (coluna_destino_id) REFERENCES coluna_board(id)
);


