using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IRepositoryParticipant : IRepository<Participant,long>
    {
        List<string> getEchipe();
        List<Participant> getAllByTeam(string team);
    }
}
