# Regras de Negócio - Carrinho

## RN001 - Adicionar produto

### Funcionalidade
Carrinho

### Regra
Um produto disponível para compra pode ser adicionado ao carrinho.

### Condição
O produto deve estar disponível para compra.

### Comportamento esperado
O produto deve ser incluído no carrinho com a quantidade selecionada.

### Exceções
Produtos indisponíveis não devem ser adicionados ao carrinho.

### Termos relacionados
carrinho, produto, adicionar, compra, quantidade

---

## RN002 - Quantidade do produto

### Funcionalidade
Carrinho

### Regra
A quantidade de um produto deve respeitar os limites definidos para venda.

### Condição
O usuário alterar a quantidade de um produto no carrinho.

### Comportamento esperado
O sistema deve aceitar somente quantidades válidas.

### Exceções
Quantidades menores ou maiores que os limites permitidos devem ser rejeitadas.

### Termos relacionados
carrinho, quantidade, produto, limite, compra

---

## RN003 - Remover produto

### Funcionalidade
Carrinho

### Regra
O usuário deve poder remover produtos adicionados ao carrinho.

### Condição
O produto estiver presente no carrinho.

### Comportamento esperado
Após a remoção, o produto não deve mais fazer parte do carrinho.

### Exceções
A remoção não deve afetar outros produtos do carrinho.

### Termos relacionados
carrinho, remover, excluir, produto, compra

---

## RN004 - Total do carrinho

### Funcionalidade
Carrinho

### Regra
O total do carrinho deve representar a soma dos produtos e demais valores aplicáveis à compra.

### Condição
Existirem produtos no carrinho.

### Comportamento esperado
O sistema deve atualizar o total sempre que houver alteração nos produtos ou quantidades.

### Exceções
Valores inválidos não devem ser considerados no cálculo.

### Termos relacionados
carrinho, total, preço, valor, quantidade, cálculo

---

## RN005 - Carrinho vazio

### Funcionalidade
Carrinho

### Regra
Não deve ser possível iniciar o processo de compra sem produtos no carrinho.

### Condição
O carrinho não possuir produtos.

### Comportamento esperado
O sistema deve informar que o carrinho está vazio.

### Exceções
Nenhuma compra deve ser criada enquanto não houver produto.

### Termos relacionados
carrinho vazio, compra, checkout, produto, pedido