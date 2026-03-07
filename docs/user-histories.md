## US-01 Criar formulário

Como usuário
Quero criar um formulário
Para publicá-lo posteriormente

### Cenários
#### 1. RF-01, RF-02

* Dado foi informado título e descrição
* E informa dados válidos de curso e aulas  
* Quando submeter  
* Então o curso é criado

#### 2. RF-01, RN-07

* Dado que foi informado um formulário sem nenhum campo
* Quando submeter  
* Então um erro é disparado

#### 3. RF-01, RN-09

* Dado que foi informado um formulário com campos duplicados
* Quando submeter
* Então um erro é disparado

#### 4. RF-01, RN-09

* Dado que foi informado um formulário com campos duplicados
* Quando submeter
* Então um erro é disparado

#### 5. RF-01, RF-02, RN-05

* Dado que foi adicionado campo inválido no formulário
* Quando submeter
* Então um erro é disparado

## US-02 Editar formulário

Como usuário
Quero editar um formulário
Para posteriormente publicá-lo

### Cenários
#### 1. RF-02, RN-01, RN-05, RN-07

* Dado que foi selecionado um formulário em status DRAFT
* Quando ocorrer a edição
* Então o formulário é editado
