# SISTEMA DE GESTÃO DE PEDIDOS

---

## INSTRUÇÕES PARA COMPILAR E EXECUTAR

1. Abra o terminal na pasta do projeto "projeto-poo":
   - Navegue até a pasta do projeto.
   - Clique com o botão direito → "Abrir no Terminal" ou digite "cmd" na barra de endereços.

2. Compile todos os arquivos Java:
   - javac -d out Main.java model\*.java enums\*.java exceptions\*.java repositorio\*.java repositorio\impl\*.java servico\*.java fila\*.java processador\*.java ui\*.java

3. Execute o programa:
   - java -cp out Main

---

## FUNDAMENTOS DE POO APLICADOS

• Classes e Objetos: Cliente, Produto, Pedido, ItemPedido
• Construtores: Fábricas estáticas (Cliente.criar, Produto.criar)
• Encapsulamento: Atributos private final, sem setters
• Herança: Entidade → Cliente, Produto, Pedido
• Polimorfismo: Repositorio<T> genérico
• Classe Abstrata: Entidade
• Interface: Repositorio<T>

---

## PRINCÍPIOS SOLID

• SRP: Cada classe tem uma única responsabilidade
  - ServicoCliente → cadastra clientes
  - ProcessadorPedidos → processa fila de pedidos

• OCP: Sistema extensível via interfaces (Repositorio)

• LSP: Subclasses substituem sem alterar o comportamento esperado

• ISP: Interface Repositorio é pequena e específica

• DIP: As classes dependem de abstrações, não de implementações concretas

---

## TRATAMENTO DE EXCEÇÕES

• Exceção customizada: ValidacaoException
• Validações obrigatórias:
  - Nome do cliente não pode estar vazio
  - E-mail deve conter '@'
  - Preço do produto deve ser maior que 0
  - Categoria é obrigatória
  - Pedido deve conter pelo menos 1 item

---

## PROCESSAMENTO ASSÍNCRONO E CONCORRÊNCIA

• FilaPedidos: Implementada como thread-safe usando synchronized + wait()/notifyAll()
• ProcessadorPedidos: Executa como thread daemon que:
  1. Retira o pedido da fila (status FILA)
  2. Altera o status para PROCESSANDO
  3. Simula o processamento com Thread.sleep (3–5 segundos)
  4. Define o status como FINALIZADO

• O menu permanece totalmente responsivo durante o processamento
• Sincronização garantida no acesso ao status do Pedido

---

## OUTROS DETALHES TÉCNICOS

- Imutabilidade: Cliente, Produto e ItemPedido possuem atributos final
- Geração de IDs thread-safe: AtomicLong no repositório
- Enums: StatusPedido e CategoriaProduto
- Persistência: Em memória (HashMap + List)
- Bônus (arquivo JSON/CSV): Não implementado (opcional)

---

## FUNÇÕES DOS INTEGRANTES

- Bernardo Küster Ragugnetti: 
- Pedro Henrique Moreira: 
- Guilherme Dos Anjos Barbosa: 
