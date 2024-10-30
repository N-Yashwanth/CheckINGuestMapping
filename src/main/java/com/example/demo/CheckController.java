package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {
	@Autowired
	private CheckRepo repo;
	@GetMapping
	public List<CheckIN> getALL(){
		return repo.findAll();
	}
	@GetMapping("/{id}")
	public CheckIN getByID(@PathVariable int id) {
		return repo.findById(id).orElseThrow();
	}
	@PostMapping
	public CheckIN save(@RequestBody CheckIN check) {
		return repo.save(check);
	}
	@PutMapping("/{id}")
	public CheckIN update(@PathVariable int id, @RequestBody CheckIN check) {
		CheckIN checkIN=repo.findById(id).orElseThrow();
		checkIN.setInTime(check.getInTime());
		checkIN.setGuests(check.getGuests());
		return repo.save(checkIN);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return "Deleted Successfully";
		}
		else {
			return "Not Found";
		}
	}
	@GetMapping("/page/{pageNo}/{pageSize}")
	public List<CheckIN> pagination(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<CheckIN> result=repo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<>();
	}
	@GetMapping("/sort")
	public List<CheckIN> sorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return repo.findAll(Sort.by(sort, field));
	}
}
