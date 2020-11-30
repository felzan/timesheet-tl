## Timesheet

### 
Este projeto contém a API para o serviço de controle de horas.

- Documentação visual disponível em: http://localhost:8080/swagger-ui.html

Para o controle de acesso foi utilizado o Spring Security, pode ser feito o login pela página padrão.

Usuários e senha para teste:
```
admin@email.com  | admin
p1@email.com     | p1
p2@email.com     | p2
```

Para desenvolvimento e testes, utilizei o H2 pela praticidade; adicionando também alguns dados.

Para requisições na API deve ser utilizado a autenticação "Basic"; ex: `Authorization: Basic YWRtaW5AZW1haWwuY29tOmFkbWlu`

#### Exemplos de cURL
Admin: Lista todos os projetos com as horas de todos
```cURL
curl --location --request GET 'localhost:8080/api/v1/projects' \
--header 'Authorization: Basic YWRtaW5AZW1haWwuY29tOmFkbWlu'
```

Usuário: Lista projetos que está alocado com as horas
```cURL
curl --location --request GET 'localhost:8080/api/v1/projects' \
--header 'Authorization: Basic cDFAZW1haWwuY29tOnAx'
```

Usuário: Lança horas no projeto
```cURL
curl --location --request POST 'http://localhost:8080/api/v1/projects/2/hours' \
--header 'Authorization: Basic cDJAZW1haWwuY29tOnAy' \
--header 'Content-Type: application/json' \
--data-raw '{
    "day": "2020-12-01",
    "hours": "08:30"
}'
```

