using BabyEye.Models;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;

namespace BabyEye.Controllers.Admin
{
    public class AdminAuthController : Controller
    {
        private readonly UserManager<User> _userManager;

        public AdminAuthController(UserManager<User> userManager)
        {
            _userManager = userManager;
        }

        [HttpGet]
        [Route("admin-auth/login")]
        public IActionResult Login()
        {
            return View("~/Views/Admin/Login.cshtml");
        }

        [HttpGet]
        [Route("admin-auth/login/error")]
        public IActionResult Login(string errorText)
        {
            ViewBag.ErrorText = errorText;
            return View("~/Views/Admin/Login.cshtml");
        }

        [HttpPost]
        public async Task<IActionResult> LoginWithPassword(string emailField, string passwordField)
        {
            User user = await _userManager.FindByEmailAsync(emailField);
            if (user == null)
                return Login("User is not found");

            if (!await _userManager.CheckPasswordAsync(user, passwordField))
                return Login("Invalid password");

            var userRoles = await _userManager.GetRolesAsync(user);
            if (!userRoles.Contains(UserRole.Admin.Name))
                return Login("User doesn't have admin permissions");

            var claims = new List<Claim>
            {
                new Claim(ClaimsIdentity.DefaultNameClaimType, user.Email),
                new Claim(ClaimsIdentity.DefaultRoleClaimType, UserRole.Admin.Name)
            };

            ClaimsIdentity claimsIdentity = new(
                claims,
                "AdminCookie",
                ClaimsIdentity.DefaultNameClaimType,
                ClaimsIdentity.DefaultRoleClaimType);

            await HttpContext.SignInAsync(
                CookieAuthenticationDefaults.AuthenticationScheme,
                new ClaimsPrincipal(claimsIdentity));

            return RedirectToAction("Index", "Admin");
        }

        [HttpPost]
        [Authorize(AuthenticationSchemes = CookieAuthenticationDefaults.AuthenticationScheme, Roles = "admin")]
        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Login");
        }
    }
}
