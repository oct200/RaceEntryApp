using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Domain
{
    public class Pair<T, U>
    {
        public T First { get; }
        public U Second { get; }

        public Pair(T first, U second)
        {
            First = first;
            Second = second;
        }

        public override string ToString()
        {
            return $"({First}, {Second})";
        }
    }

}
