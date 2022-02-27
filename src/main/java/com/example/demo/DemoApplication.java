package com.example.demo;

import javax.persistence.*;

import com.example.demo.entities.Antibody;
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

@SpringBootApplication
@RestController
public class DemoApplication {

	public DemoApplication(GameLoop gameLoop){
	}


}
