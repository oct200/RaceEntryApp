package org.example.Repository;

import org.example.Domain.Entity;

import java.util.List;

public interface RepoInterface<E extends Entity<ID>,ID> {
    public ID insert(E entity);
    public void updateById(ID id,E entity);
    public void deleteById(ID id);
    public E getById(ID id);
    public List<E> getAll();
}
