# 🤖 AI-QA-Assistant

Assistente inteligente para **Quality Assurance**, desenvolvido em Java, com integração ao **Google Gemini** para apoiar a análise de requisitos, identificação de riscos, geração de cenários e casos de teste e consulta inteligente a regras de negócio.

O projeto busca demonstrar, de forma prática, como **Inteligência Artificial pode ser aplicada ao processo de Quality Assurance**, utilizando requisitos, critérios de aceitação e conhecimento de negócio como contexto para as análises.

> 🚧 Projeto em evolução. Novas funcionalidades e integrações serão adicionadas de forma incremental.

---

## 🎯 Objetivo

O **AI-QA-Assistant** tem como objetivo criar um assistente capaz de apoiar diferentes atividades realizadas por profissionais de QA.

A proposta é centralizar capacidades de análise e conhecimento em uma aplicação que possa futuramente ser consumida através de uma **API REST**, permitindo que ferramentas como o **Postman** sejam utilizadas como interface para interação com o assistente.

Entre as capacidades planejadas estão:

* análise inteligente de User Stories;
* interpretação de critérios de aceitação;
* identificação de riscos de qualidade;
* geração de cenários de teste;
* geração de casos de teste;
* rastreabilidade entre requisitos, regras, riscos e testes;
* consulta inteligente à base de regras de negócio;
* respostas sobre o comportamento esperado do sistema;
* integração futura com ferramentas utilizadas no processo de QA.

---

# 🧠 Funcionalidades

## 📋 Análise de requisitos

A aplicação recebe informações relacionadas a uma User Story, incluindo:

* contexto;
* projeto;
* funcionalidade;
* plataforma;
* ambiente;
* observações;
* User Story;
* critérios de aceitação;
* regras de negócio.

A partir dessas informações, o Gemini realiza uma análise orientada para Quality Assurance.

A análise pode identificar:

* resumo do requisito;
* riscos;
* impacto;
* probabilidade;
* prioridade;
* cenários positivos;
* cenários negativos;
* cenários de borda;
* casos de teste;
* rastreabilidade entre os elementos analisados.

---

## 📚 Base de conhecimento

O projeto possui uma base de conhecimento formada por regras de negócio armazenadas em arquivos Markdown.

Atualmente a base está organizada por domínio:

```text
src/main/resources/business-rules/

├── cadastro.md
├── login.md
├── carrinho.md
├── checkout.md
├── pagamento.md
└── pedidos.md
```

A separação por domínio permite que a base de conhecimento cresça de forma organizada.

### Exemplo de regra

```text
RN001 - Nome obrigatório

Funcionalidade:
Cadastro

Regra:
O nome da conta deve ser informado para que o cadastro seja realizado.

Condição:
O campo nome deve possuir um valor válido.

Comportamento esperado:
O sistema deve permitir o cadastro quando o nome for informado corretamente.

Exceções:
Quando o nome não for informado, o cadastro não deve ser concluído.
```

---

# 💬 Assistente de conhecimento

O projeto possui um fluxo específico para consulta inteligente à base de regras de negócio.

O QA pode fazer uma pergunta utilizando linguagem natural, por exemplo:

```text
Quais são as regras para cadastrar uma conta?
```

O sistema pesquisa a base de conhecimento, identifica as regras relevantes e utiliza essas informações como contexto para o Gemini.

### Fluxo

```text
Pergunta do QA
      │
      ▼
KnowledgeQuery
      │
      ▼
KnowledgeService
      │
      ▼
KnowledgeSearchService
      │
      ▼
BusinessRuleRepository
      │
      ▼
Base de conhecimento
      │
      ▼
Regras relevantes
      │
      ▼
KnowledgePrompt
      │
      ▼
KnowledgeGeminiClient
      │
      ▼
Google Gemini
      │
      ▼
Resposta para o QA
```

O objetivo é fazer com que a IA responda utilizando o **conhecimento disponível na aplicação**, reduzindo respostas baseadas apenas em conhecimento genérico.

---

# 🔗 Rastreabilidade

Um dos princípios do projeto é manter rastreabilidade entre os elementos analisados.

Por exemplo:

```text
CA001
 │
 ▼
TS001
 │
 ▼
CT001
```

Também é possível relacionar regras e riscos:

```text
RN001
 │
 ▼
R001
 │
 ▼
TS001
 │
 ▼
CT001
```

Onde:

```text
CA = Critério de Aceitação
RN = Regra de Negócio
R  = Risco
TS = Cenário de Teste
CT = Caso de Teste
```

Essa estrutura permite identificar a origem de um caso de teste e entender quais requisitos, regras ou riscos estão relacionados a ele.

---

# 🧪 Geração de casos de teste

A aplicação utiliza o Gemini para auxiliar na criação de casos de teste a partir dos requisitos fornecidos.

Os casos possuem uma estrutura orientada ao formato:

```text
CT001 - Cadastro realizado com nome válido

Cenário: TS001
Tipo: Positivo
Prioridade: Alta

Dado:
que o usuário informe um nome válido

Quando:
realizar o cadastro da conta

Então:
o sistema deve permitir a criação da conta
```

Também são considerados cenários negativos e de borda quando aplicáveis.

Exemplo:

```text
CT002 - Cadastro bloqueado para nome contendo apenas espaços

Cenário: TS002
Tipo: Negativo
Prioridade: Alta

Dado:
que o usuário informe apenas espaços no campo nome

Quando:
tentar realizar o cadastro

Então:
o sistema deve impedir a conclusão do cadastro
```

---

# 🏗️ Arquitetura

O projeto utiliza separação de responsabilidades entre modelos, serviços, clientes, prompts, conhecimento, parsing e validação.

```text
br.com.qa.ai
│
├── client
│   ├── GeminiClient
│   └── KnowledgeGeminiClient
│
├── config
│   ├── GeminiConfig
│   └── TestCaseSchema
│
├── knowledge
│   ├── BusinessRuleRepository
│   └── BusinessKnowledgeService
│
├── model
│   ├── AcceptanceCriterion
│   ├── BusinessRule
│   ├── Context
│   ├── KnowledgeQuery
│   ├── QAAnalysisResponse
│   ├── QARequest
│   ├── Risk
│   ├── TestCase
│   ├── TestScenario
│   └── UserStory
│
├── parser
│   └── TestCaseParser
│
├── prompt
│   ├── KnowledgePrompt
│   └── QAAnalysisPrompt
│
├── service
│   ├── KnowledgeSearchService
│   ├── KnowledgeService
│   └── QAAnalysisService
│
└── validator
    └── QAAnalysisValidator
```

---

# 🔄 Fluxo de análise de QA

O fluxo principal de análise funciona da seguinte forma:

```text
                 User Story
                     │
          ┌──────────┼──────────┐
          │          │          │
          ▼          ▼          ▼
       Contexto   Critérios   Regras
          │       Aceitação   Negócio
          └──────────┼──────────┘
                     │
                     ▼
             QAAnalysisService
                     │
                     ▼
             QAAnalysisPrompt
                     │
                     ▼
                Gemini API
                     │
                     ▼
          Resposta estruturada
                     │
                     ▼
              TestCaseParser
                     │
                     ▼
            QAAnalysisValidator
                     │
                     ▼
            QAAnalysisResponse
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
       Riscos    Cenários    Casos de
                             Teste
```

---

# 🧩 Componentes principais

## `BusinessRuleRepository`

Responsável por acessar e carregar as regras de negócio armazenadas nos arquivos Markdown.

---

## `BusinessKnowledgeService`

Responsável por disponibilizar a base de conhecimento para os demais componentes da aplicação.

---

## `KnowledgeSearchService`

Responsável por pesquisar regras relevantes a partir da pergunta realizada pelo usuário.

---

## `KnowledgePrompt`

Responsável por construir o contexto enviado ao Gemini durante as consultas à base de conhecimento.

---

## `KnowledgeGeminiClient`

Responsável pela comunicação com o Gemini para consultas relacionadas ao conhecimento de negócio.

---

## `QAAnalysisService`

Responsável por orquestrar o processo de análise de QA.

---

## `QAAnalysisPrompt`

Responsável por definir as instruções utilizadas pelo Gemini durante a análise dos requisitos.

---

## `TestCaseParser`

Responsável por transformar a resposta estruturada da IA nos objetos utilizados pela aplicação.

---

## `QAAnalysisValidator`

Responsável por validar a resposta gerada antes de disponibilizá-la ao restante da aplicação.

---

# 🤖 Integração com Google Gemini

A aplicação utiliza o SDK do Google GenAI para comunicação com o Gemini.

A integração está isolada nos clientes:

```text
GeminiClient
KnowledgeGeminiClient
```

Essa separação permite trabalhar com diferentes responsabilidades:

```text
GeminiClient
     ↓
Análise estruturada de QA

KnowledgeGeminiClient
     ↓
Consultas à base de conhecimento
```

A configuração do Gemini é centralizada em:

```text
GeminiConfig
```

---

# 🔐 Configuração

As credenciais da API não devem ser armazenadas diretamente no código-fonte.

O projeto utiliza um arquivo local:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
gemini.api.key=SUA_CHAVE
gemini.model=gemini-3.6-flash
```

O arquivo com a chave real não deve ser versionado.

Ele está protegido pelo `.gitignore`.

Para disponibilizar um modelo de configuração, pode ser utilizado:

```text
application-example.properties
```

Exemplo:

```properties
gemini.api.key=
gemini.model=gemini-3.6-flash
```

---

# ⚙️ Tecnologias

| Tecnologia       | Utilização                            |
| ---------------- | ------------------------------------- |
| Java 21          | Linguagem principal                   |
| Maven            | Gerenciamento de dependências e build |
| Google Gemini    | Inteligência Artificial               |
| Google GenAI SDK | Integração com Gemini                 |
| Jackson          | Manipulação de JSON                   |
| JUnit 5          | Testes automatizados                  |
| AssertJ          | Assertions                            |

---

# 📂 Estrutura de recursos

```text
src/main/resources/
│
├── business-rules/
│   ├── cadastro.md
│   ├── login.md
│   ├── carrinho.md
│   ├── checkout.md
│   ├── pagamento.md
│   └── pedidos.md
│
├── application-example.properties
└── application.properties
```

> `application.properties` é uma configuração local e não deve ser versionado quando contiver credenciais.

---

# ▶️ Como executar

## 1. Clone o projeto

```bash
git clone https://github.com/washingtoncandido/AI-QA-Assistant.git
```

## 2. Acesse o projeto

```bash
cd AI-QA-Assistant
```

## 3. Configure o Gemini

Crie:

```text
src/main/resources/application.properties
```

Configure:

```properties
gemini.api.key=SUA_CHAVE
gemini.model=gemini-3.6-flash
```

## 4. Compile

```bash
mvn clean compile
```

## 5. Execute

O projeto possui diferentes pontos de entrada durante a fase atual de desenvolvimento.

### `Main`

Responsável pela demonstração da análise de QA e geração de casos de teste.

### `KnowledgeMain`

Responsável pela validação e visualização das regras carregadas da base de conhecimento.

### `KnowledgeAssistantMain`

Responsável pela demonstração do assistente de conhecimento, permitindo realizar perguntas sobre as regras de negócio.

---

# 🔮 Evolução planejada para API

Uma das próximas etapas do projeto será disponibilizar as funcionalidades através de uma **API REST**.

A ideia é transformar os fluxos atualmente executados pelos `Main` em funcionalidades acessíveis através de endpoints.

A arquitetura planejada será semelhante a:

```text
                         Postman
                            │
                            ▼
                     ┌─────────────┐
                     │  REST API   │
                     └──────┬──────┘
                            │
              ┌─────────────┼─────────────┐
              │             │             │
              ▼             ▼             ▼
        Knowledge       QA Analysis    Test Cases
              │             │             │
              ▼             ▼             ▼
       KnowledgeService  QAAnalysisService
              │             │
              └──────┬──────┘
                     ▼
                  Gemini
```

### Exemplos de endpoints planejados

Consulta à base de conhecimento:

```http
POST /api/knowledge/query
```

Exemplo de requisição:

```json
{
  "pergunta": "Quais são as regras para cadastrar uma conta?"
}
```

Análise de User Story:

```http
POST /api/qa/analyze
```

A API permitirá que ferramentas externas, como o Postman, sejam utilizadas para interagir com o assistente sem depender diretamente dos `Main`.

---

# 🧭 Evolução da aplicação

A evolução planejada segue aproximadamente estas etapas:

```text
Etapa 1
Fundação Java
      ↓
Etapa 2
Integração com Gemini
      ↓
Etapa 3
Análise de requisitos
      ↓
Etapa 4
Geração de casos de teste
      ↓
Etapa 5
Base de conhecimento
      ↓
Etapa 6
Assistente de conhecimento
      ↓
Etapa 7
API REST
      ↓
Etapa 8
Integração via Postman
      ↓
Etapa 9
Busca semântica / embeddings
      ↓
Etapa 10
Integrações com ferramentas de QA
```

---

# 🚧 Próximos passos

As próximas evoluções planejadas incluem:

* [ ] Criar uma API REST para disponibilizar as funcionalidades do assistente;
* [ ] Criar endpoint para consulta à base de conhecimento;
* [ ] Criar endpoint para análise de User Stories;
* [ ] Permitir envio de perguntas através do Postman;
* [ ] Disponibilizar diferentes capacidades do assistente através de endpoints;
* [ ] Melhorar o mecanismo de busca das regras de negócio;
* [ ] Implementar busca semântica utilizando embeddings;
* [ ] Permitir ingestão de diferentes fontes de requisitos;
* [ ] Melhorar a rastreabilidade entre requisitos, regras, riscos e casos de teste;
* [ ] Integrar com Azure DevOps;
* [ ] Integrar com projetos de automação de testes;
* [ ] Integrar com pipelines CI/CD.

---

# 🛡️ Princípios do projeto

### Separação de responsabilidades

Cada componente possui uma responsabilidade específica, evitando que a lógica de negócio, comunicação com IA, parsing e conhecimento fiquem concentrados em uma única classe.

### Contexto antes da IA

A IA recebe informações relevantes do projeto como contexto para realizar suas análises.

### Não inventar regras

As consultas à base de conhecimento devem utilizar as informações disponíveis na base.

### Rastreabilidade

Requisitos, critérios, regras, riscos, cenários e casos de teste devem possuir relacionamentos identificáveis.

### Segurança

Credenciais e chaves de API não devem ser armazenadas no código ou versionadas no repositório.

### Evolução incremental

A aplicação está sendo desenvolvida de maneira incremental, permitindo a inclusão de novas capacidades sem comprometer as funcionalidades existentes.

---

# 👨‍💻 Autor

**Washington Candido**

Projeto desenvolvido para explorar a aplicação prática de **Inteligência Artificial em Quality Assurance**, combinando:

* Engenharia de Software;
* Quality Assurance;
* Análise de requisitos;
* Regras de negócio;
* Inteligência Artificial;
* Geração de cenários e casos de teste;
* Conhecimento estruturado;
* API e integrações futuras.

---

## 🔗 Repositório

[github.com/washingtoncandido/AI-QA-Assistant](https://github.com/washingtoncandido/AI-QA-Assistant)
