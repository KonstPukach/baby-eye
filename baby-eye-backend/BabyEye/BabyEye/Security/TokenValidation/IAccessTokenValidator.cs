using BabyEye.Models.Response;
using Microsoft.IdentityModel.Tokens;

namespace BabyEye.Security.TokenValidation
{
    public interface IAccessTokenValidator
    {
        public AuthResult Validate(SecurityToken accessToken);
    }
}
