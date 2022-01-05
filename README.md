# redes-ep2-typerace
Repositório para o EP2 de Redes de Computadores, EACH-USP - 2021/2

# Integrantes
* Fernanda Elimelek - 11208155
* Laura Zitelli de Souza - 11207814
* Vitória Gonçalves Lorentz - 11319020

# Descrição do jogo: 
O jogo consiste em uma competição de digitação entre os clientes conectados ao servidor. Após inicialização do servidor, os jogadores podem entra colocando um nickname, caso o nickname escolhido ja esteja em uso, e preciso entrar novamente com um novo nickname. Quando os jogadores ja estiverem no servidor e o jogo ainda não tiver iniciado eles podem digitar os seguintes comandos:
  - start: que da inicio a partida para todos os jogadores, após o disparo desse comando, as palavras vão aparece para os jogadores podem digitar o mais rápido possível. 
  - quit: desconecta o jogador, só é possível usar esse comando se não a partida não estiver andamento. Caso algum jogador utilize esse comando, os demais serão avisados.
  - help: imprime a lista de todos os comandos disponíveis 
  - -p: imprime o placar do jogo durante a partida para todos os jogadores. 

Após o inicio da partida, os jogadores deverão digitar as palavras o mais rápido possível as 8 palavras impressas na tela por o comando de iniciar e o primeiro que completar 5 palavras corretas vence a partida, é impresso para todos os jogadores qual foi o resultado detalhado, com nickname do vencedor, tempo da partida e pontuação de cada um dos jogadores. Vale lembrar que o jogador pode digitar as palavras na ordem que preferir. O ponto só é computado se a palavra estiver totalmente correta.

Com o fim da partida os jogadores podem optar por sair da partida ou iniciar uma nova, nesse momento, novos jogadores também podem ingressar no servidor.


## Pré-requisitos
* JDK 11 ou maior (testado com a JDK11 OpenJDK)
* Gradle (incluso no repositório, não é necessário instalá-lo)

### Rodando
Para rodar o servidor
```sh
./gradlew server:run
```

Para rodar um cliente
```sh
./gradlew client:run
```
