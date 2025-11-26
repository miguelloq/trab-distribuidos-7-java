# Music Streaming API - Java Version

Multi-protocol music streaming API service built with Java and Spring Boot, supporting **four communication protocols**: REST, gRPC, GraphQL, and SOAP. All protocols operate on the same domain model and service layer.

**Data Model:**
```
Usuario (id, nome, idade)
    ↓ 1:N
Playlist (id, nome, usuario_id)
    ↓ N:M
Musica (id, nome, artista)
```

## Build and Run

```bash
# Build and start all services (PostgreSQL + App)
docker-compose up -d --build

# View application logs
docker-compose logs -f app

# Stop services
docker-compose down

# Build without Docker (requires PostgreSQL running)
./gradlew build

# Run tests
./gradlew test
```

**Important:** When building, the protobuf plugin generates gRPC classes from `src/main/proto/music_streaming.proto`. This happens automatically during Gradle build.

## Service Ports

- **8080**: REST API (context path: `/api`)
- **9090**: gRPC server
- **8080/graphql**: GraphQL endpoint
- **8080/graphiql**: GraphiQL UI
- **8080/ws**: SOAP endpoint
- **8080/ws/musicStreaming.wsdl**: SOAP WSDL
- **5432**: PostgreSQL

## Architecture

### Layer Structure

1. **Model Layer** (`model/`): JPA entities with bidirectional relationships
   - `Usuario` ↔ `Playlist` (OneToMany/ManyToOne)
   - `Playlist` ↔ `Musica` (ManyToMany via join table `playlist_musica`)

2. **Repository Layer** (`repository/`): Spring Data JPA repositories with custom JPQL queries
   - `PlaylistRepository.findByMusicaId()` - critical for cascade delete operations

3. **Service Layer** (`service/`): Business logic shared across all protocols
   - Returns DTOs, not entities
   - `MusicaService.deletar()` removes música from all playlists before deletion

4. **Protocol Layers** (parallel, protocol-specific):
   - **REST** (`controller/`): Standard Spring @RestController
   - **gRPC** (`grpc/`): Uses protobuf messages, implements generated service base class
   - **GraphQL** (`graphql/`): Uses `@QueryMapping` and `@MutationMapping` annotations
   - **SOAP** (`soap/`): Uses `@Endpoint` with `@PayloadRoot`, JAXB classes defined inline

## Testing APIs

### REST
```bash
curl http://localhost:8080/api/usuarios
curl http://localhost:8080/api/musicas
curl -X POST http://localhost:8080/api/musicas -H "Content-Type: application/json" -d '{"nome":"Test","artista":"Artist"}'
```

### GraphQL
Access GraphiQL at `http://localhost:8080/graphiql` or:
```bash
curl http://localhost:8080/graphql -H "Content-Type: application/json" -d '{"query":"{ musicas { id nome artista } }"}'
```

### SOAP
WSDL available at `http://localhost:8080/ws/musicStreaming.wsdl`

### gRPC
Use Postman with gRPC support or a gRPC client tool pointing to `localhost:9090`

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- gRPC + Protobuf
- GraphQL (Spring for GraphQL)
- SOAP (Spring Web Services)
- Docker & Docker Compose
