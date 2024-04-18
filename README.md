# FAKE STORE API [closed]

## Descripción

La API de Fake Store puede usarse con cualquier tipo de proyecto que necesite productos, usuarios, categorías, autenticación y usuarios en formato JSON.

Esta API incluye características como:

* ✅ Todas las operaciones CRUD
* ✅ API REST
* ✅ Paginación
* ✅ Autenticación con JWT
* ✅ Filtrar productos por categoría, título y rango de precios
* ✅ Crear usuarios y comprobar si ya existen
* ✅ Archivos de Postman e Insomnia
* ✅ Imágenes de productos generadas por IA


## Paquetes del proyecto

El proyecto está organizado en los siguientes paquetes:

- **com.fakestore.api.advice**: Contiene los controladores de excepciones. 
- **com.fakestore.api.dto**: Contiene los DTOs que se utilizan para la comunicación entre el cliente y el servidor (creación y respuesta).
- **com.fakestore.api.exception**: Contiene las excepciones personalizadas.
- **com.fakestore.api.initialization**: Contiene la clase necesaria para la inicialización de la base de datos (base de datos por defecto). 
- **com.fakestore.api.persistence.entity**: Contiene las entidades de la base de datos.
- **com.fakestore.api.persistence.repository**: Contiene los repositorios de las entidades de la base de datos. 
- **com.fakestore.api.security**: Contiene las clases necesarias para la seguridad de la aplicación (autenticación y autorización). 
- **com.fakestore.api.service**:  Contiene las clases de servicio de la aplicación.
- **com.fakestore.api.util**: Contiene las clases de utilidad de la aplicación. 
- **com.fakestore.api.web.controller**: Controladores para manejar las solicitudes HTTP de la API REST. 
- **com.fakestore.api.web.filter**: Filtros para interceptar las solicitudes HTTP.
- **com.fakestore.api.web.interceptor**:  Interceptores de las solicitudes HTTP.

