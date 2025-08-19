(ns curso.modulo-2.aula1)

(println "====== Desafio: Calculadora Recursiva - Soma de Lista ======")

(def numeros [10 20 30 40 50])

(defn processar-lista-recursivo [lista valor-inicial funcao-processamento]
  (loop [lista-atual lista
         resultado-atual valor-inicial]
    (if (seq lista-atual)
      (recur (rest lista-atual) (funcao-processamento (first lista-atual) resultado-atual))
      resultado-atual)))

(defn somar-lista-recur [lista]
  (processar-lista-recursivo lista 0 +))

(defn buscar-maior-numero [lista]
  (processar-lista-recursivo lista (first lista)
                             (fn [elemento-atual maior-atual]
                               (if (> elemento-atual maior-atual) elemento-atual maior-atual))))

(defn contar-pares [lista]
  (processar-lista-recursivo lista 0
                             (fn [elemento-atual contador]
                               (if (even? elemento-atual) (inc contador) contador))))
(defn dobrar-numeros [lista]
  (map #(* 2 %) lista))

(println "Lista original:" numeros)
(println "Soma usando recur:" (somar-lista-recur numeros))
(println "Maior número:" (buscar-maior-numero numeros))
(println "Quantidade de pares:" (contar-pares numeros))
(println "Dobro dos números:" (dobrar-numeros numeros))
 
