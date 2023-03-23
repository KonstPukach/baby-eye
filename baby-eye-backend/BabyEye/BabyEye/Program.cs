using BabyEye.Db;
using BabyEye.Models;
using BabyEye.Repositories;
using BabyEye.Repositories.Admin;
using BabyEye.Security;
using BabyEye.Security.TokenValidation;
using BabyEye.Sources.Admin;
using BabyEye.Utils;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Text;

var builder = WebApplication.CreateBuilder(args);
var configuration = builder.Configuration;

builder.Services.AddControllersWithViews();
builder.Services.AddEndpointsApiExplorer();

builder.Services.AddRazorPages();

// Db setup
builder.Services.AddDbContext<AppDatabaseContext>(options => 
    options.UseSqlServer(configuration.GetConnectionString("DefaultConnection")));

builder.Services
    .AddIdentity<User, IdentityRole>(options => options.SignIn.RequireConfirmedAccount = true)
    .AddEntityFrameworkStores<AppDatabaseContext>()
    .AddDefaultTokenProviders();

var tokenValidationParameters = new TokenValidationParameters
{
    ValidateIssuerSigningKey = true,
    IssuerSigningKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(configuration["JWT:Secret"])),
    ValidateIssuer = false,
    ValidateAudience = false,
    RequireExpirationTime = true,
    ValidateLifetime = true,
    ClockSkew = TimeSpan.Zero       // temp
};


builder.Services.AddSingleton(tokenValidationParameters);

builder.Services.AddSingleton<JwtParams>();
builder.Services.AddSingleton<ITokenFactory, JwtTokenFactory>();
builder.Services.AddSingleton<IRefreshTokenFactory, RefreshTokenFactory>();
builder.Services.AddSingleton<IRefreshTokenValidator, RefreshTokenValidator>();
builder.Services.AddSingleton<IAccessTokenValidator, AccessJwtTokenValidator>();
builder.Services.AddSingleton<IAuthRepository, AuthRepository>();
builder.Services.AddSingleton<IRefreshTokenRequestValidator, RefreshJwtTokenRequestValidator>();
builder.Services.AddScoped<IMusicSource, MusicSource>();
builder.Services.AddScoped<IMusicRepository, MusicRepository>();

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(jwt =>
{
    var key = Encoding.ASCII.GetBytes(configuration["JWT:Secret"]);

    jwt.SaveToken = true;
    jwt.TokenValidationParameters = tokenValidationParameters;
})
.AddCookie(CookieAuthenticationDefaults.AuthenticationScheme, options =>
{
    options.LoginPath = new PathString("/admin-auth/login");
    options.AccessDeniedPath = new PathString("/admin-auth/login");
});

builder.Services.AddSwaggerGen();

builder.WebHost.UseStaticWebAssets();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseRouting();
app.UseStaticFiles();

app.UseAuthentication();
app.UseAuthorization();

//app.MapControllers();
app.UseEndpoints(endpoints =>
{
    endpoints.MapControllerRoute(
        name: "default",
        pattern: "{controller=Admin}/{action=Index}/{id?}"
    );
});

app.Run();
