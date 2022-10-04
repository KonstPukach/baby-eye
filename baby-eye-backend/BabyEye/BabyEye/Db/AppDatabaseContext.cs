using BabyEye.Models;
using Microsoft.AspNetCore.DataProtection;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace BabyEye.Db
{
    public class AppDatabaseContext : IdentityDbContext<User>
    {
        public AppDatabaseContext(DbContextOptions<AppDatabaseContext> options) : base(options)
        { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
