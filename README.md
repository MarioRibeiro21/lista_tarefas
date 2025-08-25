# lista_tarefas

## Requisitos Atendidos
### Base de dados: 
tabela tarefas com campos: id (PK), nome (único), custo ,
data_limite , ordem (inteiro, não repetido, usado na ordenação de apresentação).
### Lista de Tarefas: 
listagem ordenada por ordem , exibe todos os campos exceto ordem ;
destaque visual para custo ≥ R$ 1.000,00; botões de Editar e Excluir; botão Incluir ao final.
### Excluir:
confirmação Sim/Não.
### Editar: 
altera apenas nome , custo , data_limite , impedindo nomes duplicados.
### Incluir: 
cria nova tarefa; sistema atribui ordem como último (max+1); impede nomes
duplicados.
### Reordenação:
por drag‑and‑drop e por botões subir/descer (primeira não sobe, última não
desce).
