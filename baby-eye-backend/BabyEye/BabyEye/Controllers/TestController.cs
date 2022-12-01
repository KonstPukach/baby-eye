using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace BabyEye.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class TestController : Controller
    {
        [HttpGet]
        [Route("testGet")]
        public IActionResult TestGet()
        {
            var rand = new Random();
            return Ok(new TestModel(rand.Next().ToString()));
        }
    }

    public class TestModel
    {
        public TestModel(string value)
        {
            Value = value;  
        }

        public string Value { get; set; }
    }
}
