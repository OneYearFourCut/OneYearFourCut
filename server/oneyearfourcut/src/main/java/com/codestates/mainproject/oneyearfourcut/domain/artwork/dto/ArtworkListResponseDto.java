package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkListResponseDto {
    private List<ArtworkResponseDto> artworkList;
}
