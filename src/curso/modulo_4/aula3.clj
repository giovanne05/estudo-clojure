(ns curso.modulo-4.aula3)

(println "====== Desafio: Sistema de Fila com Atoms e Shadowing ======")

(def sistema-fila (atom {:fila [] :limite 3}))

(defn criar-sistema-fila []
  (reset! sistema-fila {:fila [] :limite 3}))

(defn adicionar-cliente [cliente]
  (let [estado-atual (deref sistema-fila)]
    (if (< (count (:fila estado-atual)) (:limite estado-atual))
      (swap! sistema-fila update :fila conj cliente)
      (throw (ex-info "Fila cheia" {:cliente cliente :limite (:limite estado-atual)})))))

(defn remover-cliente []
  (let [estado-atual (deref sistema-fila)]
    (if (seq (:fila estado-atual))
      (swap! sistema-fila update :fila pop)
      (throw (ex-info "Fila vazia" {:estado estado-atual})))))

(defn mostrar-estado []
  (let [estado (deref sistema-fila)]
    (println "Fila:" (:fila estado))
    (println "Tamanho:" (count (:fila estado)) "/" (:limite estado))))

(defn demonstrar-concorrencia []
  (println "=== Demonstração de Concorrência com swap! ===")
  (criar-sistema-fila)

  (let [thread1 (Thread. #(dotimes [_ 5] (try (adicionar-cliente {:id (rand-int 100) :nome "Cliente"}) (catch Exception _))))
        thread2 (Thread. #(dotimes [_ 3] (try (remover-cliente) (catch Exception _))))]
    (.start thread1)
    (.start thread2)
    (.join thread1)
    (.join thread2)
    (mostrar-estado)))

(defn -main []
  (println "=== Testando Sistema ===")
  (criar-sistema-fila)
  (mostrar-estado)

  (adicionar-cliente {:id 1 :nome "João"})
  (adicionar-cliente {:id 2 :nome "Maria"})
  (mostrar-estado)

  (remover-cliente)
  (mostrar-estado)

  (demonstrar-concorrencia)
  (println "====== Sistema Finalizado ======")) 
