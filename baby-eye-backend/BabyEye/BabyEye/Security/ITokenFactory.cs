using BabyEye.Models;
using Microsoft.IdentityModel.Tokens;

namespace BabyEye.Security
{
    public interface ITokenFactory
    {
        public SecurityToken CreateToken(User user);
    }
}
