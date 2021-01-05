package com.wongnai.interview.movie.sync;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;
import com.wongnai.interview.movie.external.MovieData;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		List<MovieData> movie_data = movieDataService.fetchAll();

		System.out.println(movie_data.get(0).getTitle());
		movie_data.stream()
			.map(result -> {
				Movie movie = new Movie(result.getTitle());
				movie.getActors().addAll(result.getCast());
				return movie; 
			})
			.forEach(movie -> movieRepository.save(movie));
	}
}
