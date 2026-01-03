# 🗂️ TaskBoard_com_Flyway_Migrations

Sistema de Gestão de Cards via **console**, desenvolvido em **Java + Spring Boot**. Ele permite criar boards, colunas, cards, mover cards entre colunas, bloquear/desbloquear, cancelar e gerar relatórios detalhados.


## 🚀 Funcionalidades 

### 🧩 **Boards** 

- Criar novo board;
- Listar boards existentes;
- Selecionar board;
- Excluir board.

  ### 📌 **Cards** 
- Criar card;
- Listar cards do board;
- Mover card para próxima coluna;
- Cancelar card;
- Bloquear card (com motivo);
- Desbloquear card (com motivo).


### 📊 **Relatórios** 

Tempo gasto por coluna;
Histórico de bloqueios;
Relatórios consolidados (Implementar).

## 🏗️ Estrutura do Board 

Ao criar um board, o sistema monta automaticamente: 
1. **Coluna Inicial**
2. **Colunas extras** (quantidade definida pelo usuário)
3. **Coluna Final**
4. **Coluna Cancelado**


---

## 📌 Regras Importantes

### ✔ Mover card
- Não move se estiver **bloqueado**
- Não move se estiver em **Final**
- Não move se estiver em **Cancelado**
- Só avança para a próxima coluna válida

### ✔ Cancelar card
- Só pode cancelar se **não estiver finalizado**
- Move diretamente para a coluna **Cancelado**

### ✔ Bloqueios
- Cada bloqueio registra:
  - Motivo;
  - Data de bloqueio;
  - Data de desbloqueio;
  - Tempo total bloqueado;

---

## 📊 Exemplos de Relatórios


### ⏱️ Tempo por coluna

### 🚫 Histórico de bloqueios


---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Console I/O (Scanner)**

---
