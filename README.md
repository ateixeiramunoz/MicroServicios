# Microservicios con Spring Cloud

Ejemplo docente para estudiar configuración centralizada, descubrimiento de servicios, gateway y comunicación entre aplicaciones Java.

## Mapa del repositorio

El [pom.xml raíz](pom.xml) enumera estos módulos:

| Módulo | Contenido |
| --- | --- |
| [shared](shared/) | Código compartido |
| [config-server](config-server/) | Servidor Spring Cloud Config |
| [discovery](discovery/) | Aplicación del ejemplo de descubrimiento |
| [gateway](gateway/) | Punto de entrada a los servicios |
| [users-service](users-service/) | Servicio de usuarios |
| [orders-service](orders-service/) | Servicio de pedidos |
| [frontend-service](frontend-service/) | Interfaz web |

También existen [config-repo](config-repo/), [eureka-server](eureka-server/) y [email-service](email-service/). Estos dos últimos no figuran en la lista de módulos del POM raíz; no deben confundirse con un arranque automático de todos los servicios.

## Documentación

La [guía de microservicios](MICROSERVICIOS.md) desarrolla los conceptos y ejemplos. Algunas denominaciones de esa guía describen la arquitectura del taller; la tabla anterior refleja las carpetas y módulos presentes en el repositorio.

## Preparación

- Java 21, según los POM revisados.
- Maven e IDE con soporte para proyectos Maven.
- Revisar el POM y la configuración de cada servicio antes de arrancarlo.

El [config-server](config-server/src/main/resources/application.yml) utiliza el perfil `native`, el puerto 8888 y `file:./config-repo`. Esta ruta depende del directorio de trabajo del proceso: debe resolver a la carpeta de configuración del repositorio.

No hay un script único de puesta en marcha. Importa los módulos en el IDE y revisa sus dependencias, puertos y configuración centralizada antes de ejecutar los servicios.

## Alcance

Material de aprendizaje de Spring Cloud. El POM raíz y los POM de los módulos contienen configuraciones de versiones propias; deben revisarse conjuntamente antes de intentar empaquetar el conjunto.
