using BabyEye.Models;
using BabyEye.Utils;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace BabyEye.Security
{
    public class JwtTokenFactory : ITokenFactory
    {
        private readonly JwtParams _jwtParams;
        private readonly JwtSecurityTokenHandler _jwtSecurityTokenHandler = new();

        public JwtTokenFactory(JwtParams jwtParams)
        {
            _jwtParams = jwtParams;
        }

        public SecurityToken CreateToken(User user)
        {
            var authClaims = new ClaimsIdentity(new List<Claim>
            {
                new Claim("Id", user.Id),
                new Claim(JwtRegisteredClaimNames.Name, user.UserName),
                new Claim(JwtRegisteredClaimNames.Email, user.Email),
                new Claim(JwtRegisteredClaimNames.Sub, user.Email),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            });

            SecurityTokenDescriptor tokenDescriptor = new()
            {
                Subject = authClaims,
                Expires = _jwtParams.ExpirationTermHours,
                SigningCredentials = new SigningCredentials(_jwtParams.SecurityKey, SecurityAlgorithms.HmacSha512)
            };

            return _jwtSecurityTokenHandler.CreateToken(tokenDescriptor);
        }
    }
}
