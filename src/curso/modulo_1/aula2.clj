(ns curso.modulo-1.aula2)

(defn imprime-mensagem []
  (println "------------------------")
  (println "Bem vindo(a) ao estoque!"))

(imprime-mensagem)

(defn aplica-desconto [valor-bruto]
  (* valor-bruto 0.9))

(println (aplica-desconto 100))

;; Versão final da função valor-descontado
(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (println "Calculando desconto de " desconto)
      (- valor-bruto desconto))
    valor-bruto))

;; Testes da função final
(println "Teste com valor > 100:")
(valor-descontado 1000)

(println "Teste com valor <= 100:")
(valor-descontado 100)

;; Demonstrações de tipos
(println "Tipos de números:")
(println "90.0 é do tipo:" (class 90.0))
(println "90N é do tipo:" (class 90N))
(println "90M é do tipo:" (class 90M))

;; Demonstrações de comparações
(println "Comparações:")
(println "500 > 100:" (> 500 100))
(println "500 < 100:" (< 500 100))

(println "Teste if com 500 > 100:")
(if (> 500 100)
  (println "maior")
  (println "menor ou igual"))

(println "Teste if com 50 > 100:")
(if (> 50 100)
  (println "maior")
  (println "menor ou igual"))

