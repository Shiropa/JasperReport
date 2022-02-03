package org.example.test.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter<E, D> {

	private ModelMapper modelMapper;

	protected Converter() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	public D convert(E e) {
		return modelMapper.map(e, getDtoClass());
	}

	public List<D> convert(List<E> e) {
		if (e.isEmpty()) {
			return Collections.emptyList();
		}
		return e.stream().map(this::convert).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	private Class<D> getDtoClass() {
		return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}


}
