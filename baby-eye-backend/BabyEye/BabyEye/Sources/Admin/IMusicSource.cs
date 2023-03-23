using BabyEye.Db.Models.Songs;
using BabyEye.Utils.States;

namespace BabyEye.Sources.Admin
{
    public interface IMusicSource
    {
        public ICrudResult GetGenresAsync();

        public Task<ICrudResult> CreateGenreAsync(Genre genre);

        public Task<ICrudResult> DeleteGenreAsync(int genreId);
    }
}
