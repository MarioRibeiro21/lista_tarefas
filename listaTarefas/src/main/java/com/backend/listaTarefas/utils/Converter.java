package com.backend.listaTarefas.utils;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Converter {

	private static final ModelMapper mapper = new ModelMapper();

	public static <D> D convert( Object source, Class<D> destinationType ) {
		return mapper.map( source, destinationType );
	}

	public static <D> List<D> convertList(List<?> source, Class<D> destinationType) {
        Type listType = new TypeToken<List<D>>() {}.getType();
        return mapper.map(source, listType);
    }
}
