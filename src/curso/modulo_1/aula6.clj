(ns curso.modulo-1.aula6
  (:require [curso.mock_database :as db]))

(println "====== Desafio: Sistema de Análise de Vendas ======")

(defn extrair-info-venda [{:keys [produto preco quantidade vendedor]}]
  {:produto produto :valor-total (* preco quantidade) :vendedor vendedor})

(defn vendas-caras [vendas]
  (->> vendas
       (filter #(> (:preco %) 100))
       (map extrair-info-venda)))

(defn total-por-vendedor [vendas]
  (->> vendas
       (map #(hash-map (:vendedor %) (* (:preco %) (:quantidade %))))
       (reduce (fn [acc venda]
                 (let [[vendedor valor] (first venda)]
                   (assoc acc vendedor (+ (get acc vendedor 0) valor)))) {})))

(defn processar-vendas [vendas]
  (let [vendas-filtradas (vendas-caras vendas)
        totais (total-por-vendedor vendas)]

    (println "\n=== Vendas com preço > R$ 100 ===")
    (doseq [venda vendas-filtradas]
      (println (str (:produto venda) " - R$ " (:valor-total venda) " - " (:vendedor venda))))

    (println "\n=== Total por vendedor ===")
    (doseq [[vendedor total] totais]
      (println (str vendedor ": R$ " total)))

    (println "\n=== Estatísticas ===")
    (println (str "Total de vendas: " (count vendas)))
    (println (str "Vendas caras: " (count vendas-filtradas)))))

(processar-vendas db/vendas)
