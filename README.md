# The Night of the Living DEISI - Desenvolvimento

Neste projeto/jogo decidimos separar ao máximo as resposabilidades das entidades. O gameManeger encarrega-se simplesmente de chamar os métodos/funçóes obrigatórias do projeto, o único método no qual ele se encarrega de implementar é o loadGame.

## Diagrama UML

![](diagrama.png?raw=true "Diagrama UML")

## Entidades do projeto

| Nome da Entidade | Descrição |
| ------ | ------ |
| GameManager (classe) | Possui as funções/metodos obrigatórios do projeto |
| GameSession (classe) | Possui informações essenciais sobre uma sessão de jogo, como numero de turnos completos, criaturas no tabuleiro, etc. |
| TestGameManager (classe) | Onde estão os testes unitários do jogo |
| Coord (classe) | Representa uma coordenada |
| Board (classe) | Representa o tabuleiro, contem uma grade que está representada por um array multidimensional de strings |
| Creature (classe) | Representa uma criatura, que pode ser um humano ou zombie |
| Equipment (classe) | Representa um equipamento, que pode ser uma espada samurai ou um escudo de madeira |
| CreatureType (enumerado) | Representa os diferentes tipos de uma criatura, ao momento, apenas HUMANO ou ZOMBIE |
| EquipmentStatus (enumerado) | Representa os diferentes estados de um equipamento, ao momento, apenas UNCAPTURED, CAPTURED e DESTROYED |
| EquipmentType (enumerado) | Representa os diferentes tipos de um equipamento, ao momento, apenas ESPADA ou ESCUDO |
