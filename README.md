=
       - README - SISTEMA DE GESTÃO DE PEDIDOS
       - PROJETO FINAL - PROGRAMAÇÃO ORIENTADA A OBJETOS
       - ALUNOS: Bernardo Küster Ragugnetti, Pedro Henrique Moreira e Guilherme Dos Anjos Barbosa
=

--------------------------------------------------------------------------------
1. INSTRUÇÕES PARA COMPILAR E EXECUTAR
--------------------------------------------------------------------------------

1. Abra o CMD na pasta "projeto-poo":
   - Navegue até a pasta do projeto
   - Clique com botão direito → "Abrir no Terminal" ou digite "cmd" na barra de endereços

2. Compile todos os arquivos:
   javac -d out Main.java model\*.java enums\*.java exceptions\*.java repositorio\*.java repositorio\impl\*.java servico\*.java fila\*.java processador\*.java ui\*.java

3. Execute o programa:
   java -cp out Main

--------------------------------------------------------------------------------
2. ESTRUTURA DE PASTAS DO PROJETO
--------------------------------------------------------------------------------

projeto-poo/
├── Main.java
├── model/
│   ├── Entidade.java
│   ├── Cliente.java
│   ├── Produto.java
│   ├── ItemPedido.java
│   └── Pedido.java
├── enums/
│   ├── CategoriaProduto.java
│   └── StatusPedido.java
├── exceptions/
│   └── ValidacaoException.java
├── repositorio/
│   ├── Repositorio.java
│   └── impl/
│       └── RepositorioEmMemoria.java
├── servico/
│   ├── ServicoCliente.java
│   ├── ServicoProduto.java
│   └── ServicoPedido.java
├── fila/
│   └── FilaPedidos.java
├── processador/
│   └── ProcessadorPedidos.java
├── ui/
│   └── Menu.java
└── out/ (gerado automaticamente ao compilar)

--------------------------------------------------------------------------------
3. FUNDAMENTOS DE POO APLICADOS 
--------------------------------------------------------------------------------

• Classes e Objetos: Cliente, Produto, Pedido, ItemPedido
• Construtores: Fábricas estáticas (Cliente.criar, Produto.criar)
• Encapsulamento: Atributos private final, sem setters
• Herança: Entidade → Cliente, Produto, Pedido
• Polimorfismo: Repositorio<T> genérico
• Classes Abstratas: Entidade
• Interfaces: Repositorio<T>

--------------------------------------------------------------------------------
4. PRINCÍPIOS SOLID 
--------------------------------------------------------------------------------

• SRP: Cada classe tem uma única responsabilidade
  - ServicoCliente → só cadastra clientes
  - ProcessadorPedidos → só processa fila

• OCP: Extensível via interfaces (Repositorio)

• LSP: Subclasses substituem sem quebrar o comportamento

• ISP: Interface Repositorio pequena e focada

• DIP: Dependência de abstrações (não de implementações concretas)

--------------------------------------------------------------------------------
5. TRATAMENTO DE EXCEÇÕES
--------------------------------------------------------------------------------

• Exceção customizada: ValidacaoException
• Validações obrigatórias:
  - Nome do cliente não vazio
  - E-mail com @
  - Preço do produto > 0
  - Categoria obrigatória
  - Pelo menos 1 item no pedido

--------------------------------------------------------------------------------
6. PROCESSAMENTO ASSÍNCRONO E CONCORRÊNCIA
--------------------------------------------------------------------------------

• FilaPedidos: Fila thread-safe com synchronized + wait()/notifyAll()
• ProcessadorPedidos: Thread daemon que:
  1. Retira pedido da fila (status FILA)
  2. Muda status para PROCESSANDO
  3. Simula processamento com Thread.sleep (3-5 segundos)
  4. Muda status para FINALIZADO
• Menu permanece 100% responsivo durante o processamento
• Sincronização no acesso ao status do Pedido

--------------------------------------------------------------------------------
7. OUTROS DETALHES TÉCNICOS
--------------------------------------------------------------------------------

• Imutabilidade: Cliente, Produto, ItemPedido (atributos final)
• Geração de ID thread-safe: AtomicLong no repositório
• Enums: StatusPedido e CategoriaProduto
• Persistência: Em memória (HashMap + List)
• Bônus (arquivo JSON/CSV): Não implementado (opcional)

--------------------------------------------------------------------------------
8. FUNÇÕES DOS INTEGRANTES 
--------------------------------------------------------------------------------

• Bernardo Küster Ragugnetti:
• Pedro Henrique Moreira:
• Guilherme Dos Anjos Barbosa:

================================================================================
