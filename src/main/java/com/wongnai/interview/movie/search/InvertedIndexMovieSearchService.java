package com.wongnai.interview.movie.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.naming.spi.DirStateFactory.Result;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	private Map<String, HashSet<Long>> Invertedindex; // "Trem" :"<id_set>"

	public void mapInvertedindex() {
		Invertedindex = new HashMap<String, HashSet<Long>>();
		movieRepository.findAll().forEach(result -> {
			String[] words = result.getName().split(" ");
			Long movie_id = result.getId();
			for (String word : words) {
				word = word.toLowerCase();
				if (!Invertedindex.containsKey(word)) {
					HashSet<Long> id_set = new HashSet<Long>();
					id_set.add(movie_id);
					Invertedindex.put(word,id_set);
				} else {
					Invertedindex.get(word).add(movie_id);
				}
			}
		});
	}

	@Override
	public List<Movie> search(String queryText) {
		// TODO: Step 4 => Please implement in-memory inverted index to search movie by
		// keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// | Term | Movie Ids |
		// -------------------------------
		// | Star | 5, 8, 1   |
		// | War  | 5, 2      |
		// | Trek | 1, 8      |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at
		// Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these
		// ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms,
		// Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for
		// Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for
		// just movie id 5.
		if(Invertedindex==null){
			mapInvertedindex();
		}

		String[] words = queryText.split(" ");
		HashSet<Long> movie_ids ;

		for(String temp:words){
			 if(!Invertedindex.containsKey(temp.toLowerCase())) {
				return Collections.emptyList();	
			} 
		}

		movie_ids = new HashSet<Long>(Invertedindex.get(words[0].toLowerCase()));
	 	for (String Term : words) {	
			movie_ids.retainAll(Invertedindex.get(Term.toLowerCase()));	
		 } 


		 return StreamSupport.stream(movieRepository.findAllById(movie_ids).spliterator(), false)
				.collect(Collectors.toList());
	}
}
