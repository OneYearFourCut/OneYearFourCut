package com.codestates.mainproject.oneyearfourcut.domain.artwork.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtworkMapper {
    Artwork artworkRequestDtoToArtwork(ArtworkRequestDto artworkRequestDto);

    ArtworkResponseDto artworkToArtworkResponseDto(Artwork artwork);

}
