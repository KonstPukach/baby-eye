using BabyEye.Models;
using BabyEye.Models.Request;
using BabyEye.Models.Response;
using BabyEye.Repositories;
using BabyEye.Security;
using BabyEye.Security.TokenValidation;
using BabyEye.Utils;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;

namespace BabyEye.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly UserManager<User> _userManager;
        private readonly ITokenFactory _tokenFactory;
        private readonly IRefreshTokenFactory _refreshTokenFactory;
        private readonly IAuthRepository _authRepository;
        private readonly IRefreshTokenRequestValidator _refreshTokenRequestValidator;

        public AuthController(
            UserManager<User> userManager,
            ITokenFactory tokenFactory,
            IRefreshTokenFactory refreshTokenFactory,
            IAuthRepository authRepository,
            IRefreshTokenRequestValidator refreshTokenRequestValidator
        )
        {
            _userManager = userManager;
            _tokenFactory = tokenFactory;
            _refreshTokenFactory = refreshTokenFactory; 
            _authRepository = authRepository;
            _refreshTokenRequestValidator = refreshTokenRequestValidator;
        }

        [HttpPost]
        [Route("register")]
        public async Task<IActionResult> Register([FromBody] LoginModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(AuthResult.Error("Invalid payload"));
            }

            var userExists = await _userManager.FindByNameAsync(model.Email);
            
            if (userExists != null)
                return Conflict(AuthResult.Error("User already exists"));

            var user = new User()
            {
                Email = model.Email,
                SecurityStamp = Guid.NewGuid().ToString(),
                UserName = model.Email
            };

            var result = await _userManager.CreateAsync(user, model.Password);

            if (result.Succeeded)
            {
                var response = await CreateAuthResponse(user);
                return Ok(response);
            }
            else
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    AuthResult.Errors(result.Errors.Select(x => x.Description).ToList()));
            }
        }

        [HttpPost]
        [Route("login")]
        public async Task<IActionResult> Login([FromBody] LoginModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(AuthResult.Error("Invalid payload"));
            }
            var user = await _userManager.FindByNameAsync(model.Email);
            if (user != null && await _userManager.CheckPasswordAsync(user, model.Password))
            {
                return Ok(await CreateAuthResponse(user));
            }

            return Unauthorized();
        }

        [HttpPost]
        [Route("refresh-token")]
        public async Task<IActionResult> RefreshToken([FromBody] TokenRequest tokenRequest)
        {
            if (!ModelState.IsValid || tokenRequest.RefreshToken == null)
            {
                return BadRequest(AuthResult.Error("Invalid payload"));
            }

            var storedRefreshToken = await _authRepository.FirstOfNull(tokenRequest.RefreshToken);

            var validationResult = _refreshTokenRequestValidator.ValidateToken(tokenRequest.Token, storedRefreshToken);

            if (!validationResult.IsSuccess || storedRefreshToken == null)
            {
                return BadRequest(validationResult);
            }

            storedRefreshToken.IsUsed = true;
            var refreshTokenResult = _authRepository.UpdateRedreshToken(storedRefreshToken);
            var user = _userManager.FindByIdAsync(storedRefreshToken.UserId);
            await refreshTokenResult;
           
            return Ok(await CreateAuthResponse(await user));
        }

        private async Task<AuthResult> CreateAuthResponse(User user)
        {
            var accessToken = _tokenFactory.CreateToken(user);
            var refreshToken = _refreshTokenFactory.CreateToken(user, accessToken.Id);

            await _authRepository.AddRefreshToken(refreshToken);

            var result = AuthResult.Success();
            result.Token = new JwtSecurityTokenHandler().WriteToken(accessToken);
            result.RefreshToken = refreshToken.Token;

            return result;
        }
    }
}
