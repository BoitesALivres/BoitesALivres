package fr.polytech.boitesalivres.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import fr.polytech.boitesalivres.backend.dtos.BoxResponse;
import fr.polytech.boitesalivres.backend.entities.Box;

@Mapper(componentModel = "spring")
public interface BoxMapper {
    BoxMapper INSTANCE = Mappers.getMapper(BoxMapper.class);

    @Mapping(target = "coordinateId", expression = "java(box.getCoordinate() != null ? box.getCoordinate().getId() : null)")
    @Mapping(target = "latitude", expression = "java(box.getCoordinate() != null ? box.getCoordinate().getLatitude() : null)")
    @Mapping(target = "longitude", expression = "java(box.getCoordinate() != null ? box.getCoordinate().getLongitude() : null)")
    BoxResponse toResponse(Box box);
}
