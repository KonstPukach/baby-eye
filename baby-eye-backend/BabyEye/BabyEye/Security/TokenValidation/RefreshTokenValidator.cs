using BabyEye.Models;
using BabyEye.Models.Response;

namespace BabyEye.Security.TokenValidation
{
    public class RefreshTokenValidator : IRefreshTokenValidator
    {
        public AuthResult Validate(RefreshToken? refreshToken)
        {
            try
            {
                if (refreshToken == null)
                    return AuthResult.Error("Refresh token doesn't exist");

                // Check the date of the saved token if it has expired
                if (DateTime.UtcNow > refreshToken.ExpiryDate)
                    return AuthResult.Error("Token has expired, user needs to relogin");

                // check if the refresh token has been used
                if (refreshToken.IsUsed)
                    return AuthResult.Error("Token has been used");

                // Check if the token is revoked
                if (refreshToken.IsRevoked)
                    return AuthResult.Error("Token has been revoked");
            }
            catch (Exception ex)
            {
                return AuthResult.Error(ex.Message);
            }

            return AuthResult.Success();
        }
    }
}
