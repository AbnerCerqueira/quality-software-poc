# Form

## Requisitos funcionais (RF)

| ID        | Título                             | Descrição                                                                                           |
| :-------- | :--------------------------------- | :-------------------------------------------------------------------------------------------------- |
| **RF-01** | Criar formulário                   | O sistema deve permitir que formulários sejam criados.                                              |
| **RF-02** | Adicionar campos ao formulário     | O sistema deve permitir adicionar novos campos ao formulário enquanto este estiver em estado DRAFT. |
| **RF-03** | Publicar formulário PUBLISHED      | O sistema deve permitir que formulários sejam publicados                                            |
| **RF-05** | Listar respostas de um formulários | O sistema deve permitir que as respostas dos formulários sejam visualizadas.                        |
| **RF-06** | Fechar formulário CLOSED           | O sistema deve permitir que o formulário seja encerrado                                             |
| **RF-08** | Listar formulários                 | O sistema deve permitir que formulários sejam visualizados                                          |

## Regras de negócio (RN)

| ID        | Título                 | Descrição                                                        |
| :-------- | :--------------------- | :--------------------------------------------------------------- |
| **RN-01** | Edição de formulário   | Apenas formulários com status DRAFT podem ser modificados.       |
| **RN-03** | Após fechar formulário | Formulários com status CLOSED não podem ser abertos novamente    |
| **RN-04** | Campos obrigatórios    | Campos obrigatórios precisam estar sempre presentes na submissão |
| **RN-05** | Campos numéricos       | Campos numéricos devem respeitar o mínimo e máximo definidos     |
| **RN-06** | Campos select          | Campos do tipo select devem aceitar apenas valores pré-definidos |
| **RN-07** | Quantidade de campos   | Formulários devem ter no mínimo 1 campo e no máximo 100          |
| **RN-09** | Duplicatas de campos   | Não devem existir campos iguais dentro do mesmo formulário       |
| **RN-10** | Criar CLOSED           | Um formulário só pode ser criado com status DRAFT ou PUBLISHED   |

# Answer

## Requisitos funcionais (RF)

| ID        | Título                             | Descrição                                                               |
| :-------- | :--------------------------------- | :---------------------------------------------------------------------- |
| **RF-04** | Submeter respostas ao formulário   | O sistema deve permitir que formulários sejam preenchidos e submetidos. |
| **RF-07** | Impedir submissões após fechamento | O sistema deve impedir submissões quando o formulário estiver CLOSED    |

## Regras de negócio (RN)

| ID        | Título                    | Descrição                                                                     |
| :-------- | :------------------------ | :---------------------------------------------------------------------------- |
| **RN-02** | Submissão de respostas    | Apenas formulários com status PUBLISHED podem receber respostas               |
| **RN-08** | Integridade das respostas | Submissões de respostas devem conter somente campos que existem no formulário |

# Requisitos não funcionais (RNF)

| ID         | Título    | Descrição                                                        |
| :--------- | :-------- | :--------------------------------------------------------------- |
| **RNF-01** | Paginação | Os endpoints de GET devem ter opção para paginação das respostas |
