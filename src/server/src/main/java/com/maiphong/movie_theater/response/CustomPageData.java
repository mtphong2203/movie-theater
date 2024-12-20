package com.maiphong.movie_theater.response;

import java.util.Collection;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageData<T> {
    private Collection<T> data;

    private Links links;

    private PagedModel.PageMetadata page;
}
