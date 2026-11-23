import Keycloak from '/keycloak-js/keycloak.js';

const greetingElement = document.getElementById("greeting");
const loginBtn = document.getElementById("loginBtn");
const logoutBtn = document.getElementById("logoutBtn");
const productTable = document.getElementById("product-table");
const emptyState = document.getElementById("empty-state");

const config = await fetch('/config').then(r => r.json());

function renderProducts(products) {
  emptyState.style.display = 'none';
  productTable.innerHTML = `
    <table class="table table-striped table-hover mb-0">
      <thead class="table-light">
        <tr><th>#</th><th>Name</th><th>Category</th><th>Price</th></tr>
      </thead>
      <tbody>
        ${products.map(p => `
          <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td><span class="badge bg-secondary">${p.category}</span></td>
            <td>$${p.price.toFixed(2)}</td>
          </tr>
        `).join('')}
      </tbody>
    </table>`;
}

function renderError(message) {
  emptyState.style.display = 'none';
  productTable.innerHTML = `<div class="alert alert-danger mb-0">${message}</div>`;
}

document
  .getElementById("loadProducts")
  .addEventListener("click", async () => {
    const headers = {};
    if (keycloak.authenticated) {
      headers["Authorization"] = "Bearer " + keycloak.token;
    }
    const response = await fetch(config.backendUrl + "/products", { headers });
    if (response.ok) {
      renderProducts(await response.json());
    } else {
      renderError("Failed to load products: " + response.status);
    }
  });

const keycloak = new Keycloak({
  url: config.keycloakUrl,
  realm: config.keycloakRealm,
  clientId: config.keycloakClientId
});

await keycloak.init({
  onLoad: 'check-sso',
  silentCheckSsoRedirectUri: `${location.origin}/silent-check-sso.html`
});

if (keycloak.authenticated) {
  greetingElement.textContent = `Logged in as ${keycloak.tokenParsed.preferred_username}`;
  loginBtn.style.display = 'none';
  logoutBtn.style.display = 'inline';

  if (keycloak.hasRealmRole('manager')) {
    document.getElementById('pricingSection').style.display = 'block';
  }
  if (keycloak.hasResourceRole('catalog-editor', 'med-digital-frontend')) {
    document.querySelectorAll('.edit-btn').forEach(btn => btn.style.display = 'inline');
  }
}

loginBtn.addEventListener("click", () => keycloak.login());
logoutBtn.addEventListener("click", () => keycloak.logout());
