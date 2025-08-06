# 💳 Simulador de Caixa Eletrônico (ATM System)

Sistema de caixa eletrônico desenvolvido em **Java puro**, com persistência de dados via **JSON**, focado em práticas de programação orientada a objetos (POO), segurança com **hash de senhas**, e controle de transações por ID de usuário.  
Ideal para fins educacionais e como projeto demonstrativo para **vagas back-end**.

---

## 🧠 Funcionalidades

✔️ Cadastro e login de usuários  
✔️ Criação de contas bancárias com ID único  
✔️ Depósito, saque e transferência entre contas  
✔️ Histórico de transações por usuário  
✔️ Validação de saldo e segurança no acesso  
✔️ Persistência em arquivos `.json` (sem banco de dados)  
✔️ Separação por camadas: `model`, `service`, `logger`

---

## 📂 Estrutura de Pastas

```
Banco/
├── App/                  # Classe main (entrada do programa)
├── model/                # Modelos: User, BankAccount
├── service/              # Lógica de negócio: login, menu, operações bancárias
├── logger/               # Histórico de transações
├── Utils/                # Lógica de JSON: escrita e leitura de listas.
├── Cadastro.json         # Armazena credenciais dos usuários
├── Accounts.json         # Dados bancários vinculados ao ID do usuário
├── Historico.json        # Registros de transações (saques, depósitos, transferências)
```

---

## 🔐 Segurança

- As senhas são criptografadas utilizando **SHA-256** no momento do cadastro.
- O sistema valida CPF e senha na autenticação antes de permitir o acesso ao menu do usuário.
- O histórico registra todas as ações por ID, promovendo rastreabilidade.

---

## 🛠 Tecnologias Utilizadas

- Java (Java SE 17+)
- Biblioteca **Gson** para manipulação de arquivos JSON
- Programação orientada a objetos (POO)
- Conceitos de separação de responsabilidades

---

## 🧪 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/SimuladorCaixaEletronico.git
   ```

2. Abra o projeto em sua IDE Java (IntelliJ, Eclipse, etc.)

3. Certifique-se de que o arquivo `gson-<versão>.jar` esteja adicionado ao classpath.

4. Execute a classe `BancoApp.java` localizada em `App/`.

---

## 👨‍💻 Autor

**Thiago André**  
Desenvolvedor Back-End Java  
[LinkedIn](https://www.linkedin.com/in/tanm-dev) | [GitHub](https://github.com/seu-usuario)

---

## 📌 Observações

> Este projeto não utiliza banco de dados, frameworks externos (como Spring) ou Maven. Toda a estrutura foi implementada com **Java puro**, simulando um ambiente realista de sistema bancário com persistência via arquivos.
