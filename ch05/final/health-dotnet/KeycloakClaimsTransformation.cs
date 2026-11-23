using System.Security.Claims;
using System.Text.Json;
using Microsoft.AspNetCore.Authentication;

public class KeycloakClaimsTransformation : IClaimsTransformation
{
    private readonly string _clientId;

    public KeycloakClaimsTransformation(IConfiguration configuration)
    {
        _clientId = configuration["Keycloak:ClientId"] ?? "med-health";
    }

    public Task<ClaimsPrincipal> TransformAsync(ClaimsPrincipal principal)
    {
        var identity = (ClaimsIdentity)principal.Identity!;

        var resourceAccessClaim = principal.FindFirst("resource_access");
        if (resourceAccessClaim != null)
        {
            using var doc = JsonDocument.Parse(resourceAccessClaim.Value);
            if (doc.RootElement.TryGetProperty(_clientId, out var clientElement) &&
                clientElement.TryGetProperty("roles", out var rolesElement))
            {
                foreach (var role in rolesElement.EnumerateArray())
                {
                    var roleValue = role.GetString();
                    if (roleValue != null)
                    {
                        identity.AddClaim(new Claim(ClaimTypes.Role, roleValue));
                    }
                }
            }
        }

        var realmAccessClaim = principal.FindFirst("realm_access");
        if (realmAccessClaim != null)
        {
            using var doc = JsonDocument.Parse(realmAccessClaim.Value);
            if (doc.RootElement.TryGetProperty("roles", out var rolesElement))
            {
                foreach (var role in rolesElement.EnumerateArray())
                {
                    var roleValue = role.GetString();
                    if (roleValue != null && !identity.HasClaim(ClaimTypes.Role, roleValue))
                    {
                        identity.AddClaim(new Claim(ClaimTypes.Role, roleValue));
                    }
                }
            }
        }

        return Task.FromResult(principal);
    }
}
