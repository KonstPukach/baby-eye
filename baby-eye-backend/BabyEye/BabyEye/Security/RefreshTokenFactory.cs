using BabyEye.Models;
using System;

namespace BabyEye.Security
{
    public class RefreshTokenFactory : IRefreshTokenFactory
    {
        private const int RANDOM_STR_LEN = 25;

        public RefreshToken CreateToken(User user, string accessTokenId)
        {
            return new RefreshToken()
            {
                JwtId = accessTokenId,
                IsUsed = false,
                UserId = user.Id,
                AddedDate = DateTime.UtcNow,
                ExpiryDate = DateTime.UtcNow.AddYears(1),
                IsRevoked = false,
                Token = GenerateRandomString() + Guid.NewGuid()
            };
        }

        private string GenerateRandomString()
        {
            var random = new Random();
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, RANDOM_STR_LEN)
                .Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}
