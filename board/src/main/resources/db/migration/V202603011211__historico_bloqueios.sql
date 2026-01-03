-- == -- HISTÓRICO DE BLOQUEIOS DOS CARDS -- (para relatórios de bloqueios) -- == 
CREATE TABLE cards_bloqueios ( 
id_bloqueio BIGINT PRIMARY KEY AUTO_INCREMENT, 
id_card BIGINT NOT NULL, 
data_bloqueio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
motivo_bloqueio VARCHAR(255) NOT NULL, 
data_desbloqueio TIMESTAMP NULL, 
motivo_desbloqueio VARCHAR(255) NULL, 
FOREIGN KEY (id_card) REFERENCES cards(id_card) 
);