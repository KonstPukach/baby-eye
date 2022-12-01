using System.ComponentModel.DataAnnotations;

namespace BabyEye.Models.Request
{
    public class TokenRequest
    {
        [Required]
        public string? Token { get; set; }
        [Required]
        public string? RefreshToken { get; set; }
    }
}
