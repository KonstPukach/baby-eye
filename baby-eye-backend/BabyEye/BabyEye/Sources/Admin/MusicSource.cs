using BabyEye.Db;
using BabyEye.Db.Models.Songs;
using BabyEye.Utils.States;
using Microsoft.EntityFrameworkCore;

namespace BabyEye.Sources.Admin
{
    public class MusicSource : IMusicSource
    {
        private readonly AppDatabaseContext _dbContext;

        public MusicSource(AppDatabaseContext dbContext)
        {
            _dbContext = dbContext;
        }

        public ICrudResult GetGenresAsync() => new Success<List<Genre>>(_dbContext.Genres.ToList());

        public async Task<ICrudResult> CreateGenreAsync(Genre genre)
        {
            try
            {
                var first = _dbContext.Genres.FirstOrDefaultAsync((dbGenre) => dbGenre.Name == genre.Name);
                await _dbContext.Genres.AddAsync(genre);

                if ((await first) == null)
                { 
                    await _dbContext.SaveChangesAsync();
                }
                else
                {
                    return new ElementAlreadyExists();
                }
            }
            catch
            {
                return new UnexpectedError();
            }

            return new Success<object>();
        }

        public async Task<ICrudResult> DeleteGenreAsync(int genreId)
        {
            var genreToDelete = _dbContext.Genres.FirstOrDefault((genre) => genre.Id == genreId);

            if (genreToDelete == null)
            {
                return new NotFound($"Element with id {genreId} is not found");
            }
            else
            {
                _dbContext.Genres.Remove(genreToDelete);
            }

            try
            {
                await _dbContext.SaveChangesAsync();
            }
            catch
            {
                return new UnexpectedError();
            }

            return new Success<object>();
        }
    }
}
