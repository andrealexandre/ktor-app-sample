# Ktor HTTP API sample

This is a simple app that exposes a in-memory store for the resource called Order.
This resources has two properties, `id` and `description`.

The HTTP API allow to **get all**, **get one**, **create**, **update** and **delete**.

All the code is in the `src/` folder. For consulting the implementation for the routes, check `src/Application.kt`.

### How to build

```bash
gradle build
```

### How to run

```bash
gradle run
``` 

This will run on port 8080. You can access it via `http://localhost:8080`.

### API endpoints

| Endpoint                 | description |
|--------------------------|-------------|
| GET    /orders           | get all     |
| POST   /orders           | create      |
| GET    /orders/{uuid}    | get one     |
| PUT    /orders/{uuid}    | update      |
| DELETE /orders/{uuid}    | delete      |

The `POST` request return a `Location` header with the full path to the resource.