(ns curso.modulo-2.aula4)

(println "====== Desafio: Sistema de Ranking de Produtos ======")

(def produtos [{:nome "Notebook" :preco 2500 :avaliacao 4.5 :vendas 100}
               {:nome "Mouse" :preco 50 :avaliacao 4.8 :vendas 500}
               {:nome "Teclado" :preco 100 :avaliacao 4.2 :vendas 200}
               {:nome "Monitor" :preco 800 :avaliacao 4.7 :vendas 150}])

(defn ranking-por-preco [produtos]
  (sort-by :preco > produtos))

(defn top-2-produtos [produtos]
  (take 2 produtos))

(defn existe-avaliacao-alta [produtos]
  (some #(> (:avaliacao %) 4.5) produtos))

(defn terceiro-produto [produtos]
  (nth produtos 2))

(defn ultimo-produto-invertido [produtos]
  (-> produtos reverse first))

(println "Ranking por preço:" (ranking-por-preco produtos))
(println "Top 2 produtos:" (top-2-produtos produtos))
(println "Existe avaliação alta:" (existe-avaliacao-alta produtos))
(println "Terceiro produto:" (terceiro-produto produtos))
(println "Último produto invertido:" (ultimo-produto-invertido produtos))
