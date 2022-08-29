## Informações
Esta API tem como objetivo fornecer endpoints para o controle de sondas em outros planetas por meio de comandos.

#### Exemplo da necessidade:
```
Tamanho da área do planeta : 5x5

Posição de pouso da sonda 1: x=1, y=2 apontando para Norte
Sequencia de comandos: LMLMLMLMM
Posição final da sonda: x=1 y=3 apontando para Norte

Posição de pouso da sonda 2: x=3, y=3 apontando para Leste
Sequencia de comandos: MMRMMRMRRML
Posição final da sonda: x=5 y=1 apontando para Norte
```

#### Detalhes sobre o funcionamento acima:

A sequência de comandos é um conjunto de instruções enviadas da terra para a sonda, onde :
- `M` -> Andar para a frente na direção que está 1 posição.
- `L` -> Virar a sonda para a esquerda (90 graus)
- `R` -> Virar a sonda para a direita (90 graus)

A orientação da sonda dentro do plano cartesiano usa uma rosa dos ventos como referência

![rosa dos ventos](http://i.imgur.com/li8Ae5L.png "Rosa dos Ventos")

#### Adicionais implementados:
Foi implementado um sistema de colisão que verifica se o ponto em que a sonda irá pousar ou se mover está ocupado por outra sonda ou fora dos limites do terreno.
- `Colisão no pouso`: na tentativa de pouso (criação) de uma nova sonda, é verificado previamente se o ponto de pouso está ocupado por outra sonda, caso positivo, a sonda não será salva na base de dados e uma mensagem informativa será retornada.
- `Colisão no movimento`: no movimento de uma sonda, é verificado previamente se o ponto de destino está ocupado por outra sonda, caso positivo, a sonda que está se movendo ficará parada no ponto percorrido até o momento e uma mensagem informativa será retornada junto às informações de saída de dados que conterão as coordenadas atuais da sonda.
- `Movimento para fora dos limites`: no movimento de uma sonda, é verificado previamente se o ponto de destino está fora dos limites do terreno (5x5), caso positivo, a sonda que está se movendo ficará parada no ponto percorrido até o momento e uma mensagem informativa será retornada junto às informações de saída de dados que conterão as coordenadas atuais da sonda.
##


## Documentação da API

#### Cria uma ou várias sondas

```http
  POST /sonda
```

Parâmetros de entrada

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `planeta` | `String` | **Obrigatório**. Planeta de destino da Sonda|
| `inicialX` | `Integer` | **Obrigatório**. Posição inicial X da Sonda (0 a 5)|
| `inicialY` | `Integer` | **Obrigatório**. Posição inicial Y da Sonda (0 a 5)|
| `direcaoInical` | `String` | **Obrigatório**. Direção inicial da Sonda (E = Leste, W = Oeste, N = Norte, S = Sul)|
| `comandos` | `String` | **Opcional**. Sequência de comandos para movimentar a Sonda assim que pousar (máx. 255 caracteres -> M = Mover, L = Gira 90 graus para esquerda, R = Gira 90 graus para direita)|

Parâmetros de Saída

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `sondaId` | `Long` | ID da Sonda|
| `inicialX` | `Integer` | Posição inicial X da Sonda|
| `inicialY` | `Integer` | Posição inicial Y da Sonda|
| `direcaoInical` | `String` | Direção inicial da Sonda|
| `atualX` | `Integer` | Posição final X da Sonda|
| `atualY` | `Integer` | Posição final Y da Sonda|
| `direcaoAtual` | `String` | Direção final da Sonda|
| `planeta` | `String` | Planeta de destino da Sonda|
| `ultimoComando` | `String` | Última sequência de comandos recebida|
| `coordenadas` | `Array` | Lista das coordenadas de pouso e todos os movimentos da Sonda|
| `erro` | `String` | Mensagem de erro caso não seja possível pousar ou mover uma sonda devido colisão com outra sonda|

Coordenadas - descrição

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `x` | `Integer` | Posição X de uma coordenada|
| `y` | `Integer` | Posição X de uma coordenada|
| `direcao` | `String` | Direção de uma coordenada (E, W, N, S)|
| `comandoExecutado` | `String` | Comando executado que direcionou a sonda para a coordenada (M, L, R)|

#### Busca informações de uma Sonda

```http
  GET /sonda/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**. O ID da Sonda que deseja obter as informações|

Parâmetros de Saída

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `sondaId` | `Long` | ID da Sonda|
| `inicialX` | `Integer` | Posição inicial X da Sonda|
| `inicialY` | `Integer` | Posição inicial Y da Sonda|
| `direcaoInical` | `String` | Direção inicial da Sonda|
| `atualX` | `Integer` | Posição final X da Sonda|
| `atualY` | `Integer` | Posição final Y da Sonda|
| `direcaoAtual` | `String` | Direção final da Sonda|
| `planeta` | `String` | Planeta de destino da Sonda|
| `ultimoComando` | `String` | Última sequência de comandos recebida|
| `coordenadas` | `Array` | Lista das coordenadas de pouso e todos os movimentos da Sonda|
| `erro` | `String` | Mensagem de erro caso não seja possível pousar ou mover uma sonda devido colisão com outra sonda|

Coordenadas - descrição

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `x` | `Integer` | Posição X de uma coordenada|
| `y` | `Integer` | Posição X de uma coordenada|
| `direcao` | `String` | Direção de uma coordenada (E, W, N, S)|
| `comandoExecutado` | `String` | Comando executado que direcionou a sonda para a coordenada (M, L, R)|

#### Lista informações de todas as Sondas

```http
  GET /sonda
```

Parâmetros de Saída: Uma lista dos parâmetros abaixo

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `sondaId` | `Long` | ID da Sonda|
| `inicialX` | `Integer` | Posição inicial X da Sonda|
| `inicialY` | `Integer` | Posição inicial Y da Sonda|
| `direcaoInical` | `String` | Direção inicial da Sonda|
| `atualX` | `Integer` | Posição final X da Sonda|
| `atualY` | `Integer` | Posição final Y da Sonda|
| `direcaoAtual` | `String` | Direção final da Sonda|
| `planeta` | `String` | Planeta de destino da Sonda|
| `ultimoComando` | `String` | Última sequência de comandos recebida|
| `coordenadas` | `Array` | Lista das coordenadas de pouso e todos os movimentos da Sonda|
| `erro` | `String` | Mensagem de erro caso não seja possível pousar ou mover uma sonda devido colisão com outra sonda|

Coordenadas - descrição

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `x` | `Integer` | Posição X de uma coordenada|
| `y` | `Integer` | Posição X de uma coordenada|
| `direcao` | `String` | Direção de uma coordenada (E, W, N, S)|
| `comandoExecutado` | `String` | Comando executado que direcionou a sonda para a coordenada (M, L, R)|

#### Movimenta uma Sonda existente

```http
  PUT /sonda/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**. O ID da Sonda que deseja movimentar|

Parâmetros de entrada

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `comandos`      | `String` | **Obrigatório**. Sequência de comandos para movimentar a Sonda assim que pousar (máx. 255 caracteres -> M = Mover, L = Gira 90 graus para esquerda, R = Gira 90 graus para direita)|


Parâmetros de Saída

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `sondaId` | `Long` | ID da Sonda|
| `inicialX` | `Integer` | Posição inicial X da Sonda|
| `inicialY` | `Integer` | Posição inicial Y da Sonda|
| `direcaoInical` | `String` | Direção inicial da Sonda|
| `atualX` | `Integer` | Posição final X da Sonda|
| `atualY` | `Integer` | Posição final Y da Sonda|
| `direcaoAtual` | `String` | Direção final da Sonda|
| `planeta` | `String` | Planeta de destino da Sonda|
| `ultimoComando` | `String` | Última sequência de comandos recebida|
| `coordenadas` | `Array` | Lista das coordenadas dos movimentos realizados somente da sequência de comandos recebida|
| `erro` | `String` | Mensagem de erro caso não seja possível pousar ou mover uma sonda devido colisão com outra sonda|

Coordenadas - descrição

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `x` | `Integer` | Posição X de uma coordenada|
| `y` | `Integer` | Posição X de uma coordenada|
| `direcao` | `String` | Direção de uma coordenada (E, W, N, S)|
| `comandoExecutado` | `String` | Comando executado que direcionou a sonda para a coordenada (M, L, R)|
