(ns curso.modulo-1.aula3)

(defn valor-descontado
  "Retorna o valor com desconto de 10% se deve aplicar desconto."
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(println "Testes da função aplica-desconto?:")
(println "1000 > 100:" (aplica-desconto? 1000))
(println "100 > 100:" (aplica-desconto? 100))

(println "Testes da função valor-descontado:")
(println "Valor 1000 com desconto:" (valor-descontado aplica-desconto? 1000))
(println "Valor 100 com desconto:" (valor-descontado aplica-desconto? 100))

(defn mais-caro-que-100?
  [valor-bruto]
  (println "deixando claro invocacao de mais-caro-que-100?")
  (> valor-bruto 100))

(println "Função como parâmetro:")
(println (valor-descontado mais-caro-que-100? 1000))
(println (valor-descontado mais-caro-que-100? 100))

(println "Função sem nome (anônima):")
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 100))
(println (valor-descontado (fn [v] (> v 100)) 1000))
(println (valor-descontado (fn [v] (> v 100)) 100))

(println "Usando reader macros:")
(println (valor-descontado #(> %1 100) 1000))
(println (valor-descontado #(> %1 100) 100))
(println (valor-descontado #(> % 100) 1000))
(println (valor-descontado #(> % 100) 100))

(def mais-caro-que-100?-fn (fn [valor-bruto] (> valor-bruto 100)))
(println "Função definida com fn:")
(println (valor-descontado mais-caro-que-100?-fn 1000))
(println (valor-descontado mais-caro-que-100?-fn 100))

(def mais-caro-que-100?-macro #(> % 100))
(println "Função definida com reader macro:")
(println (valor-descontado mais-caro-que-100?-macro 1000))
(println (valor-descontado mais-caro-que-100?-macro 100))
