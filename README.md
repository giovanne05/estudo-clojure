# Curso de Introdução ao Clojure - Aula 2

Este é um projeto de aprendizado de Clojure que contém exemplos e exercícios da segunda aula.

## Pré-requisitos

- Java (JDK 8 ou superior)
- Leiningen (já instalado via Homebrew)

## Como usar

### Executar o projeto
```bash
lein run
```

### Iniciar o REPL
```bash
lein repl
```

### Executar testes
```bash
lein test
```

### Compilar o projeto
```bash
lein compile
```

## Estrutura do projeto

- `src/curso/core.clj` - Arquivo principal com função `-main`
- `src/curso/aula2.clj` - Exemplos e exercícios da aula 2
- `test/curso/core_test.clj` - Testes do projeto

## Configuração do Cursor/VS Code

O projeto já está configurado com:
- Configuração do clj-kondo para linting
- Configuração do LSP para Clojure
- Caminho correto para o Leiningen

## Conteúdo da Aula 2

O arquivo `aula2.clj` contém exemplos de:
- Definição de funções
- Aplicação de descontos
- Uso de `let` para variáveis locais
- Estruturas condicionais com `if`
- Tipos de dados numéricos

## Resolução de problemas

Se você encontrar o erro "LSP classpath lookup failed", verifique se:
1. O Leiningen está instalado: `which lein`
2. O projeto pode ser compilado: `lein classpath`
3. As configurações do `.vscode/settings.json` estão corretas
