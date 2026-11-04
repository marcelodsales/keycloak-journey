import express from 'express';
import path from 'node:path';
import url from 'node:url';

const app = express();
const port = 3000;

app.use('/', express.static('public'));
app.use('/vendor', express.static(resolveDependencyDir('keycloak-js')));

app.listen(port, () => {
  console.log(`Listening on port ${port}.`);
});

function resolveDependencyDir(module) {
  const mainFile = url.fileURLToPath(import.meta.resolve(module));
  return path.dirname(mainFile);
}
