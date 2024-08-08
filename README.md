# 💻 Ballit Championship (IT Academy 21)


<div align="center">
 <img height=170 width=200 src="https://media.tenor.com/_mYZWyrW3AUAAAAi/peach-goma-pc-night-keyboard-smashing.gif">
</div>

## 📝 Descrição
Sistema para gerenciar pontuações do campeonato de BALLIT, um jogo esquisito.

## 🔧 Como Executar

### Passo 1: Configurar o Banco de Dados

O banco de dados é configurado para ser executado em um container Docker.

- Inicie o banco de dados com o Docker Compose:
- 
   ```bash
   docker compose up
   ```
  
Isso vai baixar a imagem do PostgreSQL, configurar e iniciar o container com o banco de dados.

### Passo 2: Executar a Aplicação

1. Compile as dependencias do projeto utilizando o Maven
2. Execute a aplicação
3. A aplicação ficará em `http://localhost:8080`(frontend).

### Frontend

- O frontend da aplicação tá dentro do projeto e é acessada pelo navegador. 
  - Para acessar a interface gráfica: `http://localhost:8080`

### API Endpoints

- A documentação da API está disponível pelo Swagger:
  - `http://localhost:8080/swagger-ui.html`

### Observações

- Cuidado pra porta 5432 (que é a do PostgreSQL) e a porta 8080 (que é a do Spring Boot) estarem livres, se não vai dar conflito.
- O banco de dados está no container Docker, que vai fazer com que os dados não sejam perdidos entre reinicializações.

Obrigada! 🚀✨
