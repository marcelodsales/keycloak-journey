import Keycloak from "keycloak-js";

const outputElement = document.getElementById("output");
const nameElement = document.getElementById("name");
const userElement = document.getElementById("user");

function output(content) {
  if (typeof content === "object") {
    content = JSON.stringify(content, null, 2);
  }

  outputElement.textContent = content;
}

function showProfile() {
  const name =
    keycloak.idTokenParsed.name ||
    keycloak.idTokenParsed.preferred_username;

  nameElement.textContent = name;
  userElement.style.display = "block";
}

document.getElementById("logout").addEventListener("click", () => {
  keycloak.logout();
});

document.getElementById("showIdToken").addEventListener("click", () => {
  output(keycloak.idTokenParsed);
});

document
  .getElementById("showAccessToken")
  .addEventListener("click", () => {
    output(keycloak.tokenParsed);
  });

document
  .getElementById("refreshToken")
  .addEventListener("click", async () => {
    await keycloak.updateToken(-1);
    output(keycloak.idTokenParsed);
    showProfile();
  });

const keycloak = new Keycloak({
  url: "http://localhost:8080",
  realm: "mediterranean",
  clientId: "med-digital",
});

await keycloak.init({ onLoad: "login-required" });
showProfile();
