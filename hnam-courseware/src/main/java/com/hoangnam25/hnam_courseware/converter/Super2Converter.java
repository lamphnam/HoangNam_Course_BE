package com.hoangnam25.hnam_courseware.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Super2Converter<Request, Response, E> {

    public List<Response> convertEntitiesToResponseDTOs(final List<E> entities) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<E> convertRequestDTOsToEntities(final List<Request> dtos) {
        if (dtos.isEmpty()) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::convertRequestToEntity).collect(Collectors.toList());
    }

    public List<Request> convertEntitiesToRequestDTOs(final List<E> entities) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::convertEntityToRequest).collect(Collectors.toList());
    }


    public abstract Response convertToResponseDTO(final E entity);

    public abstract E convertRequestToEntity(final Request dto);

    public abstract Request convertEntityToRequest(final E request);
}