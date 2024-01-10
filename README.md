
<h1 align="center">Validador de Cartas Magic: The Gathering</h1>

<p align="center">
  Um serviço desenvolvido para validar cartas de acordo com as regras de negócio disponibilizadas pela equipe do LuizaLabs.
</p>


# Funcionalidades

- [x] Entrada de dados para validação a partir de um arquivo TXT.
- [x] Consulta na Api da Scryfall para validar se o nome da carta é verdadeira.
- [x] Validação de cartas de acordo com regra de negócio disponibilizado pelo LuizaLabs.
- [x] Testes unitários para os métodos implementados.

### Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- Java 21
- Spring Boot 3.21
- Lombok
- Project Reactor 3.6.1

## Como usar
# Clone este repositório
```bash
git clone https://github.com/gianhp12/Cards-Magic-Validator
```

##### As informações das cartas que serão validadas devem ser colocadas dentro do arquivo deck.txt existente no caminho: cardsmagicvalidator\src\main\resources\data\deck.txt
 Seguindo o padrão de quantidade, nome da carta e nomenclaturas de tipos de cartas entre colchetes.

##### Obs: Necessário possuir o Java instalado para executar o projeto. O mesmo pode ser baixado pelo link abaixo:
<a>https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR</a>


##### Execute a aplicação em uma IDE de sua prefêrencia ou realize o download do Maven pelo link: 
<a>https://maven.apache.org/download.cgi</a>

##### Após ter concluido a instalação do Maven, navegue até a pasta raiz do projeto e rode o comando pelo terminal do Windows:
 
 mvn clean install

##### O comando irá gerar um JAR executável na pasta target do projeto.

##### O serviço inciará na porta padrão do Spring :8080.


Agradeço muito a LuizaLabs pela oportunidade!






