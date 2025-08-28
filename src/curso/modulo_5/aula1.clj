(ns curso.modulo-5.aula1
  (:import
   (java.text SimpleDateFormat)
   (java.util Date)))

(println "====== DESAFIO: Sistema de Gerenciamento de Funcionários com Records e Protocols ======")

(defrecord Funcionario [id nome cargo salario data-contratacao departamento])

(defrecord Projeto [id nome descricao data-inicio data-fim responsavel])

(defn buscar-funcionario-por-id [funcionarios id]
  (if-let [funcionario (first (filter #(= (:id %) id) funcionarios))]
    funcionario
    nil))

(defn buscar-projeto-por-id [projetos id]
  (if-let [projeto (first (filter #(= (:id %) id) projetos))]
    projeto
    nil))

(defn criar-funcionario-threading [id nome cargo salario departamento]
  (Funcionario. id nome cargo salario (Date.) departamento))

(defn criar-projeto-threading [id nome descricao responsavel]
  (Projeto. id nome descricao (Date.) (Date.) responsavel))

(defn criar-funcionarios-em-lote [dados-funcionarios]
  (map (fn [dados]
         (criar-funcionario-threading
          (:id dados)
          (:nome dados)
          (:cargo dados)
          (:salario dados)
          (:departamento dados))) dados-funcionarios))

(defn criar-projetos-em-lote [dados-projetos]
  (map (fn [dados]
         (criar-projeto-threading
          (:id dados)
          (:nome dados)
          (:descricao dados)
          (:responsavel dados))) dados-projetos))

(defn formatar-data [data formato]
  (.format (SimpleDateFormat. formato) data))

(defn obter-data-atual []
  (Date.))

(defn listar-funcionarios [funcionarios]
  (doseq [funcionario funcionarios]
    (println (format "ID: %d - %s - %s - R$ %.2f"
                     (:id funcionario)
                     (:nome funcionario)
                     (:cargo funcionario)
                     (:salario funcionario)))))

(defn listar-projetos [projetos]
  (doseq [projeto projetos]
    (println (format "ID: %d - %s - %s"
                     (:id projeto)
                     (:nome projeto)
                     (:responsavel projeto)))))

(defn calcular-salario-medio [funcionarios]
  (let [total (reduce + (map :salario funcionarios))
        quantidade (count funcionarios)]
    (/ total quantidade)))

(def dados-funcionarios-exemplo
  [{:id 1 :nome "João Silva" :cargo "Desenvolvedor" :salario 5000.00 :departamento "TI"}
   {:id 2 :nome "Maria Santos" :cargo "Analista" :salario 4500.00 :departamento "RH"}
   {:id 3 :nome "Pedro Costa" :cargo "Gerente" :salario 8000.00 :departamento "TI"}])

(def dados-projetos-exemplo
  [{:id 1 :nome "Sistema Web" :descricao "Desenvolvimento de sistema web" :responsavel "João Silva"}
   {:id 2 :nome "App Mobile" :descricao "Aplicativo mobile" :responsavel "Maria Santos"}
   {:id 3 :nome "API REST" :descricao "API REST para integração" :responsavel "Pedro Costa"}])

(defn executar-desafio []
  (println "=== 1. Criando funcionários em lote ===")
  (let [funcionarios (criar-funcionarios-em-lote dados-funcionarios-exemplo)]
    (println "Funcionários criados:" (count funcionarios))
    (listar-funcionarios funcionarios))

  (println "\n=== 2. Criando projetos em lote ===")
  (let [projetos (criar-projetos-em-lote dados-projetos-exemplo)]
    (println "Projetos criados:" (count projetos))
    (listar-projetos projetos))

  (println "\n=== 3. Buscando funcionário por ID ===")
  (let [funcionario (buscar-funcionario-por-id dados-funcionarios-exemplo 1)]
    (if funcionario
      (println "Funcionário encontrado:" (:nome funcionario))
      (println "Funcionário não encontrado")))

  (println "\n=== 4. Buscando projeto por ID ===")
  (let [projeto (buscar-projeto-por-id dados-projetos-exemplo 1)]
    (if projeto
      (println "Projeto encontrado:" (:nome projeto))
      (println "Projeto não encontrado")))

  (println "\n=== 5. Criando funcionário com threading ===")
  (let [funcionario (criar-funcionario-threading 4 "Ana Oliveira" "Designer" 4000.00 "Marketing")]
    (println "Funcionário criado:" (:nome funcionario)))

  (println "\n=== 6. Criando projeto com threading ===")
  (let [projeto (criar-projeto-threading 4 "Projeto 4" "Descrição do projeto 4" "Ana Oliveira")]
    (println "Projeto criado:" (:nome projeto)))

  (println "\n=== 7. Formatando data atual ===")
  (let [data-atual (obter-data-atual)
        data-formatada (formatar-data data-atual "dd/MM/yyyy")]
    (println "Data atual formatada:" data-formatada))

  (println "\n=== 8. Listando funcionários ===")
  (listar-funcionarios dados-funcionarios-exemplo)

  (println "\n=== 9. Listando projetos ===")
  (listar-projetos dados-projetos-exemplo)

  (println "\n=== 10. Calculando salário médio ===")
  (let [salario-medio (calcular-salario-medio dados-funcionarios-exemplo)]
    (println (format "Salário médio: R$ %.2f" salario-medio))))

(defn -main []
  (executar-desafio)
  (println "\n====== Desafio Concluído ======"))
