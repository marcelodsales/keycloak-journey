# Chapter 3 - Proof of Concept

Three teams integrate their applications with Keycloak: Daniel with a JavaScript frontend and a Quarkus backend (OIDC), Camila with a Python Flask application (OIDC confidential client), and Sofia with a Spring Boot application (SAML).

## What You Build

| Directory | Description |
|-----------|-------------|
| `starter/digital-frontend` | Mediterranean Digital frontend before Keycloak integration |
| `final/digital-frontend` | Mediterranean Digital frontend with `keycloak-js`, audience mapper, PKCE |
| `starter/digital-backend` | Mediterranean Digital Quarkus backend before token validation |
| `final/digital-backend` | Mediterranean Digital Quarkus backend with OIDC bearer token validation |
| `starter/finance-flask` | Mediterranean Finance Flask application before OIDC integration |
| `final/finance-flask` | Mediterranean Finance Flask application with Authlib OIDC (confidential client) |
| `starter/logistics-saml` | Mediterranean Logistics Spring Boot application before SAML integration |
| `final/logistics-saml` | Mediterranean Logistics Spring Boot application with Spring Security SAML2 |

Start from `starter`, follow the chapter steps, and compare your result with `final`.

## Prerequisites

| Software | Version |
|----------|---------|
| Keycloak | 26.6.x (community ZIP or container) |
| OpenJDK | 25 (for ZIP) or any (for container) |
| Node.js | 22+ LTS |
| Python | 3.12+ |
| Podman | Latest |
| Maven | 3.9+ (or use the included `mvnw` wrapper) |

## Quick Start

### 1. Start Keycloak

The chapter covers three ways to run Keycloak locally: ZIP distribution, `podman run`, and `podman compose` with PostgreSQL. Pick the one that matches the application you are working on.

### 2. Configure Keycloak

In the Admin Console (`http://localhost:8080`):

1. Create a realm called `mediterranean`
2. Create the clients and users as described in each section of the chapter

### 3. Run the applications

**Digital frontend (Node.js):**
```bash
cd ch03/starter/digital-frontend
npm install
npm start
```
Open http://localhost:3000.

**Digital backend (Quarkus):**
```bash
cd ch03/starter/digital-backend
./mvnw quarkus:dev
```
Listens on http://localhost:8081.

**Finance (Flask):**
```bash
cd ch03/starter/finance-flask
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
pip install Authlib requests
flask run --host=0.0.0.0 --port=5001
```
Open http://localhost:5001.

**Logistics (Spring Boot SAML):**
```bash
cd ch03/starter/logistics-saml
./mvnw spring-boot:run -DskipTests
```
Open http://localhost:8082.

## Notes

- The `finance-flask/app.py` uses `PASTE_THE_SECRET_HERE` as a placeholder for the client secret. Replace it with the value from the Credentials tab of the `med-finance` client in the Admin Console.
- The Logistics application requires Keycloak to be running on port 8080 with a SAML client `med-logistics` configured in the `mediterranean` realm.
