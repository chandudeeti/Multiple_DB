package com.login;

import com.login.book.repository.BookRepository;
import com.login.model.book.Book;
import com.login.model.user.User;
import com.login.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class MultipleDb {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void addData2DB()
	{
		userRepository.saveAll(Stream.of(new User(23,"chandu"), new User(34,"Srinivas")).collect(Collectors.toList()));
		bookRepository.saveAll(Stream.of(new Book(24,"java"),new Book(35,"spring")).collect(Collectors.toList()));
	}

	@GetMapping("/getUsers")
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	@GetMapping("/getBooks")
	public List<Book> getAllBooks()
	{
		return bookRepository.findAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(MultipleDb.class, args);
	}

}
