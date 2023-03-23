namespace BabyEye.Models
{
    public class UserRole
    {
        public string Name { get; set; }

        public UserRole(string name) => Name = name;

        public static UserRole User = new("user");
        public static UserRole Admin = new("admin");
    }
}
