package es.gorka.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gorka.edu.dto.BookDTO;
import es.gorka.edu.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository repository;
	
	public boolean insertNewBook(BookDTO bookDto) {
		repository.insertNewBook(bookDto);
		return true;
	}

	public List<BookDTO> findBooks(BookDTO bookDto) {
		return repository.findBooks(bookDto);
	}
}
