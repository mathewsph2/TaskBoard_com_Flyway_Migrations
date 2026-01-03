-- ================== -- TABELA DE CARDS -- =================== 

CREATE TABLE cards ( 
id_card BIGINT PRIMARY KEY AUTO_INCREMENT, 
id_board BIGINT NOT NULL, 
id_coluna_atual BIGINT NOT NULL, 
titulo VARCHAR(100) NOT NULL, 
descricao VARCHAR(255) NOT NULL, 
data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
bloqueado BOOLEAN DEFAULT FALSE, 
FOREIGN KEY (id_board) REFERENCES boards(id_board), 
FOREIGN KEY (id_coluna_atual) REFERENCES colunas(id_coluna) 
);