# The Keycloak Journey — Code Examples

Code companion for the e-book **The Keycloak Journey: From Authentication Chaos to Enterprise IAM**, covering Red Hat Build of Keycloak (RHBK) 26.6.

Each directory maps to a chapter in the book. The examples follow the story of **Mediterranean Holdings**, a fictional corporation with five subsidiaries that progressively adopt Keycloak as their centralized identity platform.

## Repository Structure

| Directory | Chapter | What You Build |
|-----------|---------|----------------|
| `ch02/spa` | Getting to Know Keycloak | JavaScript SPA (Mediterranean Digital) authenticating via `keycloak-js` — the first proof of concept |

More chapters will be added as the book progresses.

## Prerequisites

| Software | Version | Notes |
|----------|---------|-------|
| Keycloak | 26.6.x (community) | For local development. RHBK 26.6 for OpenShift (Chapter 4+). |
| OpenJDK | 17, 21, or 25 | 21 recommended. Required for ZIP distribution. |
| Node.js | 22+ LTS | For JavaScript examples. |
| Docker or Podman | Latest | Optional — for container-based startup. |

## Quick Start

### Chapter 2 — Mediterranean Digital SPA

The SPA demonstrates the OAuth 2.0 Authorization Code flow with PKCE using `keycloak-js`.

**1. Start Keycloak**

Download the ZIP distribution from https://www.keycloak.org/downloads and extract it. Then:

```bash
bin/kc.sh start-dev
```

**2. Configure Keycloak**

Open http://localhost:8080 and create an admin account. Then:

- Create a realm called `mediterranean`
- Create a client called `med-digital` (Client type: OpenID Connect, Client authentication: OFF)
- Set the Valid redirect URI to `http://localhost:3000/*`
- Set the Web origin to `http://localhost:3000`
- Create a test user and set a password

**3. Run the application**

```bash
cd ch02/spa
npm install
npm start
```

Open http://localhost:3000 in a browser. The application redirects to the Keycloak login page. After authentication, you can inspect the ID token and access token.

## About the Book

The book follows Mediterranean Holdings — a company with five subsidiaries (Digital, Finance, Logistics, Corporate, Insurance) — on their journey from fragmented, homegrown authentication to a mature IAM platform built on Red Hat Build of Keycloak. Each chapter introduces Keycloak capabilities through the real problems the company faces, not as a feature reference.

**Target product:** Red Hat Build of Keycloak (RHBK) 26.6

**Author:** Marcelo Sales — Red Hat

## License

See [LICENSE](LICENSE).
