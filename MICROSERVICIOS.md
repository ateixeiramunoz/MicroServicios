
## 🧱 Arquitectura del ejemplo

| Componente         | Función principal                    | URL base     |
| ------------------ | ------------------------------------ | ------------ |
| `config-server`    | Provee la configuración centralizada | `/config`    |
| `discovery-server` | Registro de servicios con Eureka     | `/eureka`    |
| `gateway`          | Entrada única a los microservicios   | `/api/**`    |
| `users-service`    | Microservicio de usuarios            | `/users/**`  |
| `orders-service`   | Microservicio de pedidos             | `/orders/**` |

Todos los servicios leen su config del `config-server` y se registran en `eureka`.

---
## 🔧 Creación y selección de Dependencias por módulo

Los módulos se crean en el proyecto como `new -> Module`


## 🧱 Módulos del sistema de microservicios

| Nº | Módulo             | Descripción breve                          |
| -- |--------------------| ------------------------------------------ |
| 1  | `config-server`    | Servidor de configuración centralizada     |
| 2  | `discovery-server` | Registro de servicios (Eureka)             |
| 3  | `gateway`          | API Gateway (punto de entrada)             |
| 4  | `users-service`    | Microservicio de gestión de usuarios       |
| 5  | `orders-service`   | Microservicio de gestión de pedidos        |
| 6  | `config-repo`      | Carpeta o repo Git con las configuraciones |
| 7  | `auth-service`     | Servicio de autenticación (OAuth2/JWT)     |

---

### 1. 🛠 `config-server`

✅ Marca en IntelliJ:

* **Spring Web**
* **Spring Cloud Config Server**

---

### 2. 🧭 `discovery-server`

✅ Marca en IntelliJ:

* **Spring Web**
* **Spring Cloud Discovery - Eureka Server**

---

### 3. 🌐 `gateway`

✅ Marca en IntelliJ:

* **Spring Cloud Gateway**
* **Spring Cloud Discovery - Eureka Client**
* **Spring Cloud Config Client**

---

### 4. 👤 `users-service` *(igual para `orders-service`)*

✅ Marca en IntelliJ:

* **Spring Web**
* **Spring Boot Actuator**
* **Spring Cloud Discovery - Eureka Client**
* **Spring Cloud Config Client**
* *(Opcional)* **Spring Cloud OpenFeign** si harás llamadas a otros servicios por HTTP

---

### 5. 🗂 `config-repo` *(carpeta externa, no proyecto IntelliJ)*

Este no es un módulo de código. Es una carpeta donde guardarás los `.yml` de configuración como:

* `users-service.yml`
* `orders-service.yml`
* `gateway.yml`

Puede estar en tu máquina (`file://...`) o en GitHub.

---

### 6. 🔐 `auth-service` *(si lo incluyes luego)*

✅ Marca en IntelliJ:

* **Spring Web**
* **Spring Security**
* **OAuth2 Resource Server**
* *(Opcional)* **Spring Authorization Server** (no aparece como starter, hay que añadirlo manualmente al `pom.xml`)

---



## ✅ Objetivo

* Crear una carpeta llamada `config-repo/` dentro lado del proyecto Maven (`MicroServicios/`)
* Poner dentro archivos como `users-service.yml`, `orders-service.yml`, etc.
* Configurar el `config-server` para leer esa carpeta como origen

---

## 🗂️ 1. Estructura de carpetas esperada

Supongamos que tu proyecto está así:

```
MicroServicios/
├── config-server/
├── users-service/
├── 12345-service/
├── ...
├── config-repo/             👈 Carpeta de configuración local
│   ├── users-service.yml
│   ├── orders-service.yml
│   └── gateway.yml
```

> 🔧 Asegúrate de que **`config-repo/` no esté dentro de un módulo Maven**, sino al lado de ellos, en la raíz del proyecto.

---

## 📄 2. Contenido de `application.yml` en `config-server/src/main/resources`

Elimina el application.properties, ya que sería redundante.

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server

  cloud:
    config:
      server:
        native:
          search-locations: file:../config-repo  # Ruta relativa al módulo
        # Alternativa: si lo mueves a un path absoluto:
        # search-locations: file:/ruta/completa/a/config-repo
      label: main
```

---

## 📘 3. Contenido de archivo `config-repo/users-service.yml`

```yaml
spring:
  application:
    name: users-service

server:
  port: 8081

info:
  app: Usuario

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

Puedes replicar este formato para el resto de módulos `orders-service.yml`, `gateway.yml`, 

---

## 🧪 4. Probar el `config-server`

### ✅ Ejecuta `config-server`:

Desde IntelliJ o terminal:

```bash
cd config-server
mvn spring-boot:run
```

Debe levantar en **puerto 8888**.

---

### 🔍 Prueba que responde:

Accede en navegador o con `curl` a:

```
http://localhost:8888/users-service/default
```

Deberías ver el contenido de `users-service.yml` parseado como JSON (propiedades planas, estilo Spring Config).

---

## ✅ ¿Siguiente paso?



Si ya tienes todos los módulos, el `config-repo` y los `.yml` listos, vamos a **arrancar el sistema paso a paso** y ver qué debe ocurrir en cada uno. 

---

## 🚀 ORDEN DE ARRANQUE

Es importante arrancar los servicios **en el orden correcto** para evitar errores de conexión:

### 1. 🔧 `config-server`

### 2. 🧭 `discovery-server`

### 3. 🌐 `gateway`

### 4. 👤 `users-service`

### 5. 📦 `orders-service`

---

## ✅ PASO A PASO

---

### 1. 🔧 **Arrancar `config-server`**

Desde IntelliJ o consola:

```bash
cd config-server
mvn spring-boot:run
```

### 🔍 Qué debes ver:

* Puerto: `8888`
* Mensaje como:

  ```
  Located environment: users-service/default
  ```

Prueba en el navegador:

```
http://localhost:8888/users-service/default
```

✅ Debe mostrar el contenido JSON de `users-service.yml`

---

### 2. 🧭 **Arrancar `discovery-server`**

```bash
cd discovery-server
mvn spring-boot:run
```

### 🔍 Qué debes ver:

* Puerto: `8761`
* Web UI de Eureka disponible en:
  👉 `http://localhost:8761`

Al principio estará vacío (sin servicios registrados).

---

### 3. 🌐 **Arrancar `gateway`**

```bash
cd gateway
mvn spring-boot:run
```

### 🔍 Qué debes ver:

* Puerto: `8080`
* En consola:

  ```
  DiscoveryClient ... registering service with Eureka
  ```

Cuando visites:
👉 `http://localhost:8080/`
(dependiendo de si has definido rutas) puedes probar más adelante llamadas a `/users` o `/orders`.

---

### 4. 👤 **Arrancar `users-service`**

```bash
cd users-service
mvn spring-boot:run
```

### 🔍 Qué debes ver:

* Puerto: `8081`
* Registro correcto en Eureka:

  ```
  DiscoveryClient ... registering service with Eureka
  ```

Ve a `http://localhost:8761`, y verás `USERS-SERVICE` en la lista.

También puedes visitar:
👉 `http://localhost:8081/actuator/info`
👉 `http://localhost:8081/actuator/health`

---

### 5. 📦 **Arrancar `orders-service`**

Igual que `users`, en puerto `8082`.

---

## ✅ Qué deberías ver en total

| Componente         | Dirección esperada                      |
| ------------------ | --------------------------------------- |
| Config Server      | `http://localhost:8888`                 |
| Eureka (Discovery) | `http://localhost:8761`                 |
| Gateway            | `http://localhost:8080`                 |
| Users              | `http://localhost:8081` + `/actuator/*` |
| Orders             | `http://localhost:8082` + `/actuator/*` |

En Eureka (`localhost:8761`), deben verse registrados:

* `users-service`
* `orders-service`
* `gateway`

---
