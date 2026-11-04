# The Keycloak Journey - Code Examples

Code companion for the e-book **The Keycloak Journey: From Authentication Chaos to Enterprise IAM**, covering Red Hat Build of Keycloak (RHBK) 26.6.

Each directory maps to a chapter in the book. The examples follow the story of **Mediterranean Holdings**, a fictional corporation with five subsidiaries that progressively adopt Keycloak as their centralized identity platform.

## Repository Structure

| Directory | Chapter | Description |
|-----------|---------|-------------|
| [`ch02/`](ch02/) | Getting to Know Keycloak | JavaScript SPA (Mediterranean Digital) authenticating via `keycloak-js` |

More chapters will be added as the book progresses. Each chapter directory has its own README with prerequisites and setup instructions.

## General Prerequisites

| Software | Version | Notes |
|----------|---------|-------|
| Keycloak | 26.6.x (community) | For local development. RHBK 26.6 for OpenShift (Chapter 4+). |
| OpenJDK | 17, 21, or 25 | 21 recommended. Required for ZIP distribution. |
| Node.js | 22+ LTS | For JavaScript examples. |
| Docker or Podman | Latest | Optional - for container-based startup. |

Chapter-specific prerequisites and quick start instructions are in each chapter's README.

## About the Book

The book follows Mediterranean Holdings - a company with five subsidiaries (Digital, Finance, Logistics, Corporate, Insurance) - on their journey from fragmented, homegrown authentication to a mature IAM platform built on Red Hat Build of Keycloak. Each chapter introduces Keycloak capabilities through the real problems the company faces, not as a feature reference.

**Target product:** Red Hat Build of Keycloak (RHBK) 26.6

**Author:** Marcelo Sales - Red Hat

## License

See [LICENSE](LICENSE).
