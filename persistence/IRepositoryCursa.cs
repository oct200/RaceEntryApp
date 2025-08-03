using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IRepositoryCursa : IRepository<Cursa,long>
    {
        List<Cursa> getCurseByCap(int cap);
        void increaseNr(long id);
        List<int> getCapacitati();
    }
}
