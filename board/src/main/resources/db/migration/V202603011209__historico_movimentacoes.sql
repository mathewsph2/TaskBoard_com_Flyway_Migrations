-- === -- HISTÓRICO DE MOVIMENTAÇÃO DOS CARDS -- (para relatórios de tempo por coluna) -- ===== 
CREATE TABLE cards_movimentacao ( 
id_mov BIGINT PRIMARY KEY AUTO_INCREMENT, 
id_card BIGINT NOT NULL, 
id_coluna BIGINT NOT NULL, 
data_entrada TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
data_saida TIMESTAMP NULL, 
FOREIGN KEY (id_card) REFERENCES cards(id_card), 
FOREIGN KEY (id_coluna) REFERENCES colunas(id_coluna) 
);