# Product Manager Application

## Introdução

Este projeto é uma extensão do [Product Manager Application (Extendido)](https://github.com/Paloma-Regis-Ferreira/product-manager-exercicio-3.git) com melhorias significativas, focando na transição do uso do banco de dados H2 para MySQL como banco de dados principal e na implementação de migrações de banco de dados usando Flyway.

## Melhorias no Projeto

### Transição para MySQL

O projeto foi reestruturado para substituir o banco de dados em memória H2 pelo MySQL, um sistema de gerenciamento de banco de dados relacional robusto e escalável. O MySQL oferece melhor desempenho e persistência, sendo adequado para ambientes de produção.

### Integração da Migração com Flyway

O Flyway foi integrado ao projeto para gerenciar mudanças no esquema do banco de dados de forma automatizada e consistente em diferentes ambientes.

### Novo Recurso: Busca de Produtos por Filtros

Foi adicionado um novo endpoint para buscar produtos com base em filtros de nome, descrição e preço todos parametros opcionais.

## Curl

### Obter produtos usando os filtros disponíveis

```bash
curl -X GET "http://localhost:8080/api/products/findByFilters?name=produto1&price=50.0" -H "accept: application/json"
```

### Execução do Projeto

O projeto não está na nuvem, portanto é necessario garantir que o MySQL está em execução e acessível para realizar os testes necessários
