using BabyEye.Models.Response;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;

namespace BabyEye.Security.TokenValidation
{
    public class AccessJwtTokenValidator : IAccessTokenValidator
    {
        public AuthResult Validate(SecurityToken accessToken)
        {
            try
            {
                if (accessToken == null)
                    return AuthResult.Error("Access token doesn't exist");

                if (accessToken is JwtSecurityToken jwtSecurityToken)
                {
                    if (!jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha512, StringComparison.InvariantCultureIgnoreCase))
                    {
                        return AuthResult.Error("Access token has invalid security algorithm");
                    }
                }

                var expDate = UnixTimeStampToDateTime(accessToken.ValidTo.Second);
                if (expDate > DateTime.UtcNow)
                    return AuthResult.Error("We cannot refresh this since the token has not expired");
            }
            catch (Exception ex)
            {
                return AuthResult.Error(ex.Message);
            }

            return AuthResult.Success();
        }

        private DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            DateTime dtDateTime = new(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp).ToUniversalTime();
            return dtDateTime;
        }
    }
}
