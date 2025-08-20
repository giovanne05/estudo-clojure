(ns curso.modulo-2.aula2)
(def numeros [1 2 3 4 5 6 7 8 9 10])

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
(println "Soma com reduce:" (somar-lista-com-reduce numeros))
(println "Soma sem reduce:" (somar-lista-sem-reduce numeros))






