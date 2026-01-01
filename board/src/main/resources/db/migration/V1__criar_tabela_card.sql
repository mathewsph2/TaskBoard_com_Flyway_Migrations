-- Tabela Board
CREATE TABLE board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
);

-- Tabela Coluna do Board
CREATE TABLE coluna_board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(100),
    ordem INT,
    board_id BIGINT NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board(id)
);

-- Tabela Cartao
CREATE TABLE cartao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    criado_em DATETIME NOT NULL,
    coluna_board_id BIGINT NOT NULL,
    FOREIGN KEY (coluna_board_id) REFERENCES coluna_board(id)
);

-- Tabela Bloqueio
CREATE TABLE bloqueio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    motivo_bloqueio TEXT,
    bloqueado_em DATETIME,
    motivo_desbloqueio TEXT,
    desbloqueado_em DATETIME,
    cartao_id BIGINT NOT NULL,
    FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);

