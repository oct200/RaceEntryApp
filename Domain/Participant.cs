using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Participant : Entity<long>
    {
        public string Nume {  get; set; }
        public int CapMotor {  get; set; }
        public Echipa Echipa { get; set; }
        public string Cnp {  get; set; }

        public Participant(long id, string nume, int capMotor, Echipa echipa, string cnp)
        {
            this.id = id;
            this.Nume = nume;
            this.CapMotor = capMotor;
            this.Echipa = echipa;
            this.Cnp = cnp;
        }
    }
}
