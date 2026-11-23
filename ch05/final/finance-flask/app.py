import os
import json
import base64
from functools import wraps
from flask import Flask, redirect, session, url_for, render_template, abort
from authlib.integrations.flask_client import OAuth
from werkzeug.middleware.proxy_fix import ProxyFix

app = Flask(__name__)
app.wsgi_app = ProxyFix(app.wsgi_app, x_for=1, x_proto=1, x_host=1)
app.config["PREFERRED_URL_SCHEME"] = os.environ.get("PREFERRED_URL_SCHEME", "http")
app.secret_key = os.environ.get("FLASK_SECRET_KEY", "finance-dev-secret-key")

keycloak_url = os.environ.get("KEYCLOAK_URL", "http://localhost:8080")
keycloak_realm = os.environ.get("KEYCLOAK_REALM", "mediterranean")
client_id = os.environ.get("CLIENT_ID", "med-finance")
client_secret = os.environ.get("CLIENT_SECRET", "med-finance-secret")

oauth = OAuth(app)
oauth.register(
    name="keycloak",
    client_id=client_id,
    client_secret=client_secret,
    server_metadata_url=f"{keycloak_url}/realms/{keycloak_realm}/.well-known/openid-configuration",
    client_kwargs={"scope": "openid profile email"},
)


def decode_token_payload(token_string):
    payload = token_string.split('.')[1]
    padding = 4 - len(payload) % 4
    payload += '=' * padding
    return json.loads(base64.urlsafe_b64decode(payload))


def require_scope(scope_name):
    def decorator(f):
        @wraps(f)
        def wrapper(*args, **kwargs):
            if scope_name not in session.get('scopes', []):
                abort(403)
            return f(*args, **kwargs)
        return wrapper
    return decorator


@app.route("/")
def index():
    user = session.get("user")
    scopes = session.get("scopes", [])
    return render_template("index.html", user=user, scopes=scopes)


@app.route("/login", methods=["GET", "POST"])
def login():
    redirect_uri = url_for("auth", _external=True)
    return oauth.keycloak.authorize_redirect(
        redirect_uri,
        scope="openid finance:reports:read"
    )


@app.route("/auth")
def auth():
    token = oauth.keycloak.authorize_access_token()
    session["user"] = token.get("userinfo")
    session["id_token"] = token.get("id_token")
    access_claims = decode_token_payload(token['access_token'])
    session["scopes"] = access_claims.get("scope", "").split()
    return redirect("/")


@app.route("/reports")
@require_scope("finance:reports:read")
def reports():
    user = session.get("user")
    if not user:
        return redirect(url_for("login"))
    data = [
        {"id": "FIN-2024-001", "description": "Q4 Revenue Summary", "status": "Final"},
        {"id": "FIN-2024-002", "description": "Annual Tax Filing", "status": "Draft"},
        {"id": "FIN-2024-003", "description": "Budget Forecast 2025", "status": "Under Review"},
    ]
    return render_template("reports.html", user=user, reports=data)


@app.route("/transactions")
@require_scope("finance:transactions:read")
def transactions():
    user = session.get("user")
    if not user:
        return redirect(url_for("login"))
    data = [
        {"id": "TXN-2024-001", "description": "Supplier Payment - Raw Materials", "amount": -45000.00},
        {"id": "TXN-2024-002", "description": "Client Invoice - Project Alpha", "amount": 120000.00},
        {"id": "TXN-2024-003", "description": "Payroll - November", "amount": -89000.00},
    ]
    return render_template("transactions.html", user=user, transactions=data)


@app.route("/logout", methods=["POST"])
def logout():
    id_token = session.pop("id_token", None)
    session.pop("user", None)
    session.pop("scopes", None)
    if id_token:
        metadata = oauth.keycloak.load_server_metadata()
        return redirect(
            metadata["end_session_endpoint"]
            + "?id_token_hint=" + id_token
            + "&post_logout_redirect_uri=" + url_for("index", _external=True)
        )
    return redirect("/")


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 5001)), debug=True)
