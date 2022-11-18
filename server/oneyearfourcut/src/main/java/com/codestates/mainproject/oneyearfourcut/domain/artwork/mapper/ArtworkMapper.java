package com.codestates.mainproject.oneyearfourcut.domain.artwork.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtworkMapper {
    Artwork artworkRequestDtoToArtwork(ArtworkRequestDto artworkRequestDto);

    List<ArtworkResponseDto> artworkListToArtworkListResponseDto(List<Artwork> artworkList);

    ArtworkResponseDto artworkToArtworkResponseDto(Artwork artwork);
}
