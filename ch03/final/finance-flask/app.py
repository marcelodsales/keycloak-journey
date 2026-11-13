from flask import Flask, redirect, session, url_for, render_template
from authlib.integrations.flask_client import OAuth

app = Flask(__name__)
app.secret_key = "finance-dev-secret-key"

oauth = OAuth(app)
oauth.register(
    name="keycloak",
    client_id="med-finance",
    client_secret="PASTE_THE_SECRET_HERE",
    server_metadata_url="http://localhost:8080/realms/mediterranean/.well-known/openid-configuration",
    client_kwargs={"scope": "openid profile email"},
)


@app.route("/")
def index():
    user = session.get("user")
    return render_template("index.html", user=user)


@app.route("/login", methods=["GET", "POST"])
def login():
    redirect_uri = url_for("auth", _external=True)
    return oauth.keycloak.authorize_redirect(redirect_uri)


@app.route("/auth")
def auth():
    token = oauth.keycloak.authorize_access_token()
    session["user"] = token.get("userinfo")
    session["id_token"] = token.get("id_token")
    return redirect("/")


@app.route("/reports")
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


@app.route("/logout", methods=["POST"])
def logout():
    id_token = session.pop("id_token", None)
    session.pop("user", None)
    if id_token:
        metadata = oauth.keycloak.load_server_metadata()
        return redirect(
            metadata["end_session_endpoint"]
            + "?id_token_hint=" + id_token
            + "&post_logout_redirect_uri=" + url_for("index", _external=True)
        )
    return redirect("/")


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5001, debug=True)