package org.example.test.converter;

import java.util.List;

public interface Converter<E, D> {


    public D convert(E e);

    public List<D> convert(List<E> e);
}
