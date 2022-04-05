# Seresco API REST

Por favor siga cualquiera de las opciones detalladas abajo para ejecutar esta aplicación.

### Opción 1. Ejecutar la aplicación con Maven

Usaremos el goal `spring-boot` para compilar la aplicación y ejecutarla:

```shell
mvn spring-boot:run
```

La aplicación se ejecutará en el puerto `8080`. La ruta completa es `http://localhost:8080`.

### Opción 2. Ejecutar la aplicación como un contenedor Docker

### Construir la imagen Docker de la aplicación

Primero, construiremos la aplicación utilizando los goals `clean` y `package` de Maven. Después, construiremos la imagen
Docker.

```shell
mvn clean package
docker build -t seresco-api .
```

Ahora ejecutamos el contenedor proporcionándole un nombre y un puerto:

```shell
docker run --rm -p 8080:8080 --name seresco-api seresco-api
```

La ruta completa accesible desde el host es `http://localhost:8080`.