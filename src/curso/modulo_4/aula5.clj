(ns curso.modulo-4.aula5)

(println "====== Desafio: Sistema de Transferência de Filas com Delegates ======")

(def filas (atom {:normal []
                  :preferencial []
                  :urgente []}))

(defn criar-filas []
  (reset! filas {:normal []
                 :preferencial []
                 :urgente []}))

(defn transferir-pessoa [filas origem destino pessoa]
  (let [fila-origem (get @filas origem)
        fila-destino (get @filas destino)]
    (if (some #(= (:id %) (:id pessoa)) fila-origem)
      (swap! filas (fn [estado]
                     (-> estado
                         (update origem #(remove (fn [p] (= (:id p) (:id pessoa))) %))
                         (update destino conj pessoa))))
      (throw (ex-info "Pessoa não encontrada na fila de origem" {:pessoa pessoa :origem origem})))))

(defn criar-delegate [filas]
  {:adicionar (fn [tipo pessoa]
                (swap! filas update tipo conj pessoa))
   :remover (fn [tipo]
              (swap! filas update tipo pop))
   :listar (fn [tipo]
             (get @filas tipo))
   :tamanho (fn [tipo]
              (count (get @filas tipo)))})

(defn isolar-mutabilidade [operacao]
  (fn [& args]
    (let [resultado (apply operacao args)]
      (println "Operação mutável executada com sucesso")
      resultado)))

(defn usar-juxt [filas]
  (juxt (fn [pessoa] (count (:normal @filas)))
        (fn [pessoa] (count (:preferencial @filas)))
        (fn [pessoa] (count (:urgente @filas)))
        (fn [pessoa] (str "Pessoa: " (:nome pessoa)))))

(defn sistema-transferencia []
  (println "=== Criando filas ===")
  (criar-filas)
  (println "Filas criadas:" @filas)

  (println "\n=== Usando delegate para adicionar pessoas ===")
  (let [operacoes (criar-delegate filas)]
    ((:adicionar operacoes) :normal {:id 1 :nome "João"})
    ((:adicionar operacoes) :preferencial {:id 2 :nome "Maria"})
    ((:adicionar operacoes) :urgente {:id 3 :nome "Pedro"}))
  (println "Estado após adições:" @filas)

  (println "\n=== Transferindo pessoa ===")
  (transferir-pessoa filas :normal :preferencial {:id 1 :nome "João"})
  (println "Estado após transferência:" @filas)

  (println "\n=== Usando juxt para análise ===")
  (let [analise (usar-juxt filas)
        resultado (analise {:id 4 :nome "Ana"})]
    (println "Análise das filas:" resultado)
    (println "  Normal:" (first resultado))
    (println "  Preferencial:" (second resultado))
    (println "  Urgente:" (nth resultado 2))
    (println "  Info:" (nth resultado 3)))

  (println "\n=== Usando delegate para listar ===")
  (let [operacoes (criar-delegate filas)]
    (println "Fila normal:" ((:listar operacoes) :normal))
    (println "Fila preferencial:" ((:listar operacoes) :preferencial))
    (println "Fila urgente:" ((:listar operacoes) :urgente))))

(defn -main []
  (sistema-transferencia)
  (println "====== Sistema Finalizado ======"))
