**Multiplayer Bingo Game**

Este projeto em Java é um jogo de bingo multiplayer desenvolvido 
como desafio proposto pelo Professor Rocha do curso de Java da Ada Tech e Núclea. 
Ele oferece opções para sorteio automático aleatório ou manual, 
exibindo estatísticas detalhadas ao final do jogo.(Por uma questão de fé . Não 
participo de jogos de apostas . Mas não tenho nada contra quem o faz .Contudo 
na minha formação surgiu um desafio de criar um programa de um bingo, seguindo algumas normas .Tentei fazer um:)

## Etapas do Jogo

### 1. Apresentação e Jogadores
- Boas-vindas e opções de comando.
- Design visual aprimorado com o nome do jogo.
- Suporte para o modo multiplayer automático.

### 2. Cartelas
- Geração de cartelas RANDOM ou MANUAL.
- No modo MANUAL, jogadores preenchem suas cartelas.
- Formato de input MANUAL: "1,2,3,4,5-6,7,8,9,1-2,3,4,5,6".

### 3. Números Sorteados
- Opções de comando: RANDOM ou MANUAL.
- Input MANUAL via Scanner: "1,2,3,4,5".
- Exibição de ranking dos top 3 jogadores a cada rodada.
- Solicitação de tecla para continuar via Scanner; X aborta o jogo.

### 4. Fim do Jogo
- Jogadores têm array indicando números acertados.
- Bingo quando todos os números têm valor 1.
- Exibição de ranking geral e estatísticas ao final.

### 5. Regras Gerais
- Utilização de arrays/matriz em vez de classes derivadas de Collections.
- Modo manual: anunciar cartelas para marcação.
- Uso de classes utilitárias do `java.util` como `Random` e `Arrays`.

## Desenvolvedor
- [Teofilo Nicolau](https://github.com/teofilonicolau)

Divirta-se jogando o Multiplayer Bingo!

