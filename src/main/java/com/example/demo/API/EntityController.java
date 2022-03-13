package com.example.demo.API;

import javax.persistence.*;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.gameState.*;
import com.example.demo.utility.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RestController
public class EntityController {

	private AntibodyRepo antirepo;
	private virusRepo virusrepo;
	private EntityRepo entityRepo;

	public EntityController(){
		this.antirepo =
	}
	GameState state;
	Board board;

	public EntityController(int universe){
		state = GameState.instance(universe);
		board = Board.instance(universe);
	}

	@GetMapping("/viruses")
	Set<Virus> viruses(){
		return state.getVirusList();
	}

	@PostMapping("/viruses")
	void newVirus(@RequestBody Virus newVirus){

	}


}
