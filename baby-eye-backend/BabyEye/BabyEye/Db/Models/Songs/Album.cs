using Microsoft.EntityFrameworkCore.Metadata.Internal;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BabyEye.Db.Models.Songs
{
    public class Album
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        [Column(TypeName = "nvarchar(256)")]
        public string Name { get; set; }

        [Column(TypeName = "date")]
        public DateTime? DatePublished { get; set; }

        public List<Song> Songs { get; set; } = new();

        public List<Author> Authors { get; set; } = new();
    }
}
