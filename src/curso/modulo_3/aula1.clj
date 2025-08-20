(ns curso.modulo-3.aula1
  (:require
   [schema.core :as s]))

(s/defschema Usuario
  {:nome s/Str
   :idade s/Int
   :email s/Str
   :ativo s/Bool})

(s/defschema Produto
  {:nome s/Str
   :preco (s/constrained s/Num pos?)
   :categoria s/Str
   :estoque (s/constrained s/Num pos?)})

(defn validar-usuario [usuario]
  (s/validate Usuario usuario))

(defn validar-produto [produto]
  (s/validate Produto produto))

(println "====== Desafio: Sistema de Validação de Dados com Schema ======")

(defn processar-usuario [usuario]
  (validar-usuario usuario))

(defn processar-produto [produto]
  (validar-produto produto))

(defn verificar-tipo [dados schema]
  (s/check schema dados))

(println "====== Desafio: Sistema de Validação de Dados com Schema ======")

(println "=== Dados Válidos ===")
(println "Usuário válido:" (processar-usuario {:nome "João" :idade 25 :email "joao@gmail.com" :ativo true}))
(println "Produto válido:" (processar-produto {:nome "Notebook" :preco 2500 :categoria "Eletrônico" :estoque 10}))

(println "\n=== Verificação de Tipos ===")
(println "Verificação usuário:" (verificar-tipo {:nome "João" :idade 25 :email "joao@gmail.com" :ativo true} Usuario))
(println "Verificação produto:" (verificar-tipo {:nome "Notebook" :preco 2500 :categoria "Eletrônico" :estoque 10} Produto))

(println "\n=== Dados Inválidos ===")
(try
  (println "Usuário inválido:" (processar-usuario {:nome "João" :idade "vinte e cinco" :email "joao@gmail.com" :ativo true}))
  (catch Exception e
    (println "Erro na validação:" (.getMessage e))))

(try
  (println "Produto com preço inválido:" (processar-produto {:nome "Produto" :preco -100 :categoria "Teste" :estoque 10}))
  (catch Exception e
    (println "Erro na validação do produto:" (.getMessage e))))

(try
  (println "Produto com estoque inválido:" (processar-produto {:nome "Produto" :preco 100 :categoria "Teste" :estoque -5}))
  (catch Exception e
    (println "Erro na validação do estoque:" (.getMessage e))))




