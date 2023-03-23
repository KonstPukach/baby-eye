using Microsoft.AspNetCore.Mvc;

namespace BabyEye.Controllers.Admin
{
    abstract public class AdminControllerBase : Controller
    {
        protected Enum CurrentMenuItem { get; set; } = AdminMenuItem.Admins;

        protected void SwitchMenuItem(AdminMenuItem menuItem)
        {
            CurrentMenuItem = menuItem;
            ViewBag.CurrentMenuItem = MapAdminMenuItemToString(menuItem);
        }

        private string MapAdminMenuItemToString(AdminMenuItem menuItem)
        {
            return menuItem.ToString().ToLower();
        }

        protected enum AdminMenuItem
        {
            Admins,
            Music
        }
    }
}
