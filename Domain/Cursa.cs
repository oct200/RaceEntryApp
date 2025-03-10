using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Cursa : Entity<long>
    { 
        int numarParticipanti {  get; set; }
        int capMotor {  get; set; }

        public Cursa(long id, int nrPa, int cap) {
            this.id = id;
            this.numarParticipanti = nrPa;
            this.capMotor = cap;
        }
    }
}
