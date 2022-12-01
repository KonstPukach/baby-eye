using BabyEye.Models;
using BabyEye.Models.Response;

namespace BabyEye.Security.TokenValidation
{
    public interface IRefreshTokenRequestValidator
    {
        public AuthResult ValidateToken(string? accessToken, RefreshToken? refreshToken);
    }
}
