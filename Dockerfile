# Etapa 1: Usar uma imagem base oficial com Java 21
FROM openjdk:21-jdk-slim

# Etapa 2: Definir o diretório de trabalho dentro do container
WORKDIR /app

# Etapa 3: Copiar os arquivos do Maven para baixar as dependências
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# A SOLUÇÃO ESTÁ AQUI: Dar permissão de execução ao mvnw
RUN chmod +x mvnw

# Etapa 4: Baixar todas as dependências (esta camada será cacheada para builds mais rápidos)
RUN ./mvnw dependency:go-offline

# Etapa 5: Copiar o código-fonte da sua aplicação
COPY src ./src

# Etapa 6: Compilar o projeto e empacotar em um arquivo .jar
RUN ./mvnw package -DskipTests

# Etapa 7: Expor a porta que sua aplicação usa
EXPOSE 8082

# Etapa 8: Definir o comando para iniciar sua aplicação quando o container rodar
ENTRYPOINT ["java", "-jar", "target/mercado-da-vila-0.0.1-SNAPSHOT.jar"]