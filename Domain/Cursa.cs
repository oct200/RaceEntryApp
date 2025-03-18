using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Cursa : Entity<long>
    {
        public int NumarParticipanti {  get; set; }
        public int CapMotor {  get; set; }

        public Cursa(long id, int nrPa, int cap) {
            this.id = id;
            this.NumarParticipanti = nrPa;
            this.CapMotor = cap;
        }
    }
}
