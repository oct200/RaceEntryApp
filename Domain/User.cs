using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class User : Entity<long>
    {
        string username {  get; set; }
        string password { get; set; }

        public User(long id, string username, string password)
        {
            this.id = id;
            this.username = username;
            this.password = password;
        }
    }
}
