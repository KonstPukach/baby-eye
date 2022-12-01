using Microsoft.IdentityModel.Tokens;
using System.Text;
using System.Xml.Linq;

namespace BabyEye.Utils
{
    public class JwtParams
    {
        private readonly IConfiguration _configs;

        public JwtParams(IConfiguration configs)
        {
            _configs = configs;
        }

        public SymmetricSecurityKey SecurityKey 
        {
            get { 
                return new SymmetricSecurityKey(Encoding.ASCII.GetBytes(_configs["JWT:Secret"]));
            }
        }
       
        public string ValidIssuer
        {
            get { return _configs["JWT:ValidIssuer"]; }
        }

        public string ValidAudience
        {
            get { return _configs["JWT:ValidAudience"]; }
        }

        public DateTime ExpirationTermHours
        {
            get { return DateTime.Now.AddSeconds(int.Parse(_configs["JWT:ExpirationTermHours"])); }     // TODO
        }
    }
}
