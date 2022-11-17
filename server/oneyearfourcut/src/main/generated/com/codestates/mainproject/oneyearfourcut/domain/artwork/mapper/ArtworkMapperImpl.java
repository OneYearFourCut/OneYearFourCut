package com.codestates.mainproject.oneyearfourcut.domain.artwork.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-17T01:35:49+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class ArtworkMapperImpl implements ArtworkMapper {

    @Override
    public Artwork artworkRequestDtoToArtwork(ArtworkRequestDto artworkRequestDto) {
        if ( artworkRequestDto == null ) {
            return null;
        }

        Artwork artwork = new Artwork();

        artwork.setTitle( artworkRequestDto.getTitle() );
        artwork.setContent( artworkRequestDto.getContent() );
        artwork.setImg( artworkRequestDto.getImg() );

        return artwork;
    }

    @Override
    public ArtworkResponseDto artworkToArtworkResponseDto(Artwork artwork) {
        if ( artwork == null ) {
            return null;
        }

        ArtworkResponseDto artworkResponseDto = new ArtworkResponseDto();

        if ( artwork.getArtworkId() != null ) {
            artworkResponseDto.setArtworkId( artwork.getArtworkId() );
        }
        artworkResponseDto.setTitle( artwork.getTitle() );
        artworkResponseDto.setContent( artwork.getContent() );

        return artworkResponseDto;
    }
}
