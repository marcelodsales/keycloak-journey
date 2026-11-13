const greetingElement = document.getElementById("greeting");
const loginBtn = document.getElementById("loginBtn");
const logoutBtn = document.getElementById("logoutBtn");
const productTable = document.getElementById("product-table");
const emptyState = document.getElementById("empty-state");

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
    const response = await fetch("http://localhost:8081/products");
    if (response.ok) {
      renderProducts(await response.json());
    } else {
      renderError("Failed to load products: " + response.status);
    }
  });
