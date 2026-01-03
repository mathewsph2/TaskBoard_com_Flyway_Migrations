-- ======================= -- TABELA DE COLUNAS DO BOARD -- ============================= 
CREATE TABLE colunas ( 
id_coluna BIGINT PRIMARY KEY AUTO_INCREMENT, 
id_board BIGINT NOT NULL, 
nome VARCHAR(100) NOT NULL, 
ordem INT NOT NULL, 
tipo ENUM('INICIAL','PENDENTE','FINAL','CANCELAMENTO') 
NOT NULL, 
FOREIGN KEY (id_board) REFERENCES boards(id_board) 
);