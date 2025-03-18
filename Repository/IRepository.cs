using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Repository
{
    public interface IRepository<E,ID> where E : Entity<ID>
    {

        ID Insert(E entity);
        void DeleteById(ID id);
        void UpdateById(ID id, E entity);
        List<E> GetAll();
        E GetById(ID id);
        
    }
}
