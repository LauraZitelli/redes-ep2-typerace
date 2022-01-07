# redes-ep2-typerace
Repositório para o EP2 de Redes de Computadores, EACH-USP - 2021/2

# Integrantes
* Fernanda Elimelek - 11208155
* Laura Zitelli de Souza - 11207814
* Vitória Gonçalves Lorentz - 11319020

# Descrição do jogo: 
O jogo consiste em uma competição de digitação entre os clientes conectados ao servidor.
Após inicialização do servidor, os jogadores podem entrar colocando um nickname único, caso o nickname escolhido já esteja em uso, é preciso entrar novamente com um novo nickname.
Quando os jogadores já estiverem no servidor e o jogo ainda não tiver iniciado todos podem digitar os seguintes comandos:

start: que dá inicio à partida para todos os jogadores. Após o disparo desse comando, as palavras vão aparecer para os jogadores poderem digitar o mais rápido possível.
quit: desconecta o jogador. Só é possível usar esse comando se a partida não estiver em andamento. Caso algum jogador utilize esse comando, os demais serão avisados.
help: imprime a lista de todos os comandos disponíveis
Ao longo da partida, é possível acionar o seguinte comando:
-p: imprime o placar do jogo durante a partida para todos os jogadores até aquele momento.

Após o inicio da partida, os jogadores deverão digitar as palavras o mais rápido possível as 8 palavras impressas na tela, após o comando de iniciar, e o primeiro que completar 5 palavras corretas vence a partida.
Por palavras corretas, ou “acertos”, se entende aquelas que foram digitadas e são iguais às que estão na tela.
O jogador não perde ponto caso digite uma palavra errada, mas o ponto só é computado se a palavra estiver totalmente correta.
Por ”palavras que faltaram” se considera as palavras que não chegaram a ser digitadas corretamente, tanto por uma questão de tempo ou por não estarem 100% iguais às da lista.
É impresso para todos os jogadores qual foi o resultado detalhado, com nickname do vencedor, tempo da partida e pontuação de cada um dos jogadores, tanto de acertos, quanto de palavras que faltaram. Vale lembrar que o jogador pode digitar as palavras na ordem que preferir. 

Com o fim da partida, os jogadores podem optar por sair da partida ou iniciar uma nova. Nesse momento, novos jogadores também podem ingressar no servidor.


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
