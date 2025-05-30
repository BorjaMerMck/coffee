# Sistema de Gestión de Cafetería ☕

API REST desarrollada con Spring Boot para la gestión de una cafetería. El sistema permite administrar cafés, clientes y pedidos.

## Características 🚀

- Gestión de cafés (CRUD)
- Gestión de clientes (CRUD)
- Gestión de pedidos (CRUD)
- Paginación de resultados
- Manejo de excepciones centralizado
- Validaciones de negocio
- Documentación JavaDoc completa

## Tecnologías Utilizadas 💻

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JavaDoc
- Swagger/OpenAPI 3.0

## Documentación API con Swagger 📝

La API está completamente documentada usando Swagger/OpenAPI 3.0. Puedes acceder a la documentación interactiva de la API en:

```
http://localhost:8080/swagger-ui.html
```

Características de la documentación:
- Descripción detallada de todos los endpoints
- Esquemas de modelos de datos
- Ejemplos de peticiones y respuestas
- Documentación de códigos de estado HTTP
- Interfaz interactiva para probar los endpoints
- Documentación de parámetros y respuestas

## Documentación 📚

El proyecto incluye documentación JavaDoc completa para todas las capas de la aplicación
Para generar la documentación JavaDoc, ejecuta:
```bash
mvn javadoc:javadoc
```
La documentación generada estará disponible en: `target/site/apidocs/index.html`

## Estructura del Proyecto 📁

```
coffee-shop-api/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/gammatech/coffee/
│       │       ├── controllers/
│       │       │   ├── CoffeeController.java
│       │       │   ├── CustomerController.java
│       │       │   └── OrderController.java
│       │       │
│       │       ├── models/
│       │       │   ├── Coffee.java
│       │       │   ├── Customer.java
│       │       │   ├── Order.java
│       │       │   ├── OrderItem.java
│       │       │   └── OrderStatus.java
│       │       │
│       │       ├── services/
│       │       │   ├── CoffeeService.java
│       │       │   ├── CustomerService.java
│       │       │   └── OrderService.java
│       │       │
│       │       ├── repositories/
│       │       │   ├── CoffeeRepository.java
│       │       │   ├── CustomerRepository.java
│       │       │   └── OrderRepository.java
│       │       │
│       │       ├── exceptions/
│       │       │   ├── GlobalExceptionHandler.java
│       │       │   ├── ResourceNotFoundException.java
│       │       │   └── ResourceAlreadyExistsException.java
│       │       │
│       │       └── responses/
│       │           ├── CoffeePageResponse.java
│       │           └── CustomerPageResponse.java
│       │
│       └── resources/
│           └── application.properties
│
├── pom.xml
├── README.md
└── LICENSE.md
```

## Endpoints API 🛣️

### Cafés
- GET `/api/coffees` - Obtener cafés (paginado)
- GET `/api/coffees/all` - Obtener todos los cafés
- GET `/api/coffees/{id}` - Obtener café por ID
- POST `/api/coffees` - Crear nuevo café
- PUT `/api/coffees/{id}` - Actualizar café
- PATCH `/api/coffees/{id}/image` - Actualizar imagen del café
- DELETE `/api/coffees/{id}` - Eliminar café

### Clientes
- GET `/api/customers` - Obtener clientes (paginado)
- GET `/api/customers/all` - Obtener todos los clientes
- GET `/api/customers/{id}` - Obtener cliente por ID
- POST `/api/customers` - Crear nuevo cliente
- PUT `/api/customers/{id}` - Actualizar cliente
- PATCH `/api/customers/{id}/email` - Actualizar email del cliente
- DELETE `/api/customers/{id}` - Eliminar cliente

### Pedidos
- GET `/api/orders` - Obtener todos los pedidos
- GET `/api/orders/{id}` - Obtener pedido por ID
- GET `/api/orders/status/{status}` - Obtener pedidos por estado
- GET `/api/orders/customer/{customerId}` - Obtener pedidos por cliente
- POST `/api/orders` - Crear nuevo pedido
- PUT `/api/orders/{id}` - Actualizar pedido
- PATCH `/api/orders/{id}/status` - Actualizar estado del pedido
- DELETE `/api/orders/{id}` - Eliminar pedido

## Estados de Pedido 📦

1. PENDING - Pedido registrado
2. PROCESSING - Pedido en preparación
3. SHIPPED - Pedido enviado
4. DELIVERED - Pedido entregado

## Configuración del Proyecto ⚙️

1. Clonar el repositorio:
```bash
git clone https://github.com/BorjaMerMck/coffee>
```

2. Configurar la base de datos en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/coffee_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

3. Ejecutar el proyecto:
```bash
mvn spring-boot:run
```

## Ejemplos de Uso 📝

### Crear un Café
```json
POST /api/coffees
{
    "name": "Café Americano",
    "price": 2.50,
    "imageUrl": "https://ejemplo.com/cafe-americano.jpg"
}
```

### Crear un Pedido
```json
POST /api/orders
{
    "customer": {
        "id": 1
    },
    "items": [
        {
            "coffee": {
                "id": 1
            },
            "quantity": 2
        }
    ]
}
```

## Validaciones 🔍

### Café
- Nombre único
- Precio mayor que cero
- URL de imagen requerida

### Cliente
- Email único
- Nombre requerido
- Email válido

### Pedido
- Cliente existente
- Al menos un ítem
- No permite cafés duplicados
- Cantidades positivas

## Autor ✒️

* **Sandra Amorós Reyes**  &&  Borja Merchan Mckenna

Proyecto desarrollado como Trabajo Final del Bootcamp de Desarrollo Backend en GammaTech School



