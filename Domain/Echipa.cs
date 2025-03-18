using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Echipa : Entity<long>
    {
        public string nume {  get; set; }
        public Echipa(long id, string nume) {
            this.id = id;
            this.nume = nume;
        }
    }
}
