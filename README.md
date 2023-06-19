# API Bancaria - Prueba de Concepto (POC)

Esta API Rest simula un pequeño banco permitiendo el registro de usuarios, creación de cuentas (wallets), realización de depósitos de dinero, visualización de cuentas con balances y movimientos, y transferencias de dinero entre cuentas. Además, como parte opcional, se integra con Blockchain para el manejo de transacciones.

## Características

- Registro de usuario con validaciones.
- Creación de billetera (wallet) asociada a un usuario.
- Realización de depósitos de dinero en una billetera.
- Visualización de billetera, incluyendo balance y movimientos.
- Transferencia de dinero entre billeteras.
- Integración opcional con Blockchain utilizando contratos inteligentes.
- Pruebas unitarias y manejo de errores.

## Tecnologías Utilizadas

- Java
- Spring Boot
- H2 Database (en memoria)
- Gradle
- Truffle (para Blockchain, opcional)
- Ganache (para Blockchain, opcional)
- Web3j (para interactuar con contratos inteligentes, opcional)

## Requisitos

- Java JDK 17 o superior.
- Gradle.
- (Opcional) Truffle y Ganache para la parte de Blockchain.

## Configuración y Ejecución

1. Clonar el repositorio: `git clone https://github.com/tomysx/api-bank.git`.
2. Asegurarse de estar en el directorio raíz del proyecto
3. Ejecutar el proyecto con Gradle: `./gradlew bootRun` (Linux/Mac) o `gradlew.bat bootRun` (Windows).
4. Acceder a la aplicación a través de un navegador web o herramienta de API (como Postman) en `http://localhost:8080`.

## Endpoints de la API

#### Crear usuario
POST http://localhost:8080/api/users

### Recuperar usuario mediante ID
GET http://localhost:8080/api/users/{id}

### Crear wallet

POST http://localhost:8080/api/wallets

### Depositar fondos en una wallet

PUT http://localhost:8080/api/wallets/{id}/deposit

### Recuperar saldo de una wallet

GET http://localhost:8080/api/wallets/{id}/balance

### Recuperar movimientos de una wallet

GET http://localhost:8080/api/wallets/{id}/movements

### Realizar transferencia de una wallet A hacia una wallet B

PUT http://localhost:8080/api/wallets/{id}/transfer/{toWalletId}

## Pruebas

Para ejecutar las pruebas unitarias, usa el siguiente comando:

./gradlew test

## Integración con Blockchain (Opcional)

Esta sección describe cómo se integra el proyecto con Blockchain utilizando Truffle y Ganache.

1. Asegúrate de tener Truffle y Ganache instalados.
2. Inicia Ganache.
3. Despliega los contratos inteligentes utilizando Truffle.
4. Configura las credenciales y la dirección del contrato en la clase `BlockchainService`.

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## Contacto

Para preguntas o colaboración, por favor contacta a [tomasfdezgar@gmail.com](mailto:tomasfdezgar@gmail.com).
