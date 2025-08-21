(ns curso.modulo-3.aula2
  (:require
   [schema.core :as s]))

(s/defschema Cliente
  {:nome s/Str
   :email (s/constrained s/Str #(re-matches #".*@.*\..*" %))
   :telefone s/Str})

(s/defschema Item
  {:produto s/Str
   :quantidade (s/constrained s/Int pos?)
   :preco (s/constrained s/Num pos?)})

(s/defschema PedidoSchema
  {:id (s/constrained s/Int pos?)
   :cliente Cliente
   :itens [Item]
   :total (s/constrained s/Num pos?)
   :status (s/enum "pendente" "aprovado" "cancelado")
   :observacoes (s/maybe s/Str)})

(defn criar-pedido [dados]
  (s/validate PedidoSchema dados))

(defn explicar-erro [dados]
  (s/explain (s/check PedidoSchema dados)))

(defn validar-com-pred [valor]
  (s/validate (s/pred #(> % 100) 'maior-que-100) valor))

(defn processar-com-validacao [ativar? dados]
  (if ativar?
    (s/with-fn-validation (criar-pedido dados))
    (criar-pedido dados)))

(defn validacao-em-sequencia [dados]
  (-> dados
      (s/validate Cliente)
      (s/validate Item)
      (s/validate PedidoSchema)))

(println "====== Desafio: Sistema de Gerenciamento de Pedidos ======")

(def pedido-valido
  {:id 1
   :cliente {:nome "João" :email "joao@email.com" :telefone "123456789" :idade 25}
   :itens [{:produto "Notebook" :quantidade 1 :preco 2500 :desconto 10}]
   :total 2500
   :status "pendente"
   :observacoes "Pedido urgente"})

(def pedido-invalido
  {:id -1
   :cliente {:nome "João" :email "email-invalido" :telefone "123"}
   :itens [{:produto "Mouse" :quantidade 0 :preco -50}]
   :total -50
   :status "invalido"})

(println "Pedido válido:" (criar-pedido pedido-valido))
(println "Explicação do erro:" (explicar-erro pedido-invalido))
(println "Validação com pred:" (validar-com-pred 150))
(println "Processamento com validação ativada:" (processar-com-validacao true pedido-valido))
