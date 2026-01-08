package fr.polytech.boitesalivres.backend.mappers;

import fr.polytech.boitesalivres.backend.dtos.ReservationRequest;
import fr.polytech.boitesalivres.backend.dtos.ReservationResponse;
import fr.polytech.boitesalivres.backend.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "boxId", source = "box.id")
    @Mapping(target = "boxName", source = "box.name")
    @Mapping(target = "reservation", source = "reservation")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "box.id", source = "boxId")
    @Mapping(target = "id", expression = "java(new fr.polytech.boitesalivres.backend.utils.keys.ReservationId(request.userId(), request.boxId()))")
    Reservation toEntity(ReservationRequest request);
}