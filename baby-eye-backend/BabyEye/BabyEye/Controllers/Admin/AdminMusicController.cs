using BabyEye.Db.Models.Songs;
using BabyEye.Models.Request.Admin;
using BabyEye.Repositories.Admin;
using BabyEye.Utils.States;
using BabyEye.ViewModels;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace BabyEye.Controllers.Admin
{
    [Authorize(AuthenticationSchemes = CookieAuthenticationDefaults.AuthenticationScheme, Roles = "admin")]
    public class AdminMusicController : AdminControllerBase
    {
        private readonly IMusicRepository _musicRepository;

        public AdminMusicController(IMusicRepository musicRepository)
        {
            _musicRepository = musicRepository;
        }

        [HttpGet]
        [Route("admin/music/")]
        public IActionResult Index()
        {
            SwitchMenuItem(AdminMenuItem.Music);
            return View("~/Views/Admin/AdminMusicPage.cshtml");
        }

        [HttpGet]
        [Route("admin/music/genres")]
        public IActionResult Genres()
        {
            SwitchMenuItem(AdminMenuItem.Music);
            return View("~/Views/Admin/Genres.cshtml");
        }

        [HttpGet]
        [Route("admin/music/genres-list")]
        public IActionResult GenresList()
        {
            var result = _musicRepository.GetGenresAsync();
            if (result.isSuccess)
            {
                GenresViewModel genresViewModel = new(((Success<List<Genre>>)result).Data ?? new List<Genre>());
                return new JsonResult(genresViewModel);
            } 
            else
            {
                return this.MapCrudResult(result);
            }
        }

        [HttpPost]
        [Route("admin/music/create-genre")]
        public async Task<IActionResult> CreateGenre([FromBody] GenreModel genre)
        {
            if (genre.Name == "" || genre.Name == null)
            {
                return BadRequest();
            }

            Genre genreObj = new() { Name = genre.Name };
            var result = await _musicRepository.CreateGenreAsync(genreObj);

            return this.MapCrudResult(result);
        }

        [HttpDelete]
        [Route("admin/music/delete-genre/{id:int}")]
        public async Task<IActionResult> DeleteGenre(int id)
        {
            var result = await _musicRepository.DeleteGenreAsync(id);
            return this.MapCrudResult(result);
        }
    }
}
