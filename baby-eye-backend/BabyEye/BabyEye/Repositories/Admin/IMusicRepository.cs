using BabyEye.Db.Models.Songs;
using BabyEye.Utils.States;

namespace BabyEye.Repositories.Admin
{
    public interface IMusicRepository
    {
        public ICrudResult GetGenresAsync();

        public Task<ICrudResult> CreateGenreAsync(Genre genre);

        public Task<ICrudResult> DeleteGenreAsync(int genreId);
    }
}
