using BabyEye.Db.Models.Songs;
using BabyEye.Models.Request.Admin;

namespace BabyEye.ViewModels
{
    public class GenresViewModel
    {
        public List<GenreModel> Genres{ get; set; }

        public GenresViewModel(List<Genre> genres)
        {
            Genres = genres.Select((genre) => new GenreModel()
            {
                Name = genre.Name,
                Id = genre.Id
            }).ToList();
        }
    }
}
