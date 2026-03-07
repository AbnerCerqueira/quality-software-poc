# Backend

## Getting started

### Pré-requisitos
- Docker 29
- Java 21
- MongoDB 7

### Iniciar MongoDB

```bash
docker compose up -d
```

### Rodar aplicação

```bash
./mvnw spring-boot:run
```

### Rodar testes

```bash
./mvnw test
```

### Swagger

A documentação da API está disponível em:

```
http://localhost:8080/swagger-ui.html
```

Documentação OpenAPI em JSON:

```
http://localhost:8080/api-docs
```

## Code style, linting and format
This backend uses Maven with Google style as baseline for formatting and linting.

This project uses:

- Spotless + `google-java-format` for automatic formatting
- Checkstyle with `google_checks.xml` for style rules
- SpotBugs for bug pattern analysis

Project-level customizations (versioned in repository):

- `checkstyle-suppressions.xml` to disable specific Checkstyle checks
- `.vscode/java-formatter.xml` to enforce 2-space Java indentation in VS Code

### Day-to-day commands

- Format all Java files:

```bash
./mvnw spotless:apply
```

- Check formatting only:

```bash
./mvnw spotless:check
```

- Full quality gate:

```bash
./mvnw verify
```

## Checklist VS Code (2 minutos)

- [ ] Instalar `Extension Pack for Java`
- [ ] Instalar `Checkstyle for Java`
- [ ] Instalar `SonarLint`
- [ ] Confirmar `editor.formatOnSave: true` em `.vscode/settings.json`
- [ ] `Ctrl+Shift+P` -> `Checkstyle: Set Checkstyle Configuration` -> **Google Checks** 13.3.0
- [ ] Verificar se o plugin de Checkstyle esta lendo `checkstyle-suppressions.xml`
- [ ] Abrir aba `Problems` e validar feedback em tempo real
- [ ] Rodar `./mvnw spotless:check`
- [ ] Rodar `./mvnw -DskipTests verify`

## Checklist IntelliJ (2 minutos)

- [ ] Instalar plugin `Checkstyle-IDEA`
- [ ] Instalar plugin `SonarLint`
- [ ] Ir em `Settings > Tools > Checkstyle`
- [ ] Adicionar configuracao com **Google Checks** 13.3.0 (bundled)
- [ ] Ativar inspecao do Checkstyle no editor
- [ ] Ir em `Settings > Tools > Actions on Save`
- [ ] Marcar `Reformat code`
- [ ] Marcar `Optimize imports`
- [ ] Validar avisos no editor e em `Problems/Inspections`
- [ ] Rodar `./mvnw spotless:check`
- [ ] Rodar `./mvnw -DskipTests verify`
