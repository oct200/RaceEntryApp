using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Repository
{
    public interface IRepositoryInscriere : IRepository<Inscriere,Pair<long,long>>
    {
    }
}
