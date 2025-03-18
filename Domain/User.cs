using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class User : Entity<long>
    {
        public string username {  get; set; }
        public string parola { get; set; }

        public User(long id, string username, string password)
        {
            this.id = id;
            this.username = username;
            this.parola = password;
        }
    }
}
