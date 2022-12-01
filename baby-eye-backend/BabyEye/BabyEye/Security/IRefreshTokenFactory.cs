using BabyEye.Models;

namespace BabyEye.Security
{
    public interface IRefreshTokenFactory
    {
        public RefreshToken CreateToken(User user, string accessTokenId);
    }
}
