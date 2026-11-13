from flask import Flask, render_template

app = Flask(__name__)


@app.route("/")
def index():
    return render_template("index.html", user=None)


@app.route("/reports")
def reports():
    data = [
        {"id": "FIN-2024-001", "description": "Q4 Revenue Summary", "status": "Final"},
        {"id": "FIN-2024-002", "description": "Annual Tax Filing", "status": "Draft"},
        {"id": "FIN-2024-003", "description": "Budget Forecast 2025", "status": "Under Review"},
    ]
    return render_template("reports.html", user=None, reports=data)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5001, debug=True)
