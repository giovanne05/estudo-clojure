(ns curso.modulo-4.aula4)

(println "====== Desafio: Sistema de Processamento com Funções Avançadas ======")

(def dados [{:nome "João" :idade 25 :cidade "SP" :salario 3000}
            {:nome "Maria" :idade 30 :cidade "RJ" :salario 4500}
            {:nome "Pedro" :idade 22 :cidade "SP" :salario 2800}
            {:nome "Ana" :idade 28 :cidade "MG" :salario 3800}
            {:nome "Carlos" :idade 35 :cidade "SP" :salario 5200}])

(defn calcular-bonus [salario]
  (* salario 0.1))

(defn processar-dados [lista-dados]
  (mapv (fn [pessoa]
          (assoc pessoa :bonus (calcular-bonus (:salario pessoa))))
        lista-dados))

(defn extrair-informacoes [pessoa]
  (let [nome (:nome pessoa)
        idade (:idade pessoa)
        cidade (:cidade pessoa)]
    {:nome-completo (str nome " - " cidade)
     :faixa-etaria (if (> idade 30) "adulto" "jovem")
     :localizacao cidade}))

(defn criar-filtro [campo valor]
  (partial filter #(= (campo %) valor)))

(defn iterar-clientes [clientes]
  (doseq [cliente clientes]
    (println "Processando:" (:nome cliente))
    (println "  Cidade:" (:cidade cliente))
    (println "  Salário:" (:salario cliente))
    (println "---")))

(defn executar-tarefas [quantidade]
  (dotimes [i quantidade]
    (println "Executando tarefa" (inc i) "de" quantidade)))

(defn sistema-completo []
  (println "=== Processando dados com mapv ===")
  (let [dados-processados (processar-dados dados)]
    (println "Dados processados:" dados-processados))

  (println "\n=== Extraindo informações ===")
  (let [informacoes (map extrair-informacoes dados)]
    (println "Informações extraídas:" informacoes))

  (println "\n=== Usando filtro com partial ===")
  (let [filtro-sp (criar-filtro :cidade "SP")
        pessoas-sp (filtro-sp dados)]
    (println "Pessoas de SP:" pessoas-sp))

  (println "\n=== Iterando com doseq ===")
  (iterar-clientes dados)

  (println "=== Executando tarefas com dotimes ===")
  (executar-tarefas 3))

(defn -main []
  (sistema-completo)
  (println "====== Sistema Finalizado ======"))
