using BabyEye.Models;
using BabyEye.Models.Response;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;

namespace BabyEye.Security.TokenValidation
{
    public class RefreshJwtTokenRequestValidator : IRefreshTokenRequestValidator
    {
        private readonly IAccessTokenValidator _accessTokenValidator;
        private readonly IRefreshTokenValidator _refreshTokenValidator;
        private readonly TokenValidationParameters _validationParams;

        public RefreshJwtTokenRequestValidator(
            IRefreshTokenValidator refreshTokenValidator, 
            IAccessTokenValidator accessTokenValidator,
            TokenValidationParameters tokenValidationParameters
        )
        {
            _refreshTokenValidator = refreshTokenValidator;
            _accessTokenValidator = accessTokenValidator;   
            _validationParams = tokenValidationParameters;
        }

        public AuthResult ValidateToken(string? accessToken, RefreshToken? refreshToken)
        {
            var jwtTokenHandler = new JwtSecurityTokenHandler();
            var decodedAccessToken = jwtTokenHandler.ReadJwtToken(accessToken);

            var accessTokenValidationResult = _accessTokenValidator.Validate(decodedAccessToken);

            if (!accessTokenValidationResult.IsSuccess)
            {
                return accessTokenValidationResult;
            }

            var refreshTokenValidationResult = _refreshTokenValidator.Validate(refreshToken);

            if (!refreshTokenValidationResult.IsSuccess)
            {
                return refreshTokenValidationResult;
            }

            string jti = decodedAccessToken.Claims.Single(x => x.Type == JwtRegisteredClaimNames.Jti).Value;

            // check the id that the recieved token has against the id saved in the db
            if (refreshToken?.JwtId != jti)
                return AuthResult.Error("The token doenst mateched the saved token");

            return AuthResult.Success();
        }
    }
}
