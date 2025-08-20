(ns curso.modulo-1.aula4
  (:require
   [clojure.string :as str]
   [curso.mock_database :as db]))

(println "====== Desafio: Gerenciador de Estoque Simples ======")

(defn buscar-quantidade [produtos nome-produto]
  (let [produto (first (filter #(= (:nome %) nome-produto) produtos))]
    (get produto :quantidade 0)))

(defn incrementar-quantidade [produtos nome-produto quantidade-adicional]
  (map (fn [produto]
         (if (= (:nome produto) nome-produto)
           (update produto :quantidade + quantidade-adicional)
           produto))
       produtos))

(defn listar-nomes [produtos]
  (map :nome produtos))

(defn gerenciar-estoque [produtos]
  (println "\n=== Estoque Atual ===")
  (doseq [produto produtos]
    (println (str "- " (:nome produto) ": " (:quantidade produto) " unidades")))

  (println "\n=== Testando as funções ===")

  (let [qtd-maca (buscar-quantidade produtos "Maçã")]
    (println (str "Quantidade de Maçã: " qtd-maca " unidades")))

  (let [qtd-inexistente (buscar-quantidade produtos "Produto Inexistente")]
    (println (str "Quantidade de Produto Inexistente: " qtd-inexistente " unidades")))

  (let [produtos-atualizados (incrementar-quantidade produtos "Banana" 5)]
    (println "\n=== Após incrementar 5 bananas ===")
    (doseq [produto produtos-atualizados]
      (println (str "- " (:nome produto) ": " (:quantidade produto) " unidades"))))

  (let [nomes (listar-nomes produtos)]
    (println "\n=== Nomes dos produtos ===")
    (println (str "Lista de nomes: " (clojure.string/join ", " nomes)))))

(gerenciar-estoque db/estoque-exemplo)



