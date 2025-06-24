package com.hoangnam25.hnam_courseware.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SuperConverter<D, E> {
    public List<D> convertEntitiesToDTOs(final List<E> entities) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(entity -> convertToDTO(entity)).collect(Collectors.toList());
    }

    public List<E> convertDTOsToEntities(final List<D> dtos) {
        if (dtos.isEmpty()) {
            return new ArrayList<>();
        }
        return dtos.stream().map(dto -> convertToEntity(dto)).collect(Collectors.toList());
    }


    public abstract D convertToDTO(final E entity);

    public abstract E convertToEntity(final D dto);

}
