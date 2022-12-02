using BabyEye.Db.Models.Songs;
using BabyEye.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace BabyEye.Db
{
    public class AppDatabaseContext : IdentityDbContext<User>
    {
        public virtual DbSet<RefreshToken> RefreshTokens { get; set; }

        public DbSet<Album> Albums { get; set; }
        public DbSet<Author> Authors { get; set; }
        public DbSet<Genre> Genres { get; set; }
        public DbSet<Song> Songs { get; set; }
        public DbSet<SongFragment> SongFragments { get; set; }

        public AppDatabaseContext(DbContextOptions<AppDatabaseContext> options) : base(options)
        { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
