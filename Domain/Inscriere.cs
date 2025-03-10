using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Inscriere : Entity<long>
    {
        Participant participant {  get; set; }
        Cursa cursa { get; set; }

        public Inscriere(long id,Participant participant, Cursa cursa)
        {
            this.id = id;
            this.participant = participant;
            this.cursa = cursa;
        }
    }
}
