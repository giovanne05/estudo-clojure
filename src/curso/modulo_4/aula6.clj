(ns curso.modulo-4.aula6)

(println "====== DESAFIO: Sistema de Gerenciamento de Estoque com Refs e Transações ======")

(def estoque (ref {:produto-1 {:nome "Notebook" :quantidade 10 :preco 2500.00}
                   :produto-2 {:nome "Mouse" :quantidade 50 :preco 45.00}
                   :produto-3 {:nome "Teclado" :quantidade 30 :preco 120.00}}))

(def historico-operacoes (ref []))

(defn adicionar-produto [id-produto nome quantidade preco]
  (dosync
   (alter estoque assoc id-produto {:nome nome :quantidade quantidade :preco preco})
   (alter historico-operacoes conj {:tipo :adicionar :produto id-produto :quantidade quantidade :preco preco})))

(defn remover-produto [id-produto]
  (dosync
   (alter estoque dissoc id-produto)
   (alter historico-operacoes conj {:tipo :remover :produto id-produto})))

(defn transferir-produto [produto-origem produto-destino quantidade]
  (dosync
   (let [produto-origem-dados (get @estoque produto-origem)
         produto-destino-dados (get @estoque produto-destino)]
     (if (and produto-origem-dados produto-destino-dados)
       (if (>= (:quantidade produto-origem-dados) quantidade)
         (do
           (alter estoque assoc produto-origem (update produto-origem-dados :quantidade - quantidade))
           (alter estoque assoc produto-destino (update produto-destino-dados :quantidade + quantidade))
           (alter historico-operacoes conj {:tipo :transferir
                                            :produto-origem produto-origem
                                            :produto-destino produto-destino
                                            :quantidade quantidade})
           (println (format "Transferência de %d unidades de %s para %s realizada"
                            quantidade produto-origem produto-destino)))
         (throw (ex-info "Quantidade insuficiente"
                         {:produto-origem produto-origem
                          :quantidade-disponivel (:quantidade produto-origem-dados)
                          :quantidade-solicitada quantidade})))
       (throw (ex-info "Produto não encontrado"
                       {:produto-origem produto-origem
                        :produto-destino produto-destino}))))))

(defn processar-pedido-assincrono [produto quantidade]
  (future
    (Thread/sleep 1000) ; Simulando processamento
    (dosync
     (let [produto-dados (get @estoque produto)]
       (if (and produto-dados (>= (:quantidade produto-dados) quantidade))
         (do
           (alter estoque assoc produto (update produto-dados :quantidade - quantidade))
           (alter historico-operacoes conj {:tipo :pedido-assincrono
                                            :produto produto
                                            :quantidade quantidade})
           (println (format "Pedido assíncrono processado: %d unidades de %s" quantidade produto)))
         (println (format "Pedido assíncrono falhou: produto %s não encontrado ou estoque insuficiente" produto)))))))

(defn aguardar-pedidos [futures]
  (println "Aguardando processamento de pedidos...")
  (doseq [fut futures]
    (let [resultado @fut] ; @fut aguarda o resultado do future
      (println "Future concluído:" resultado))))

(defn aplicar-desconto-geral [percentual]
  (println (format "Aplicando desconto de %d%% em todos os produtos..." percentual))
  (dosync
   (doseq [[id-produto dados] @estoque]
     (let [novo-preco (* (:preco dados) (/ (- 100 percentual) 100))]
       (alter estoque assoc id-produto (assoc dados :preco novo-preco))))
   (alter historico-operacoes conj {:tipo :desconto-geral :percentual percentual})))

(defn listar-produtos-estoque-baixo [limite]
  (println (format "Produtos com estoque abaixo de %d:" limite))
  (doseq [[id-produto dados] @estoque]
    (when (< (:quantidade dados) limite)
      (println (format "  %s: %s - %d unidades" id-produto (:nome dados) (:quantidade dados))))))

(defn calcular-valor-total-estoque []
  (let [valor-total (reduce + (for [[id-produto dados] @estoque]
                                (* (:quantidade dados) (:preco dados))))]
    (println (format "Valor total do estoque: R$ %.2f" valor-total))
    valor-total))

(defn encontrar-produto-mais-caro []
  (let [produto-mais-caro (apply max-key (comp :preco val) @estoque)]
    (println (format "Produto mais caro: %s - %s - R$ %.2f"
                     (key produto-mais-caro)
                     (:nome (val produto-mais-caro))
                     (:preco (val produto-mais-caro))))
    produto-mais-caro))

(defn mostrar-estado-estoque []
  (println "Estado atual do estoque:")
  (doseq [[id-produto dados] @estoque]
    (println (format "  %s: %s - %d unidades - R$ %.2f"
                     id-produto (:nome dados) (:quantidade dados) (:preco dados)))))

(defn mostrar-historico-recente [quantidade]
  (println "Histórico de operações recentes:")
  (doseq [operacao (take-last quantidade @historico-operacoes)]
    (case (:tipo operacao)
      :adicionar (println (format "  Adicionado: %s - %d unidades - R$ %.2f"
                                  (:produto operacao) (:quantidade operacao) (:preco operacao)))
      :remover (println (format "  Removido: %s" (:produto operacao)))
      :transferir (println (format "  Transferido: %d unidades de %s para %s"
                                   (:quantidade operacao) (:produto-origem operacao) (:produto-destino operacao)))
      :pedido-assincrono (println (format "  Pedido assíncrono: %d unidades de %s"
                                          (:quantidade operacao) (:produto operacao)))
      :desconto-geral (println (format "  Desconto geral: %d%%" (:percentual operacao))))))

(defn executar-desafio []
  (println "=== Estado inicial do estoque ===")
  (mostrar-estado-estoque)

  (println "\n=== 1. Adicionando novo produto ===")
  (adicionar-produto :produto-4 "Monitor" 15 800.00)
  (mostrar-estado-estoque)

  (println "\n=== 2. Realizando transferência ===")
  (transferir-produto :produto-1 :produto-2 5)
  (mostrar-estado-estoque)

  (println "\n=== 3. Processando pedidos assíncronos ===")
  (let [futures [(processar-pedido-assincrono :produto-1 2)
                 (processar-pedido-assincrono :produto-2 10)
                 (processar-pedido-assincrono :produto-3 5)]]
    (aguardar-pedidos futures))
  (mostrar-estado-estoque)

  (println "\n=== 4. Aplicando desconto geral ===")
  (aplicar-desconto-geral 10)
  (mostrar-estado-estoque)

  (println "\n=== 5. Listando produtos com estoque baixo ===")
  (listar-produtos-estoque-baixo 20)

  (println "\n=== 6. Calculando valor total do estoque ===")
  (calcular-valor-total-estoque)

  (println "\n=== 7. Encontrando produto mais caro ===")
  (encontrar-produto-mais-caro)

  (println "\n=== 8. Histórico de operações ===")
  (mostrar-historico-recente 10))

(defn -main []
  (executar-desafio)
  (println "\n====== Desafio Concluído ======"))
  
