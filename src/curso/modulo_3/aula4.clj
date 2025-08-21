(ns curso.modulo-3.aula4
  (:require
   [schema.core :as s]))

(s/defschema Configuracao {:nome s/Str
                           (s/optional-key :versao) s/Str
                           (s/optional-key :ambiente) s/Str
                           :configuracoes {s/Keyword s/Any}})

(s/defschema Usuario {:id s/Int
                      :nome s/Str
                      (s/optional-key :email) s/Str
                      :propriedades {s/Keyword s/Any}})

(defn validar-configuracao-flexivel [dados]
  (try
    (s/validate Configuracao dados)
    (println "Configuração válida:" dados)
    (catch Exception e
      (println "Configuração inválida:" (.getMessage e)))))

(println "====== Sistema de Configuração Dinâmica com Schemas Flexíveis ======")

(def config-valida
  {:nome "SistemaX"
   :versao "1.0"
   :ambiente "producao"
   :configuracoes {:porta 8080 :host "localhost" :debug true}})

(def config-sem-versao
  {:nome "SistemaY"
   :ambiente "homologacao"
   :configuracoes {:timeout 30 :retry 3}})

(def config-sem-ambiente
  {:nome "SistemaZ"
   :versao "2.1"
   :configuracoes {:cache true :max-connections 100}})

(def config-apenas-obrigatorio
  {:nome "SistemaW"
   :configuracoes {:simple true}})

(def config-invalida
  {:nome 123
   :versao 1.2
   :configuracoes 42})

(validar-configuracao-flexivel config-valida)
(validar-configuracao-flexivel config-sem-versao)
(validar-configuracao-flexivel config-sem-ambiente)
(validar-configuracao-flexivel config-apenas-obrigatorio)
(validar-configuracao-flexivel config-invalida)

(defn validar-usuario-flexivel [dados]
  (try
    (s/validate Usuario dados)
    (println "Usuário válido:" dados)
    (catch Exception e
      (println "Usuário inválido:" (.getMessage e)))))

(def usuario-valido
  {:id 1
   :nome "João"
   :email "joao@email.com"
   :propriedades {:cargo "Desenvolvedor" :departamento "TI" :ativo true}})

(def usuario-sem-email
  {:id 2
   :nome "Maria"
   :propriedades {:cargo "Designer" :projetos ["A", "B"]}})

(validar-usuario-flexivel usuario-valido)
(validar-usuario-flexivel usuario-sem-email)
