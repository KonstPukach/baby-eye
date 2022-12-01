namespace BabyEye.Models.Response
{
    public class AuthResult
    {
        public string? Token { get; set; }
        public string? RefreshToken { get; set; }
        public bool IsSuccess { get; set; }
        public List<string>? ErrorsList { get; set; }

        public static AuthResult Errors(List<string> errors)
        {
            return new AuthResult()
            {
                IsSuccess = false,
                ErrorsList = errors
            };
        }

        public static AuthResult Error(string error)
        {
            return new AuthResult()
            {
                IsSuccess = false,
                ErrorsList = new List<string> { error }
            };
        }

        public static AuthResult Success()
        {
            return new AuthResult()
            {
                IsSuccess = true
            };
        }
    }
}
