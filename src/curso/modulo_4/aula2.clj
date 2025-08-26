(ns curso.modulo-4.aula2)

(println "====== Desafio:Sistema de Fila com Threads e Limites ======")

(def limite 3)

(def fila-banco (atom []))

(defn adicionar-com-cliente [cliente]
  (if (< (count @fila-banco) limite)
    (swap! fila-banco conj cliente)
    (throw (ex-info "Fila cheia" {:fila @fila-banco :limite limite}))))

(defn remover-cliente []
  (if (> (count @fila-banco) 0)
    (swap! fila-banco pop)
    (throw (ex-info "Fila vazia" {:fila @fila-banco}))))

(defn mostrar-estado-fila []
  (println "Quantidade do banco:" (count @fila-banco))
  (println "Limite de clientes na fila:" limite))

(defn simular-cliente [cliente operacao]
  (mostrar-estado-fila)
  (operacao cliente))

(defn -main []
  (println "====== Iniciando Teste com Threads ======")

  (let [thread1 (Thread. #(simular-cliente {:id 1 :nome "João" :tipo "pessoa física"} adicionar-com-cliente))
        thread2 (Thread. #(simular-cliente {:id 2 :nome "Maria" :tipo "pessoa física"} adicionar-com-cliente))
        thread3 (Thread. #(simular-cliente {:id 3 :nome "Empresa ABC" :tipo "pessoa jurídica"} adicionar-com-cliente))
        thread4 (Thread. #(simular-cliente {:id 4 :nome "Empresa XYZ" :tipo "pessoa jurídica"} adicionar-com-cliente))
        thread5 (Thread. #(remover-cliente))]

    (println "Iniciando threads...")
    (.start thread1)
    (.start thread2)
    (.start thread3)
    (.start thread4)
    (.start thread5)

    (println "Aguardando threads terminarem...")
    (.join thread1)
    (.join thread2)
    (.join thread3)
    (.join thread4)
    (.join thread5)

    (println "Estado final da fila:")
    (mostrar-estado-fila)
    (println "====== Sistema de Fila com Threads Finalizado ======")))
