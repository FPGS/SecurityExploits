package es.gorka.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gorka.edu.dto.AuthorDTO;
import es.gorka.edu.repository.AuthorRepository;


@Service
public class AuthorService {
	
	@Autowired
	AuthorRepository repository;
	
	public boolean insertNewAuthor(AuthorDTO authorDto) {
		repository.insertNewAuthor(authorDto);
		return true;
	}
	
	public ArrayList<AuthorDTO> findAuthors(AuthorDTO authorDto) {
		return repository.findAuthors(authorDto);
	}
}
