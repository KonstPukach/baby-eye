using BabyEye.Models;
using BabyEye.Models.Response;
using BabyEye.Utils;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

namespace BabyEye.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly JwtParams _jwtParams;
        private readonly UserManager<User> _userManager;

        public AuthController(JwtParams jwtParams, UserManager<User> userManager)
        {
            _jwtParams = jwtParams;
            _userManager = userManager;
        }

        [HttpPost]
        [Route("register")]
        public async Task<IActionResult> Register([FromBody] LoginModel model)
        {
            var userExists = await _userManager.FindByNameAsync(model.Email);
            
            if (userExists != null)
                return Conflict("User already exists");

            var user = new User()
            {
                Email = model.Email,
                SecurityStamp = Guid.NewGuid().ToString(),
                UserName = model.Email
            };

            var result = await _userManager.CreateAsync(user, model.Password);

            if (!result.Succeeded)
                return StatusCode(StatusCodes.Status500InternalServerError);
            else 
                return Ok();
        }

        [HttpPost]
        [Route("login")]
        public async Task<IActionResult> Login([FromBody] LoginModel model)
        {
            var user = await _userManager.FindByNameAsync(model.Email);
            if (user != null && await _userManager.CheckPasswordAsync(user, model.Password))
            {
                var authClaims = new List<Claim>
                {
                    new Claim(ClaimTypes.Name, user.UserName),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                };

                var token = new JwtSecurityToken(
                    issuer: _jwtParams.ValidIssuer,
                    audience: _jwtParams.ValidAudience,
                    expires: _jwtParams.ExpirationTermHours,
                    claims: authClaims,
                    signingCredentials: new SigningCredentials(_jwtParams.SecurityKey, SecurityAlgorithms.HmacSha256)
                );

                return Ok(new TokenResponse
                {
                    Token = new JwtSecurityTokenHandler().WriteToken(token),
                    ExpireAt = token.ValidTo
                });
            }

            return Unauthorized();
        }
    }
}
