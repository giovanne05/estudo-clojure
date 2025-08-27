(ns curso.modulo-4.aula1)

(def fila-banco (atom []))

(defn adicionar-cliente [cliente]
  (swap! fila-banco conj cliente))

(defn atender-cliente []
  (let [cliente-atendido (peek @fila-banco)]
    (swap! fila-banco pop)
    cliente-atendido))

(defn ver-proximo-cliente []
  (peek @fila-banco))

(defn mostrar-fila []
  (println "Fila atual:" @fila-banco))

(println "====== Desafio: Sistema de Fila de Banco ======")

(adicionar-cliente {:id 1 :nome "João" :tipo "pessoa física" :prioridade "normal"})
(adicionar-cliente {:id 2 :nome "Maria" :tipo "pessoa física" :prioridade "preferencial"})
(adicionar-cliente {:id 3 :nome "Empresa ABC" :tipo "pessoa jurídica" :prioridade "normal"})

(mostrar-fila)

(println "Próximo cliente:" (ver-proximo-cliente))
(println "Atendendo cliente:" (atender-cliente))

(mostrar-fila)

(adicionar-cliente {:id 4 :nome "Pedro" :tipo "pessoa física" :prioridade "normal"})

(mostrar-fila)

(defn -main []
  (println "====== Sistema de Fila de Banco Finalizado ======"))

