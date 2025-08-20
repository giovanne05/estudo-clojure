(ns curso.modulo-2.aula5
  (:require
   [curso.mock_database :as db]))

(def dados db/dados)

(println "====== Desafio: Sistema de Filtros Inteligentes ======")

(defn validar-dados [dados]
  (keep #(when (and (:valor %) (> (:valor %) 0)) (:valor %)) dados))

(defn demonstrar-eager-lazy [dados]
  (let [lazy-seq (map #(when (:valor %) (* (:valor %) 2)) dados)
        eager-seq (doall lazy-seq)]
    (println "Lazy:" (type lazy-seq))
    (println "Eager:" (type eager-seq))
    eager-seq))

(defn processar-chunks [dados]
  (->> dados
       (partition-all 2)
       (map #(apply + (filter number? (map :valor %))))))

(defn criar-lista-ligada [elementos]
  (reduce #(cons %2 %1) '() elementos))

(println "Dados válidos:" (validar-dados dados))
(println "Eager vs Lazy:" (demonstrar-eager-lazy dados))
(println "Processamento em chunks:" (processar-chunks dados))
(println "Lista ligada:" (criar-lista-ligada [1 2 3 4 5]))




