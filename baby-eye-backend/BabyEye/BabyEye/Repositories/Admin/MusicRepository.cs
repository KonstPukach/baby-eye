using BabyEye.Db.Models.Songs;
using BabyEye.Sources.Admin;
using BabyEye.Utils.States;

namespace BabyEye.Repositories.Admin
{
    public class MusicRepository : IMusicRepository
    {
        private readonly IMusicSource _musicSource;

        public MusicRepository(IMusicSource musicSource) => _musicSource = musicSource;

        public ICrudResult GetGenresAsync() => _musicSource.GetGenresAsync();

        public Task<ICrudResult> CreateGenreAsync(Genre genre) => _musicSource.CreateGenreAsync(genre);

        public Task<ICrudResult> DeleteGenreAsync(int genreId) => _musicSource.DeleteGenreAsync(genreId);
    }
}
