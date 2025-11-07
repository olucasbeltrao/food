# 🍕 Food Order Management System

Um sistema de gerenciamento de pedidos de comida desenvolvido como projeto educacional para o curso de Programação Orientada a Objetos.

## 📋 Sobre o Projeto

Este é um sistema web para gerenciamento de pedidos de comida que demonstra conceitos importantes de POO, incluindo:
- **Padrão Observer** para notificações de mudança de status
- **Injeção de Dependência** com Spring
- **Interface gráfica moderna** com Vaadin
- **Persistência de dados** com JPA/Hibernate
- **Arquitetura em camadas**

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Vaadin 24.9.4** - Interface gráfica web
- **H2 Database** - Banco de dados em memória
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências

## 🔄 Fluxo de Pedidos

O sistema gerencia pedidos através de 4 status:

1. **PENDING** (Pendente) - Pedido criado
2. **PREPARING** (Preparando) - Em preparo na cozinha
3. **IN_TRANSIT** (Em trânsito) - Saiu para entrega
4. **DELIVERED** (Entregue) - Pedido finalizado

## 🏗️ Atividades

1. Crie a interface `OrderStatusObserver` com o método `update(Order order)`.
2. Crie a implementação `KitchenListener` que implementa `OrderStatusObserver`
    - Todos os listeners possuem um atributo `HomeView` que é passado via construtor.
    - No método `update`, verifique se o status do pedido é `PREPARING` e, se for, chame o método `addToKitchen()` e `addLog()` da `HomeView`.
3. Crie a implementação `DeliveryListener` que implementa `OrderStatusObserver`
    - Todos os listeners possuem um atributo `HomeView` que é passado via construtor.
    - No método `update`, verifique se o status do pedido é `IN_TRANSIT` e, se for, chame o método `addToDelivery()` e `showNotification()` da `HomeView`.
4. Em `OrderService`:
    - Adicione uma lista de listeners
    - Crie um método para notificar todos os listeners.
    - No método `advanceOrderStatus()`, após atualizar o status do pedido, chame o método de notificação dos listeners.
    - No método `setView` faça a associação dos listeners `KitchenListener` e `DeliveryListener`, passando a `HomeView` como parâmetro.