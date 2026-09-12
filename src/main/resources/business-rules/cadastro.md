# Regras de Negócio - Cadastro

## RN001 - Nome obrigatório

### Funcionalidade
Cadastro

### Regra
O nome da conta deve ser informado para que o cadastro seja realizado.

### Condição
O campo nome deve possuir um valor válido.

### Comportamento esperado
O sistema deve permitir o cadastro quando o nome for informado corretamente.

### Exceções
Quando o nome não for informado, o cadastro não deve ser concluído.

### Termos relacionados
cadastro, criar conta, nome, conta, usuário, registro

---

## RN002 - Nome somente com espaços

### Funcionalidade
Cadastro

### Regra
Não é permitido cadastrar uma conta utilizando apenas espaços no campo nome.

### Condição
O valor informado no campo nome não pode ser composto somente por espaços.

### Comportamento esperado
O sistema deve rejeitar o cadastro quando o nome informado possuir somente espaços.

### Exceções
Espaços no início ou no final do nome devem ser tratados conforme a política de validação da aplicação.

### Termos relacionados
cadastro, nome, espaços, validação, campo obrigatório, criar conta

---

## RN003 - Usuário autenticado

### Funcionalidade
Cadastro

### Regra
Somente usuários autenticados podem criar uma conta.

### Condição
O usuário deve estar autenticado antes de realizar a criação da conta.

### Comportamento esperado
Usuários autenticados podem realizar a criação da conta.

### Exceções
Usuários não autenticados não devem conseguir criar uma conta.

### Termos relacionados
cadastro, criar conta, autenticação, login, usuário logado, usuário autenticado

---

## RN004 - Vinculação da conta

### Funcionalidade 
Cadastro

### Regra
A conta criada deve ser vinculada ao usuário que realizou o cadastro.

### Condição
O usuário deve estar autenticado durante a criação da conta.

### Comportamento esperado
Após o cadastro, a conta deve estar associada ao usuário responsável pela criação.

### Exceções
O sistema não deve vincular a conta a outro usuário.

### Termos relacionados
cadastro, conta, usuário, vínculo, associação, criação de conta

---

## RN005 - Mensagem de validação

### Funcionalidade
Cadastro

### Regra
Quando o nome não for informado, o sistema deve apresentar uma mensagem de validação.

### Condição
O campo nome estiver vazio.

### Comportamento esperado
O sistema deve apresentar a mensagem:

"Nome da conta é obrigatório."

### Exceções
A mensagem não deve ser apresentada quando o nome for informado corretamente.

### Termos relacionados
cadastro, validação, mensagem, nome, campo obrigatório, erro