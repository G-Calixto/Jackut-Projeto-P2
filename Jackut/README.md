# Jackut - Rede Social

## Descrição
    Jackut é uma rede social desenvolvida como parte de um projeto acadêmico da disciplina de Programacao 2, pelo professor Mario Hozano.
    O projeto foi dividido em varias MILESTONES. Na MILESTONE 1, foi implementado: Gerenciamento de perfis de usuários, relacionamentos de amizade, sistema de mensagens, comunidades e diferentes tipos de relacionamentos sociais.

## Funcionalidades Implementadas

### 1. Gerenciamento de Usuários
    O gerenciamento de usuários permite o cadastro e controle de contas no Jackut. Cada usuário é identificado por um login único e precisa fornecer uma senha e um nome. O sistema valida as informações fornecidas, garantindo que não existam logins duplicados e que todos os campos obrigatórios sejam preenchidos. Além disso, os usuários podem personalizar seus perfis adicionando quaisquer atributos que acharem necessário, como idade, cidade, profissão, interesses e outros dados relevantes.
    Para acessar o sistema, é necessário realizar autenticação através de login e senha. Uma sessão é criada e mantida ativa enquanto o usuário estiver utilizando a plataforma. O sistema implementa ainda validações para garantir que apenas usuários cadastrados possam acessar funcionalidades restritas.

### 2. Sistema de Relacionamentos
    O sistema de relacionamentos é composto por várias funcionalidades:
    - **Amizades**: Permite que usuários estabeleçam conexões entre si através de convites
    - **Paqueras**: Usuários podem marcar outros como paqueras
    - **Ídolos**: Sistema para marcar usuários como ídolos
    - **Inimigos**: Possibilidade de marcar usuários como inimigos
    Cada tipo de relacionamento possui suas próprias regras e validações específicas.

### 3. Sistema de Mensagens
    O sistema de mensagens permite que usuários troquem recados entre si. Os recados são armazenados em ordem cronológica e podem ser lidos posteriormente. O sistema inclui validações para garantir que:
    - Usuários não possam enviar mensagens para si mesmos
    - Mensagens sejam entregues apenas a usuários válidos
    - Histórico de mensagens seja mantido adequadamente

### 4. Sistema de Comunidades
    O sistema permite a criação e gerenciamento de comunidades, onde usuários podem:
    - Criar novas comunidades
    - Participar de comunidades existentes
    - Gerenciar membros
    - Interagir dentro das comunidades

## Estrutura do Projeto

### Pacotes
- `entities`: Classes que representam as entidades do sistema
- `factory`: Implementação do padrão Factory para criação de objetos
- `relationship`: Classes relacionadas aos diferentes tipos de relacionamentos
- `exceptionsJackut`: Exceções personalizadas do sistema
- `utils`: Classes auxiliares do sistema
- `data`: Gerenciamento de persistência de dados
- `libs`: Bibliotecas externas
- `main`: Ponto de entrada da aplicação

### Classes Principais
- `Users`: Representa um usuário do sistema
- `Systems`: Gerencia as operações do sistema
- `Facade`: Interface para os testes de aceitação
- `SocialManager`: Gerencia as interações sociais
- `Comunidade`: Representa uma comunidade no sistema

### Diagrama de Classes 

``` 
+----------------+          +----------------+          +---------------------+
|    Facade      |--------->|    Systems     |--------->|     Users           |
+----------------+          +----------------+          +---------------------+
| - sistema      |          | - usuarios     |          | - atributos         |
|                |          | - sessoes      |          | - amigos            |
| + zerarSistema()|         | + criarUsuario()|         | - paqueras          |
| + criarUsuario()|         | + abrirSessao()|          | - idolos            |
| + abrirSessao() |         | + editarPerfil()|         | - inimigos          |
| + editarPerfil()|         | + adicionarAmigo()|       | - comunidades       |
| + adicionarAmigo()|       | + ehAmigo()     |         | - recados           |
| + ehAmigo()     |         | + getAmigos()   |         |                     |
| + getAmigos()   |         | + enviarRecado()|         | + getNome()         |
| + enviarRecado()|         | + lerRecado()   |         | + getLogin()        |
| + encerrarSistema()|      | + zerarSistema()|         | + getSenha()        |
+----------------+          +----------------+          | + setAtributo()     |
                                                        | + getAtributo()     |
                                                        | + adicionarConvite()|
                                                        | + aceitarConvite()  |
                                                        | + rejeitarConvite() |
                                                        | + ehAmigo()         |
                                                        | + getAmigos()       |
                                                        | + receberRecado()   |
                                                        | + lerRecado()       |
                                                        +---------------------+

+----------------+          +----------------+
| SocialManager  |--------->|  Comunidade    |
+----------------+          +----------------+
| - relacionamentos|        | - nome         |
| - comunidades   |        | - membros      |
|                |        | - descricao    |
| + gerenciarRelacionamento()| + adicionarMembro()|
| + criarComunidade()|      | + removerMembro()  |
| + gerenciarComunidade()|  | + getMembros()     |
+----------------+          +----------------+
```

## Como Executar

1. Compile o projeto:
```bash
javac -d bin src/*.java
```

2. Execute o programa:
```bash
java -cp bin Main
```

## Testes
O projeto utiliza o framework EasyAccept para testes de aceitação.
Os testes do sistema são realizados utilizando o framework EasyAccept, que permite a criação de testes de aceitação baseados em scripts. Os scripts de teste descrevem cenários de uso do sistema e verificam se o comportamento está de acordo com as especificações.
Na primeira MILESTONE, eles sao divididos em 4 grupos, cada grupo com dois testes. Em cada um desses grupos é avaliado uma implementaçao diferente do programa.

## Tratamento de Exceções
O sistema implementa um robusto sistema de tratamento de exceções, incluindo:
- Validação de login e senha
- Verificação de duplicidade de usuários
- Validação de relacionamentos
- Controle de acesso a comunidades
- Validação de mensagens
- Verificação de atributos obrigatórios

## Contribuição
Este é um projeto acadêmico desenvolvido como parte da disciplina de Programacao 2, do curso de Ciencias da Computacao. Para contribuições, entre em contato com os desenvolvedores.

## Licença
Este projeto é parte de um trabalho acadêmico e seu uso está sujeito às regras da instituição de ensino, UFAL - Universidade Federal de Alagoas. 