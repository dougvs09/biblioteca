## Getting Started

### Prerequisites

Prerequisites for run the project
* Java 17+
* Maven 3+
* Docker
* Postman

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/dougvs09/biblioteca
   ```
2. Enter in folder /backend
3. Install dependencies
   ```sh
   mvn clean install
   ```
4. Run docker compose in folder /docker
   ```sh
   docker-compose up -d
   ```
5. Start the spring boot application
   ```sh
   mvn spring-boot:run
   ```

## Usage
In your postman, import the collection that is found in /postman and use the endpoints

