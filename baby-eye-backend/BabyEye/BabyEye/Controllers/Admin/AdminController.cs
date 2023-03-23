using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace BabyEye.Controllers.Admin
{
    [Authorize(AuthenticationSchemes = CookieAuthenticationDefaults.AuthenticationScheme, Roles = "admin")]
    public class AdminController : AdminControllerBase
    {
        [HttpGet]
        public IActionResult Index()
        {
            SwitchMenuItem(AdminMenuItem.Admins);
            return View("~/Views/Admin/Index.cshtml");
        }
    }
}
