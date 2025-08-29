(ns curso.modulo-5.aula3)

(println "====== DESAFIO: Sistema de Cache com Componentização OO ======")

(defrecord Cache [dados])
(defrecord Calculadora [cache])

;; ===== DESAFIO 1: FUNÇÃO PURA =====

(defn calcular-fatorial [n]
  (if (= n 0) 1 (* n (calcular-fatorial (dec n)))))

;; ===== DESAFIO 2: FUNÇÃO DE CACHEAMENTO =====

(defn cachear-resultado [cache chave valor]
  (assoc cache chave valor))

(defn buscar-no-cache [cache chave]
  (get cache chave))

;; ===== DESAFIO 3: COMPONENTIZAR COMPORTAMENTO =====

(defn calcular-com-cache [calculadora n]
  (if (buscar-no-cache (:cache calculadora) n)
    (buscar-no-cache (:cache calculadora) n)
    (let [resultado (calcular-fatorial n)]
      (cachear-resultado (:cache calculadora) n resultado)
      resultado)))


;; ===== DADOS DE EXEMPLO =====

(def cache-inicial (->Cache {}))
(def calculadora (->Calculadora cache-inicial))

;; ===== FUNÇÃO PRINCIPAL =====

(defn executar-desafio []
  (println "=== 1. Calculando fatorial sem cache ===")
  (let [resultado (calcular-fatorial 5)]
    (println "Fatorial de 5:" resultado))

  (println "\n=== 2. Calculando com cache ===")
  (let [resultado1 (calcular-com-cache calculadora 5)
        resultado2 (calcular-com-cache calculadora 5)]
    (println "Primeira vez (calcula):" resultado1)
    (println "Segunda vez (usa cache):" resultado2))

  (println "\n=== 3. Testando cache diretamente ===")
  (let [cache-atualizado (cachear-resultado (:cache calculadora) "teste" "valor")]
    (println "Valor no cache:" (buscar-no-cache cache-atualizado "teste"))))

(defn -main []
  (executar-desafio)
  (println "\n====== Desafio Concluído ======"))
