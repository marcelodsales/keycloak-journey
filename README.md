# The Keycloak Journey - Code Examples

Code repository for the e-book **The Keycloak Journey: From Authentication Chaos to Enterprise IAM**, covering Keycloak 26.8.2.

Each directory maps to a chapter in the book. The examples follow the story of **Mediterranean Holdings**, a fictional corporation whose subsidiaries progressively adopt Keycloak as their centralized identity platform.

## Repository Structure

| Directory | Chapter | Description |
|-----------|---------|-------------|
| [`ch02/`](ch02/) | Getting to Know Keycloak | JavaScript frontend (Mediterranean Digital) authenticating via `keycloak-js`. Realm export. |
| [`ch03/`](ch03/) | Proof of Concept | Four applications, three teams: Digital frontend (JS + keycloak-js), Digital backend (Quarkus OIDC), Finance (Python + Flask + Authlib), Logistics (Spring Boot + SAML2) |
| [`ch04/`](ch04/) | The Shared Environment and the SSO Surprise | Kubernetes deployment: Keycloak Operator (OLM), PostgreSQL 18, cert-manager TLS, NGINX Ingress. [OpenShift guide](ch04/final/openshift/guide/) with validated RHBK config. |
| [`ch05/`](ch05/) | The Need for Control | RBAC: realm roles, client roles, groups, composite roles. Mediterranean Health (.NET 10). Realm export. |
| [`ch06/`](ch06/) | Bringing Everyone In | User Federation (LDAP/AD), Identity Providers (Entra ID, Google, healthcare federation). First production go-live. |
| [`ch07/`](ch07/) | Making It Ours | Themes and branding: login, account, email templates. Containerfile, theme deployment. |
| [`ch08/`](ch08/) | Friction by Design | Authentication flows, MFA (OTP), Step-up Authentication (ACR/LoA). |
| [`ch09/`](ch09/) | When Roles Are Not Enough | Authorization Services: resources, scopes, policies, permissions. RPT vs decision mode. |
| [`ch10/`](ch10/) | When Configuration Is Not Enough | SPIs: Custom User Storage, Authenticator (geo-velocity), Event Listener. |
| [`ch11/`](ch11/) | The Night Shift | Offline Tokens, Token Exchange V2, FAPI 1.0 Baseline. |
| [`ch12/`](ch12/) | Watching the Watchers | Observability: metrics, events, Kafka listener, Grafana dashboards. |
| [`ch13/`](ch13/) | The Enterprise Path | Organizations, multi-realm hybrid architecture, GitOps, Admin API. |
| [`ch14/`](ch14/) | What If This Goes Down? | HA single-cluster, Infinispan Server cross-site, multi-site. |

From Chapter 3 onwards, each chapter provides a `starter/` directory (before integration) and a `final/` directory (after all steps) where applicable.

## General Prerequisites

| Software | Version | Notes |
|----------|---------|-------|
| Keycloak | 26.8.2 (community) | For local development and Kubernetes deployment. |
| OpenJDK | 17, 21, or 25 | 21 recommended. 25 required for ZIP distribution. |
| Node.js | 22+ LTS | For JavaScript examples. |
| Python | 3.12+ | For Flask examples (Chapter 3+). |
| Podman | Latest | Container runtime. Used from Chapter 3 onwards. |
| Maven | 3.9+ | For Java examples. Wrapper (`mvnw`) included in each project. |
| Kubernetes | 1.30+ | For Chapter 4 onwards. Any distribution with OLM and NGINX Ingress Controller. |
| Helm | 3.x | For NGINX Ingress Controller and cert-manager installation. |

Chapter-specific prerequisites and quick start instructions are in each chapter's README.

## OpenShift Users

Organizations using Red Hat OpenShift and Red Hat Build of Keycloak (RHBK) can follow the [OpenShift deployment guide](ch04/final/openshift/guide/) in Chapter 4. The guide includes validated YAML manifests and screenshots for deploying RHBK via the OperatorHub on OpenShift.

## About the Book

Mediterranean Holdings is a fictional company with subsidiaries in e-commerce (Digital), financial services (Finance), logistics (Logistics), corporate services (Corporate), insurance (Insurance), and healthcare (Health). The book follows their journey from fragmented, homegrown authentication to a mature identity platform built on Keycloak. Each chapter introduces Keycloak capabilities through the real problems the company faces, not as a feature reference.

**Target product:** Keycloak 26.8.2 (community edition)

**Author:** Marcelo Sales

## License

See [LICENSE](LICENSE).
