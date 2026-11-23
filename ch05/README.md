# Chapter 5 - The Need for Control

## Quick Start

Start Keycloak with PostgreSQL and the realm pre-configured:

```bash
cd starter
podman compose up -d
```

Or with Docker:

```bash
cd starter
docker compose up -d
```

Keycloak will be available at http://localhost:8080 after a few seconds.

The `starter/` directory contains the realm as it was at the end of Chapter 4. The `final/` directory contains the realm after all Chapter 5 changes.

## Credentials

### Keycloak Admin Console

| URL | Username | Password |
|-----|----------|----------|
| http://localhost:8080/admin | `admin` | `admin` |

### Realm Users (realm `mediterranean`)

| Username | Password | Subsidiary |
|----------|----------|------------|
| `daniel` | `password123` | Digital |
| `camila` | `password123` | Finance |
| `sofia` | `password123` | Logistics |
| `ana` | `password123` | Digital |
| `sara` | `password123` | Health (final only) |

### Confidential Clients

| Client ID | Secret |
|-----------|--------|
| `med-digital-backend` | `med-digital-backend-secret` |
| `med-finance` | `med-finance-secret` |
| `med-logistics` | `med-logistics-secret` |
| `med-health` | `med-health-secret` (final only) |

### Public Clients (no secret)

| Client ID | Redirect URI |
|-----------|-------------|
| `med-digital-frontend` | `http://localhost:3000/*` |

## Application Ports

| Application | Port | Framework |
|-------------|------|-----------|
| digital-frontend | 3000 | Node.js + Express |
| digital-backend | 8081 | Quarkus |
| finance-flask | 5001 | Python + Flask |
| logistics-saml | 8082 | Spring Boot |
| health-dotnet | 5002 | ASP.NET Core 10 |

## Stopping

```bash
podman compose down -v
```

The `-v` flag removes the database volume. Without it, the data persists between restarts.
