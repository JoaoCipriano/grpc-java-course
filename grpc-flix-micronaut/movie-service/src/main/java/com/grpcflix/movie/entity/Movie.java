package com.grpcflix.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_movie")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private int id;
    private String title;
    private int year;
    private double rating;
    private String genre;
}