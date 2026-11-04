# Chapter 2 - Getting to Know Keycloak

The first proof of concept. Daniel, the lead developer at Mediterranean Digital, sets up Keycloak locally and integrates the e-commerce platform using `keycloak-js`.

## What You Build

A JavaScript Single Page Application (SPA) that authenticates users through Keycloak using the OAuth 2.0 Authorization Code flow with PKCE. After login, the application displays the authenticated user's name and lets you inspect the ID token and access token.

| Directory | Description |
|-----------|-------------|
| `spa/` | Mediterranean Digital - public OIDC client using `keycloak-js` |

## Prerequisites

| Software | Version |
|----------|---------|
| Keycloak | 26.6.x (community ZIP distribution) |
| OpenJDK | 17, 21, or 25 (21 recommended) |
| Node.js | 22+ LTS |

## Quick Start

### 1. Start Keycloak

Download the ZIP distribution from https://www.keycloak.org/downloads and extract it. Then:

```bash
bin/kc.sh start-dev
```

Open http://localhost:8080 and create an admin account.

### 2. Configure Keycloak

In the Admin Console:

1. Create a realm called `mediterranean`
2. Create a client:
   - **Client ID:** `med-digital`
   - **Client type:** OpenID Connect
   - **Client authentication:** OFF (public client)
   - **Valid redirect URIs:** `http://localhost:3000/*`
   - **Web origins:** `http://localhost:3000`
3. Create a test user and set a password (disable *Temporary* toggle)

### 3. Run the application

```bash
cd spa
npm install
npm start
```

Open http://localhost:3000 in a browser. The application redirects to the Keycloak login page. After authentication, use the buttons to inspect the ID token and access token.

## What to Look For

- The **ID token** contains the user's identity claims (`sub`, `name`, `preferred_username`, `email`).
- The **access token** contains the `realm_access.roles` array and the `aud` (audience) claim.
- The **Refresh** button obtains new tokens without a new login, demonstrating the refresh token flow.
- The **Logout** button ends the Keycloak session and redirects back to the login page.
