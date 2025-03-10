using Motociclete.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motociclete.Repository
{
    public abstract class AbstractRepository<E,ID> where E : Entity<ID>
    {
        public AbstractRepository() { }

        public abstract void Insert(E entity);
        public abstract void DeleteById(ID id);
        public abstract void UpdateById(ID id, E entity);
        public abstract List<E> GetAll();
        public abstract E GetById(ID id);
        
    }
}
