# Jackut - Rede Social

## Descrição
    Jackut é uma rede social desenvolvida como parte de um projeto acadêmico da disciplina de Programacao 2, pelo professor Mario Hozano.
    O projeto foi dividido em varias MILESTONES. Na MILESTONE 1, foi implementado: Gerenciamento de perfis de usuários, relacionamentos de amizade e envio de mensagens.

## Funcionalidades Implementadas

### 1. Gerenciamento de Usuários
    O gerenciamento de usuários permite o cadastro e controle de contas no Jackut. Cada usuário é identificado por um login único e precisa fornecer uma senha e um nome. O sistema valida as informações fornecidas, garantindo que não existam logins duplicados e que todos os campos obrigatórios sejam preenchidos. Além disso, os usuários podem personalizar seus perfis adicionando quaisquer atributos que acharem necessário, como idade, cidade, profissão, interesses e outros dados relevantes.
    Para acessar o sistema, é necessário realizar autenticação através de login e senha. Uma sessão é criada e mantida ativa enquanto o usuário estiver utilizando a plataforma. O sistema implementa ainda validações para garantir que apenas usuários cadastrados possam acessar funcionalidades restritas.

### 2. Sistema de Amizades
    O sistema de amizades permite que usuários estabeleçam conexões entre si. Para iniciar uma amizade, um usuário envia um convite que precisa ser aceito pelo destinatário.

### 3. Sistema de Mensagens
    O sistema de mensagens permite que usuários troquem recados entre si. Os recados são armazenados em ordem cronológica e podem ser lidos posteriormente.


## Estrutura do Projeto

### Pacotes
- `entitites`: Classes que representam as entidades do sistema
- `exceptionsJackut`: Exceções personalizadas do sistema
- `utils`: Classes auxiliares do sistema
- `data`: Gerenciamento de persistência de dados
- `libs`: Bibliotecas externas

### Classes Principais
- `Users`: Representa um usuário do sistema
- `Systems`: Gerencia as operações do sistema
- `Facade`: Interface para os testes de aceitação

### Diagrama de Classes

``` 
+----------------+          +----------------+          +---------------------+
|    Facade      |--------->|    Systems     |--------->|     Users           |
+----------------+          +----------------+          +---------------------+
| - sistema      |          | - usuarios     |          | - atributos         |
|                |          | - sessoes      |          | - amigos            |
| + zerarSistema()|         | + criarUsuario()|         | - convites          |
| + criarUsuario()|         | + abrirSessao()|          | - recados           |
| + abrirSessao() |         | + editarPerfil()|         |                     |
| + editarPerfil()|         | + adicionarAmigo()|       | + getNome()         |
| + adicionarAmigo()|       | + ehAmigo()     |         | + getLogin()        |
| + ehAmigo()     |         | + getAmigos()   |         | + getSenha()        |
| + getAmigos()   |         | + enviarRecado()|         | + setAtributo()     |
| + enviarRecado()|         | + lerRecado()   |         | + getAtributo()     |
| + encerrarSistema()|      | + zerarSistema()|         | + adicionarConvite()|
+----------------+          +----------------+          | + aceitarConvite()  |
                                                        | + rejeitarConvite() |
                                                        | + ehAmigo()         |
                                                        | + getAmigos()       |
                                                        | + receberRecado()   |
                                                        | + lerRecado()       |
                                                        +---------------------+
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


## Contribuição
Este é um projeto acadêmico desenvolvido como parte da disciplina de Programacao 2, do curso de Ciencias da Computacao. Para contribuições, entre em contato com os desenvolvedores.

## Licença
Este projeto é parte de um trabalho acadêmico e seu uso está sujeito às regras da instituição de ensino, UFAL - Universidade Federal de Alagoas. 