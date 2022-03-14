package com.example.demo.API;

import javax.persistence.*;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.gameState.*;
import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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

	private final Factory factory = Factory.instance();
	private AntibodyRepo antirepo;
	private virusRepo virusrepo;
	private EntityRepo entityRepo;

	public EntityController(){
	}
	GameState state;
	Board board;

	@GetMapping("/{id}/state")
	public GameState state(@PathVariable int id){
		state = GameState.instance(id);
		Virus v = new Virus("",factory.newPair(2,2),1);
		return state;
	}

	public static void main(String[] args){
		SpringApplication.run(EntityController.class,args);
	}


}
