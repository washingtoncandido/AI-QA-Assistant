# Regras de Negócio - Checkout

## RN001 - Carrinho com produto

### Funcionalidade
Checkout

### Regra
O checkout deve ser iniciado somente quando houver pelo menos um produto no carrinho.

### Condição
O carrinho possuir produtos válidos.

### Comportamento esperado
O usuário deve poder avançar para o checkout.

### Exceções
Um carrinho vazio não deve iniciar o checkout.

### Termos relacionados
checkout, carrinho, produto, compra, pedido

---

## RN002 - Identificação do cliente

### Funcionalidade
Checkout

### Regra
O cliente deve estar identificado antes da finalização da compra.

### Condição
O usuário iniciar o processo de finalização.

### Comportamento esperado
O sistema deve identificar o cliente antes de permitir a conclusão do pedido.

### Exceções
Caso o cliente não esteja identificado, o sistema deve solicitar a identificação.

### Termos relacionados
checkout, cliente, identificação, login, compra, pedido

---

## RN003 - Endereço de entrega

### Funcionalidade
Checkout

### Regra
A compra deve possuir um endereço de entrega válido quando a modalidade de entrega exigir endereço.

### Condição
O pedido utilizar uma modalidade de entrega que necessite de endereço.

### Comportamento esperado
O sistema deve utilizar um endereço válido para calcular e realizar a entrega.

### Exceções
Pedidos sem endereço válido não devem ser finalizados quando a entrega exigir endereço.

### Termos relacionados
checkout, endereço, entrega, frete, pedido

---

## RN004 - Forma de pagamento

### Funcionalidade
Checkout

### Regra
O cliente deve selecionar uma forma de pagamento válida antes de finalizar a compra.

### Condição
O cliente estiver na etapa de pagamento.

### Comportamento esperado
O sistema deve permitir a seleção de uma forma de pagamento disponível.

### Exceções
A compra não deve ser finalizada sem uma forma de pagamento válida.

### Termos relacionados
checkout, pagamento, forma de pagamento, compra, pedido

---

## RN005 - Finalização do pedido

### Funcionalidade
Checkout

### Regra
O pedido somente deve ser criado após a validação das informações necessárias para a compra.

### Condição
Produtos, cliente, entrega e pagamento estiverem válidos.

### Comportamento esperado
O sistema deve concluir a compra e criar o pedido.

### Exceções
Caso alguma informação obrigatória seja inválida, o pedido não deve ser criado.

### Termos relacionados
checkout, finalizar compra, pedido, pagamento, entrega