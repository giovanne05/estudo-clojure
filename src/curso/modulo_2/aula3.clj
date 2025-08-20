(ns curso.modulo-2.aula3)

(println "====== Desafio: Sistema de Banco em Memória ======")

(def contas {:contas {:conta1 {:numero "001" :titular "João" :saldo 1000 :tipo "corrente"}
                      :conta2 {:numero "002" :titular "Maria" :saldo 2500 :tipo "poupanca"}
                      :conta3 {:numero "003" :titular "Pedro" :saldo 500 :tipo "corrente"}}})

(defn adicionar-nova-conta [contas nova-conta]
  (let [numero-conta (:numero nova-conta)
        conta-existente (get contas numero-conta)]
    (if conta-existente
      (println "Conta já existe")
      (assoc contas numero-conta nova-conta))))

(defn buscar-conta-por-numero [contas numero-conta]
  (->> (:contas contas)
       (vals)
       (filter #(= (:numero %) numero-conta))
       (first)))

(defn agrupar-contas-por-tipo [contas]
  (group-by :tipo (vals (:contas contas))))

(defn agrupar-contas-por-saldo [contas] (group-by :saldo contas))

(println "Contas por tipo:" (agrupar-contas-por-tipo contas))
(println "Contas por saldo:" (agrupar-contas-por-saldo contas))
(println "Adicionar nova conta:" (adicionar-nova-conta (:contas contas) {:numero "004" :titular "Ana" :saldo 1500 :tipo "corrente"}))
(println "Buscar conta por número:" (buscar-conta-por-numero contas "001"))


