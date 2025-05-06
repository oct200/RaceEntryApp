using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IRepositoryInscriere : IRepository<Inscriere,Pair<long,long>>
    {
        List<Cursa> getCursePosibilePentruParticipant(Participant participant);
    }
}
