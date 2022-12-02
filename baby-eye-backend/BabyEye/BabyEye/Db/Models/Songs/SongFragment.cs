using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace BabyEye.Db.Models.Songs
{
    public class SongFragment
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        public int Duration { get; set; }

        [Required]
        public Song Song { get; set; }
    }
}
