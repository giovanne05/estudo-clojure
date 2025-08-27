(ns curso.mock_database)

;; Dados para Aula 4 - Gerenciador de Estoque
(def estoque-exemplo
  [{:nome "Maçã" :quantidade 10}
   {:nome "Banana" :quantidade 15}
   {:nome "Laranja" :quantidade 8}
   {:nome "Uva" :quantidade 12}])

;; Dados para Aula 5 - Sistema de Cadastro de Alunos
(def alunos
  {:aluno1 {:nome "João" :idade 20 :curso "Matemática"}
   :aluno2 {:nome "Maria" :idade 22 :curso "Física"}})

;; Dados para Aula 6 - Sistema de Análise de Vendas
(def vendas
  [{:produto "Notebook" :preco 2500 :quantidade 2 :vendedor "João"}
   {:produto "Mouse" :preco 50 :quantidade 10 :vendedor "Maria"}
   {:produto "Teclado" :preco 100 :quantidade 5 :vendedor "João"}
   {:produto "Monitor" :preco 800 :quantidade 1 :vendedor "Pedro"}])

;; Dados para Módulo 2 - Aula 1 - Calculadora Recursiva
(def numeros-aula1 [10 20 30 40 50])

;; Dados para Módulo 2 - Aula 2 - Calculadora com Reduce
(def numeros-aula2 [1 2 3 4 5 6 7 8 9 10])

;; Dados para Módulo 2 - Aula 3 - Sistema de Banco em Memória
(def contas-bancarias
  {:contas {:conta1 {:numero "001" :titular "João" :saldo 1000 :tipo "corrente"}
            :conta2 {:numero "002" :titular "Maria" :saldo 2500 :tipo "poupanca"}
            :conta3 {:numero "003" :titular "Pedro" :saldo 500 :tipo "corrente"}}})

(def produtos [{:nome "Notebook" :preco 2500 :avaliacao 4.5 :vendas 100}
               {:nome "Mouse" :preco 50 :avaliacao 4.8 :vendas 500}
               {:nome "Teclado" :preco 100 :avaliacao 4.2 :vendas 200}
               {:nome "Monitor" :preco 800 :avaliacao 4.7 :vendas 150}])

(def dados [{:id 1 :valor 10 :ativo true}
            {:id 2 :valor nil :ativo false}
            {:id 3 :valor 25 :ativo true}
            {:id 4 :valor 0 :ativo true}])
(def cliente-banco {:id "número único"
                    :nome "nome do cliente"
                    :tipo "pessoa física ou jurídica"
                    :prioridade "normal ou preferencial"})
