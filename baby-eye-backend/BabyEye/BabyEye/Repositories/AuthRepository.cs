using BabyEye.Db;
using BabyEye.Models;
using Microsoft.EntityFrameworkCore;

namespace BabyEye.Repositories
{
    public class AuthRepository : IAuthRepository
    {
        private readonly IServiceScopeFactory _scopeFactory;

        public AuthRepository(IServiceScopeFactory scopeFactory)
        {
            _scopeFactory = scopeFactory;
        }   

        public async Task<int> AddRefreshToken(RefreshToken refreshToken)
        {
            using (var scope = _scopeFactory.CreateScope())
            {
                var _dbContext = scope.ServiceProvider.GetRequiredService<AppDatabaseContext>();
                await _dbContext.RefreshTokens.AddAsync(refreshToken);
                return await _dbContext.SaveChangesAsync();
            }
        }

        public async Task<int> UpdateRedreshToken(RefreshToken refreshToken)
        {
            using (var scope = _scopeFactory.CreateScope())
            {
                var _dbContext = scope.ServiceProvider.GetRequiredService<AppDatabaseContext>();
                _dbContext.RefreshTokens.Update(refreshToken);
                return await _dbContext.SaveChangesAsync();
            }
        }

        public async Task<RefreshToken?> FirstOfNull(string refreshToken)
        {
            using (var scope = _scopeFactory.CreateScope())
            {
                var _dbContext = scope.ServiceProvider.GetRequiredService<AppDatabaseContext>();
                return await _dbContext.RefreshTokens.FirstOrDefaultAsync(token => (token.Token ?? "") == refreshToken);
            }
        }
    }
}
