import express from 'express';

const app = express();
const port = process.env.PORT || 3000;

app.use((req, res, next) => {
  res.set('Cache-Control', 'no-store, no-cache, must-revalidate');
  res.set('Pragma', 'no-cache');
  res.set('Expires', '0');
  next();
});

app.get('/config', (req, res) => {
  res.json({
    keycloakUrl: process.env.KEYCLOAK_URL || 'http://localhost:8080',
    keycloakRealm: process.env.KEYCLOAK_REALM || 'mediterranean',
    keycloakClientId: process.env.KEYCLOAK_CLIENT_ID || 'med-digital-frontend',
    backendUrl: process.env.BACKEND_URL || 'http://localhost:8081'
  });
});

app.use('/keycloak-js', express.static('node_modules/keycloak-js/lib'));
app.use('/', express.static('public'));

app.listen(port, () => {
  console.log(`Listening on port ${port}.`);
});
