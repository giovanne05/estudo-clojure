(ns curso.modulo-5.aula2
  (:import
   (java.text SimpleDateFormat)
   (java.util Date)))

(println "====== DESAFIO: Sistema de Gerenciamento de Eventos com Protocols e Extensões ======")

;; ===== ESTRUTURA BÁSICA FORNECIDA =====

(defrecord Evento [id nome descricao data-inicio data-fim local])
(defrecord Usuario [id nome email tipo])
(defrecord Organizador [id nome empresa especialidade])

;; ===== DESAFIO 1: PROTOCOLO DATEABLE =====
;; Define um protocolo para trabalhar com datas

(defprotocol Dateable
  (get-data-inicio [this])
  (get-data-fim [this])
  (get-duracao-dias [this])
  (is-acontecendo-hoje? [this])
  (formatar-datas [this formato]))

;; ===== DESAFIO 2: PROTOCOLO NOTIFICAVEL =====
;; Define um protocolo para notificações

(defprotocol Notificavel
  (enviar-notificacao [this mensagem])
  (get-contato [this])
  (pode-receber-notificacao? [this]))

;; ===== DESAFIO 3: IMPLEMENTAR FUNÇÕES COM SOME =====

(defn verificar-evento-por-nome [eventos nome]
  (some #(when (= (:nome %) nome) %) eventos))

(defn verificar-usuario-por-email [usuarios email]
  (some #(when (= (:email %) email) %) usuarios))

(defn verificar-organizador-por-especialidade [organizadores especialidade]
  (some #(when (= (:especialidade %) especialidade) %) organizadores))

;; ===== DESAFIO 4: EXTENDER TIPOS COM PROTOCOLOS =====

;; Estendendo o tipo Evento com o protocolo Dateable
(extend-type Evento
  Dateable
  (get-data-inicio [this] (:data-inicio this))
  (get-data-fim [this] (:data-fim this))
  (get-duracao-dias [this]
    (let [inicio (:data-inicio this)
          fim (:data-fim this)]
      (int (/ (- (.getTime fim) (.getTime inicio)) (* 1000 60 60 24)))))
  (is-acontecendo-hoje? [this]
    (let [hoje (Date.)
          inicio (:data-inicio this)
          fim (:data-fim this)]
      (and (<= (.getTime inicio) (.getTime hoje))
           (>= (.getTime fim) (.getTime hoje)))))
  (formatar-datas [this formato]
    (let [formatter (SimpleDateFormat. formato)]
      (str "Início: " (.format formatter (:data-inicio this))
           " | Fim: " (.format formatter (:data-fim this))))))

;; Estendendo o tipo Usuario com o protocolo Notificavel
(extend-type Usuario
  Notificavel
  (enviar-notificacao [this mensagem]
    (println (format "Enviando notificação para %s: %s" (:nome this) mensagem)))
  (get-contato [this] (:email this))
  (pode-receber-notificacao? [this] true))

;; Estendendo o tipo Organizador com ambos os protocolos
(extend-type Organizador
  Dateable
  (get-data-inicio [this] (Date.)) ; Organizadores não têm data específica, retorna data atual
  (get-data-fim [this] (Date.)) ; Organizadores não têm data específica, retorna data atual
  (get-duracao-dias [this] 0) ; Organizadores não têm duração específica
  (is-acontecendo-hoje? [this] true) ; Organizadores sempre estão ativos
  (formatar-datas [this formato]
    (let [formatter (SimpleDateFormat. formato)
          hoje (Date.)]
      (str "Ativo desde: " (.format formatter hoje))))

  Notificavel
  (enviar-notificacao [this mensagem]
    (println (format "Enviando notificação para organizador %s (%s): %s"
                     (:nome this) (:empresa this) mensagem)))
  (get-contato [this] (:nome this))
  (pode-receber-notificacao? [this] true))

;; ===== DESAFIO 5: FUNÇÕES AUXILIARES =====

(defn obter-data-atual []
  (identity (Date.)))

(defn listar-eventos [eventos]
  (doseq [evento eventos]
    (println (format "ID: %d - %s - %s"
                     (:id evento) (:nome evento) (:local evento)))))

(defn listar-usuarios [usuarios]
  (doseq [usuario usuarios]
    (println (format "ID: %d - %s (%s) - %s"
                     (:id usuario) (:nome usuario) (:tipo usuario) (:email usuario)))))

(defn listar-organizadores [organizadores]
  (doseq [organizador organizadores]
    (println (format "ID: %d - %s (%s) - %s"
                     (:id organizador) (:nome organizador) (:empresa organizador) (:especialidade organizador)))))

;; ===== DADOS DE EXEMPLO =====

(def eventos-exemplo
  [(->Evento 1 "Conferência Tech" "Conferência de tecnologia" (Date.) (Date.) "Centro de Eventos")
   (->Evento 2 "Workshop Clojure" "Workshop de Clojure" (Date.) (Date.) "Sala de Treinamento")
   (->Evento 3 "Meetup Dev" "Meetup de desenvolvedores" (Date.) (Date.) "Coworking")])

(def usuarios-exemplo
  [(->Usuario 1 "João Silva" "joao@email.com" :participante)
   (->Usuario 2 "Maria Santos" "maria@email.com" :palestrante)
   (->Usuario 3 "Pedro Costa" "pedro@email.com" :organizador)])

(def organizadores-exemplo
  [(->Organizador 1 "Tech Events" "Empresa de eventos" "Tecnologia")
   (->Organizador 2 "Dev Community" "Comunidade" "Desenvolvimento")
   (->Organizador 3 "Innovation Hub" "Hub de inovação" "Inovação")])

;; ===== FUNÇÃO PRINCIPAL PARA TESTAR =====

(defn executar-desafio []
  (println "=== 1. Verificando eventos por nome ===")
  (let [resultado (verificar-evento-por-nome eventos-exemplo "Conferência Tech")]
    (println "Evento encontrado:" resultado))

  (println "\n=== 2. Verificando usuário por email ===")
  (let [resultado (verificar-usuario-por-email usuarios-exemplo "joao@email.com")]
    (println "Usuário encontrado:" resultado))

  (println "\n=== 3. Verificando organizador por especialidade ===")
  (let [resultado (verificar-organizador-por-especialidade organizadores-exemplo "Tecnologia")]
    (println "Organizador encontrado:" resultado))

  (println "\n=== 4. Testando protocolo Dateable com eventos ===")
  (let [evento (first eventos-exemplo)]
    (println "Data início:" (get-data-inicio evento))
    (println "Data fim:" (get-data-fim evento))
    (println "Duração em dias:" (get-duracao-dias evento))
    (println "Acontecendo hoje:" (is-acontecendo-hoje? evento))
    (println "Datas formatadas:" (formatar-datas evento "dd/MM/yyyy")))

  (println "\n=== 5. Testando protocolo Notificavel com usuários ===")
  (let [usuario (first usuarios-exemplo)]
    (println "Contato:" (get-contato usuario))
    (println "Pode receber notificação:" (pode-receber-notificacao? usuario))
    (println "Enviando notificação:" (enviar-notificacao usuario "Teste de notificação")))

  (println "\n=== 6. Testando protocolos com organizadores ===")
  (let [organizador (first organizadores-exemplo)]
    (println "Data início:" (get-data-inicio organizador))
    (println "Contato:" (get-contato organizador))
    (println "Enviando notificação:" (enviar-notificacao organizador "Notificação para organizador")))

  (println "\n=== 7. Listando eventos ===")
  (listar-eventos eventos-exemplo)

  (println "\n=== 8. Listando usuários ===")
  (listar-usuarios usuarios-exemplo)

  (println "\n=== 9. Listando organizadores ===")
  (listar-organizadores organizadores-exemplo)

  (println "\n=== 10. Testando identity ===")
  (let [data-atual (obter-data-atual)]
    (println "Data atual (identity):" data-atual)))

(defn -main []
  (executar-desafio)
  (println "\n====== Desafio Concluído ======"))
