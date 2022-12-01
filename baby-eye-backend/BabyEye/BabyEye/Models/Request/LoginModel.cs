using System.ComponentModel.DataAnnotations;

namespace BabyEye.Models.Request
{
    public class LoginModel
    {
        [EmailAddress]
        [Required(ErrorMessage = "Email is required")]
        public string Email { get; private set; } = "";

        [Required(ErrorMessage = "Password is required")]
        public string Password { get; private set; } = "";

        public LoginModel(string email, string password)
        {
            Email = email; 
            Password = password;
        }
    }
}
