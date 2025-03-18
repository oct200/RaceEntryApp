using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Inscriere : Entity<Pair<long,long>>
    {
        public Participant Participant {  get; set; }
        public Cursa Cursa { get; set; }

        public Inscriere(long id,Participant participant, Cursa cursa)
        {
            this.id = new Pair<long, long>(participant.id, cursa.id);
            this.Participant = participant;
            this.Cursa = cursa;
        }
    }
}
