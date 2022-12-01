using BabyEye.Models;
using BabyEye.Models.Response;

namespace BabyEye.Security.TokenValidation
{
    public interface IRefreshTokenValidator
    {
        AuthResult Validate(RefreshToken? refreshToken);
    }
}
