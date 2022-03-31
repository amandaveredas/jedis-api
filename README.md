# bootcamp-java-starwars-restapi

## Essa API realiza operações de cadastro de Jedis:
* Obter Jedi pelo id;
* Salvar um novo Jedi;
* Atualizar um Jedi pelo id;
* Excluir um Jedi pelo id.

## Regras de implementação:
1. Obter Jedi pelo id:
   1. Caso seja encontrado: Retorna Status 200 - OK. Body: Jedi.
   2. Caso não seja encontrado: Retorna Status 400 - NotFound. Body: vazio.
   3. Caso ocorra URISyntaxException: Retorna Status 500 - InternalServerError. Body: vazio.
2. Salvar um novo Jedi:
   1. Caso seja encontrado: Retorna Status 201 - Created. Body: Jedi.
   2. Caso ocorra URISyntaxException: Retorna Status 500 - InternalServerError. Body: vazio.
3. Atualizar um Jedi pelo id:
   1. Caso seja encontrado: Retorna Status 204 - NoContent. Body: vazio.
   2. Caso não seja encontrado: Retorna Status 404 - BadRequest. Body: vazio.
   3. Caso ocorra Exception: Retorna Status 500 - InternalServerError. Body: vazio.
4. Excluir um Jedi pelo id:
   1. Caso seja encontrado: Retorna Status 204 - NoContent. Body: vazio.
   2. Caso não seja encontrado: Retorna Status 400 - NotFound. Body: vazio.
   3. Caso ocorra Exception: Retorna Status 500 - InternalServerError. Body: vazio.

## Para rodar a aplicação:
1. Faça o clone desse repositório em sua máquina local.
2. Rode o projeto na sua IDE de preferência.
3. Abra o console do H2: http://localhost:8080/h2-console
4. O password e a senha é o que esará lá por padrão. 
### Pronto, apĺicação Rodando!

## Slides sobre TDD com StarWars
https://docs.google.com/presentation/d/13DAMAh-eu8GCftH2QeY4qPz9mjKN_KP-kuHxtvDvCSc/edit?usp=sharing

## Design da API:
![DesignStarWarsRestApi](https://user-images.githubusercontent.com/42419543/159596039-70e1ff73-cfe5-47a9-8a88-1acbfd0d1e75.png)
