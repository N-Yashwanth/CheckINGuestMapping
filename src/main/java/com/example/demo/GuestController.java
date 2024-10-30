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
@RequestMapping("/guests")
public class GuestController {
	@Autowired
	private GuestRepo repo;
	@GetMapping
	public List<Guest> getALL(){
		return repo.findAll();
	}
	@GetMapping("/{id}")
	public Guest getByID(@PathVariable int id) {
		return repo.findById(id).orElseThrow();
	}
	@PostMapping
	public Guest save(@RequestBody Guest check) {
		return repo.save(check);
	}
	@PutMapping("/{id}")
	public Guest update(@PathVariable int id, @RequestBody Guest guest) {
		Guest guests=repo.findById(id).orElseThrow();
		guests.setName(guest.getName());
		guests.setCheck(guest.getCheck());
		return repo.save(guests);
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
	public List<Guest> pagination(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Guest> result=repo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<>();
	}
	@GetMapping("/sort")
	public List<Guest> sorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return repo.findAll(Sort.by(sort, field));
	}
}
