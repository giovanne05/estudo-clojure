(ns curso.modulo-3.aula3
  (:require
   [schema.core :as s]))

(defn validar-numero [valor]
  (s/validate (s/pred #(> % 0) 'numero-positivo) valor))

(s/defschema Endereco {:rua s/Str
                       :numero (s/constrained s/Int validar-numero)
                       :complemento (s/maybe s/Str)
                       :cidade s/Str})

(s/defschema Pessoa {:nome s/Str
                     :idade (s/constrained s/Int validar-numero)
                     :endereco Endereco
                     :telefone (s/maybe s/Str)})

(println "====== Desafio: Sistema de Validação Avançada com Schemas Compostos ======")

(def pessoa-valida {:nome "João"
                    :idade 25
                    :endereco {:rua "Rua das Flores"
                               :numero 123
                               :complemento "Apto 4B"
                               :cidade "São Paulo"}
                    :telefone "1234567890"})

(println "Pessoa válida:" (s/validate Pessoa pessoa-valida))

(def pessoa-invalida {:nome "João"
                      :idade -5
                      :endereco {:rua "Rua das Flores"
                                 :numero 0
                                 :cidade "São Paulo"}})

(try
  (println "Pessoa inválida:" (s/validate Pessoa pessoa-invalida))
  (catch Exception e
    (println "Erro de validação:" (.getMessage e))))
