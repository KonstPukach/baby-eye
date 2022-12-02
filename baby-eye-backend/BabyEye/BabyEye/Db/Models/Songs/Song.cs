using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BabyEye.Db.Models.Songs
{
    public class Song
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        [Column(TypeName = "varchar(256)")]
        public string Name { get; set; }

        public short? GenreId { get; set; }
        public Genre? Genre { get; set; }

        public List<Author> Authors { get; set; } = new();

        public Album? Album { get; set; }
    }
}
