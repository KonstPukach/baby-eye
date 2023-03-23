using BabyEye.Utils.States;
using Microsoft.AspNetCore.Mvc;

namespace BabyEye.Controllers.Admin
{
    public static class AdminUtils
    {
        public static IActionResult MapCrudResult(this ControllerBase controller, ICrudResult result)
        {
            return result switch
            {
                Success<object> => controller.Ok(((Success<object>)result).Data),
                NotFound => controller.NotFound(),
                ElementAlreadyExists => controller.Conflict(),
                UnexpectedError => controller.StatusCode(500),
                _ => controller.StatusCode(500)
            };
        }
    }
}
