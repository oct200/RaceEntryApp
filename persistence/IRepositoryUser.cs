using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IRepositoryUser:IRepository<User, long>
    {
        User getUserByUsernameAndPassword(string username,string pass);
        User getUserByUsername(string username);
    }
}
