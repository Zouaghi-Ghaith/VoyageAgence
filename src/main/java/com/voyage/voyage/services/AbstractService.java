package com.voyage.voyage.services;
import java.util.List;

public interface AbstractService<T> {

    Integer save(T dto);

    List<T> findall();

    T findbyId (Integer id);

    void delete(Integer id );

}
