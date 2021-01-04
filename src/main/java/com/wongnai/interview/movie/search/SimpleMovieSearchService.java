package com.wongnai.interview.movie.search;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		List<MovieData> movie_data = movieDataService.fetchAll();

		return movie_data.stream().filter(result -> {
			List<String> title = Arrays.asList(result.getTitle().toLowerCase().split(" "));
			return title.contains(queryText.toLowerCase());
		}).map(result -> {
			Movie movie = new Movie(result.getTitle());
			movie.getActors().addAll(result.getCast());
			return movie;
		}).collect(Collectors.toList());

	}
}
