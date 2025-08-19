(ns curso.modulo-1.aula5)

(println "====== Desafio: Sistema de Cadastro de Alunos ======")

(def alunos {:aluno1 {:nome "João" :idade 20 :curso "Matemática"}
             :aluno2 {:nome "Maria" :idade 22 :curso "Física"}})

(defn quantidade-alunos [alunos] (count alunos))

(defn listar-chave-alunos [alunos] (keys alunos))

(defn adicionar-aluno [id nome idade curso alunos]
  (assoc alunos id {:nome nome :idade idade :curso curso}))

(defn buscar-aluno [id alunos] (get alunos id))

(defn sistemas-cadastro-alunos [alunos]
  (println "\n=== Chave Alunos ===")
  (-> alunos
      (listar-chave-alunos)
      (println))
  (println "\n=== Quantidade de alunos ===")
  (-> alunos
      (quantidade-alunos)
      (println))
  (println "\n=== Adicionar aluno ===")
  (let [alunos-atualizados (adicionar-aluno :aluno3 "Leticia" 20 "Matemática" alunos)]
    (println alunos-atualizados)

    (println "\n=== Nova quantidade de alunos ===")
    (-> alunos-atualizados
        (quantidade-alunos)
        (println))

    (println "\n=== Buscar aluno 3 ===")
    (println (buscar-aluno :aluno3 alunos-atualizados))))

(sistemas-cadastro-alunos alunos)

