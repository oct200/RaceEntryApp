using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Repository
{
    internal interface IRepositoryUser:IRepository<User, long>
    {
        User getUserByUsername(string username);
    }
}
