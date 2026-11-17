# The Keycloak Journey - Code Examples

Code companion for the e-book **The Keycloak Journey: From Authentication Chaos to Enterprise IAM**, covering Red Hat Build of Keycloak (RHBK) 26.6.

Each directory maps to a chapter in the book. The examples follow the story of **Mediterranean Holdings**, a fictional corporation with five subsidiaries that progressively adopt Keycloak as their centralized identity platform.

## Repository Structure

| Directory | Chapter | Description |
|-----------|---------|-------------|
| [`ch02/`](ch02/) | Getting to Know Keycloak | JavaScript SPA (Mediterranean Digital) authenticating via `keycloak-js` |
| [`ch03/`](ch03/) | Proof of Concept | Four applications, three teams: Digital frontend (JS + keycloak-js), Digital backend (Quarkus OIDC), Finance (Python + Flask + Authlib), Logistics (Spring Boot + SAML2) |

More chapters will be added as the book progresses. Each chapter directory has its own README with prerequisites and setup instructions. From Chapter 3 onwards, each chapter provides a `starter` directory (before integration) and a `final` directory (after all steps).

## General Prerequisites

| Software | Version | Notes |
|----------|---------|-------|
| Keycloak | 26.6.x (community) | For local development. RHBK 26.6 for OpenShift (Chapter 4+). |
| OpenJDK | 17, 21, or 25 | 21 recommended. 25 required for ZIP distribution. |
| Node.js | 22+ LTS | For JavaScript examples. |
| Python | 3.12+ | For Flask examples (Chapter 3). |
| Podman | Latest | Container runtime. Used from Chapter 3 onwards. |
| Maven | 3.9+ | For Java examples. Wrapper (`mvnw`) included in each project. |

Chapter-specific prerequisites and quick start instructions are in each chapter's README.

## About the Book

Mediterranean Holdings is a fictional company with five subsidiaries: Digital, Finance, Logistics, Corporate, and Insurance. The book follows their journey from fragmented, homegrown authentication to a mature IAM platform built on Red Hat Build of Keycloak. Each chapter introduces Keycloak capabilities through the real problems the company faces, not as a feature reference.

**Target product:** Red Hat Build of Keycloak (RHBK) 26.6

**Author:** Marcelo Sales

## License

See [LICENSE](LICENSE).
