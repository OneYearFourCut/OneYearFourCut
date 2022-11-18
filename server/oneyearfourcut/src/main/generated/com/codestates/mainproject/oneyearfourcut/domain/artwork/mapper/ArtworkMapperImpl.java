package com.codestates.mainproject.oneyearfourcut.domain.artwork.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.dto.ArtworkResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-17T22:19:56+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class ArtworkMapperImpl implements ArtworkMapper {

    @Override
    public Artwork artworkRequestDtoToArtwork(ArtworkRequestDto artworkRequestDto) {
        if ( artworkRequestDto == null ) {
            return null;
        }

        Artwork.ArtworkBuilder artwork = Artwork.builder();
        artwork.title( artworkRequestDto.getTitle() );
        artwork.content( artworkRequestDto.getContent() );

        return artwork.build();
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

    @Override
    public List<ArtworkResponseDto> artworkListToArtworkListResponseDto(List<Artwork> artworkList) {
        if ( artworkList == null ) {
            return null;
        }

        List<ArtworkResponseDto> list = new ArrayList<ArtworkResponseDto>( artworkList.size() );
        for ( Artwork artwork : artworkList ) {
            list.add( artworkToArtworkResponseDto( artwork ) );
        }

        return list;
    }
}
