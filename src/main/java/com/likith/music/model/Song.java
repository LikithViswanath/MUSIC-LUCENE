package com.likith.music.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private String songId;
    private String name;
    private String genre;
    private String artist;
    private String album;
    private double rating;
    private int ratingNum;
}
