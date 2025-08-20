(ns curso.modulo-2.aula2
  (:require
   [curso.mock_database :as db]))

(defn somar-lista-com-reduce [numeros] (reduce + numeros))

(defn somar-lista-sem-reduce
  ([numeros] (somar-lista-sem-reduce 0 numeros))
  ([total numeros]
   (loop [total-atual total numeros-restantes numeros]
     (if (seq numeros-restantes)
       (recur (+ total-atual (first numeros-restantes))
              (rest numeros-restantes))
       total-atual))))

(println "====== Desafio: Calculadora com Reduce e Loop ======")
(println "Soma com reduce:" (somar-lista-com-reduce db/numeros-aula2))
(println "Soma sem reduce:" (somar-lista-sem-reduce db/numeros-aula2))






