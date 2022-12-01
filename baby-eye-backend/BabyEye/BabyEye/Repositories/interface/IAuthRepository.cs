using BabyEye.Models;

namespace BabyEye.Repositories
{
    public interface IAuthRepository
    {
        public Task<int> AddRefreshToken(RefreshToken refreshToken);

        public Task<RefreshToken?> FirstOfNull(string refreshToken);

        public Task<int> UpdateRedreshToken(RefreshToken refreshToken);
    }
}
