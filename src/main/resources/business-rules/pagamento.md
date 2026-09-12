# Regras de Negócio - Pagamento

## RN001 - Forma de pagamento válida

### Funcionalidade 
Pagamento

### Regra
Somente formas de pagamento disponíveis para a compra podem ser utilizadas.

### Condição
O cliente selecionar uma forma de pagamento.

### Comportamento esperado
O sistema deve permitir somente métodos de pagamento habilitados.

### Exceções
Métodos indisponíveis não devem ser utilizados para concluir a compra.

### Termos relacionados
pagamento, forma de pagamento, método, compra, checkout

---

## RN002 - Valor do pagamento

### Funcionalidade
Pagamento

### Regra
O valor do pagamento deve corresponder ao valor devido pelo pedido.

### Condição
O cliente realizar o pagamento.

### Comportamento esperado
O sistema deve processar o pagamento considerando o valor correto do pedido.

### Exceções
Valores divergentes não devem resultar em uma aprovação incorreta.

### Termos relacionados
pagamento, valor, preço, pedido, cobrança

---

## RN003 - Pagamento aprovado

### Funcionalidade
Pagamento

### Regra
O pedido pode avançar somente quando o pagamento for aprovado.

### Condição
A transação de pagamento retornar status aprovado.

### Comportamento esperado
O pedido deve avançar para a próxima etapa do fluxo de compra.

### Exceções
Pagamentos recusados ou pendentes não devem ser tratados como aprovados.

### Termos relacionados
pagamento aprovado, aprovação, transação, pedido, compra

---

## RN004 - Pagamento recusado

### Funcionalidade
Pagamento

### Regra
Um pagamento recusado não deve resultar na confirmação do pedido como pago.

### Condição
A transação retornar status recusado.

### Comportamento esperado
O sistema deve informar que o pagamento não foi aprovado.

### Exceções
O pedido não deve ser considerado pago.

### Termos relacionados
pagamento recusado, transação, erro, compra, pedido

---

## RN005 - Pagamento pendente

### Funcionalidade
Pagamento

### Regra
Um pagamento pendente não deve ser tratado como pagamento aprovado.

### Condição
A transação retornar status pendente.

### Comportamento esperado
O pedido deve permanecer com status compatível com o pagamento pendente.

### Exceções
O pedido não deve ser considerado concluído como pago antes da confirmação.

### Termos relacionados
pagamento pendente, status, transação, aprovação, pedido