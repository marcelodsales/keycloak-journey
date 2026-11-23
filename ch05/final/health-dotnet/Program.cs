using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.OpenIdConnect;
using Microsoft.IdentityModel.Protocols.OpenIdConnect;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = OpenIdConnectDefaults.AuthenticationScheme;
})
.AddCookie()
.AddOpenIdConnect(options =>
{
    options.Authority = builder.Configuration["Keycloak:Authority"];
    options.ClientId = builder.Configuration["Keycloak:ClientId"];
    options.ClientSecret = builder.Configuration["Keycloak:ClientSecret"];
    options.ResponseType = OpenIdConnectResponseType.Code;
    options.SaveTokens = true;
    options.GetClaimsFromUserInfoEndpoint = true;
    options.RequireHttpsMetadata = false;
    options.Scope.Add("openid");
    options.Scope.Add("profile");
    options.TokenValidationParameters.RoleClaimType = "roles";
    options.PushedAuthorizationBehavior = PushedAuthorizationBehavior.Disable;
});

builder.Services.AddTransient<IClaimsTransformation, KeycloakClaimsTransformation>();
builder.Services.AddAuthorization();

var app = builder.Build();

app.UseAuthentication();
app.UseAuthorization();

app.MapGet("/", () => "Mediterranean Health - Clinical Portal").AllowAnonymous();

app.MapGet("/dashboard", (HttpContext ctx) =>
{
    var name = ctx.User.Identity?.Name ?? "Unknown";
    return Results.Ok(new { message = $"Welcome, {name}", section = "Dashboard" });
}).RequireAuthorization();

app.MapGet("/appointments", (HttpContext ctx) =>
{
    return Results.Ok(new { section = "Appointments", data = new[]
    {
        new { Id = "APT-001", Patient = "Maria Santos", Time = "09:00", Status = "Confirmed" },
        new { Id = "APT-002", Patient = "João Almeida", Time = "10:30", Status = "Pending" },
        new { Id = "APT-003", Patient = "Clara Ferreira", Time = "14:00", Status = "Confirmed" }
    }});
}).RequireAuthorization(policy => policy.RequireRole("nurse", "doctor"));

app.MapGet("/medical-records", (HttpContext ctx) =>
{
    return Results.Ok(new { section = "Medical Records", data = new[]
    {
        new { Id = "MR-2024-001", Patient = "Maria Santos", Type = "Lab Results", Date = "2024-11-15" },
        new { Id = "MR-2024-002", Patient = "João Almeida", Type = "Imaging", Date = "2024-11-18" }
    }});
}).RequireAuthorization(policy => policy.RequireRole("doctor"));

app.MapGet("/staff-schedules", (HttpContext ctx) =>
{
    return Results.Ok(new { section = "Staff Schedules", data = new[]
    {
        new { Name = "Dr. Costa", Shift = "Morning", Department = "Cardiology" },
        new { Name = "Nurse Oliveira", Shift = "Night", Department = "Emergency" }
    }});
}).RequireAuthorization(policy => policy.RequireRole("clinic-admin"));

app.Run();
