package com.grpcflix.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "release_year")
    private int year;
    private double rating;
    private String genre;
}
