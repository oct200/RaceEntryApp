package Motociclete;

import jakarta.annotation.PostConstruct;
import org.example.CursaRepositoryInterface;
import org.model.Cursa;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("motociclete/curse")
public class RestControllerCurse {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private CursaRepositoryInterface repoCursa;

    @PostConstruct
    public void init() {
        System.out.println("RestControllerCurse is loaded.");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Cursa cursa) {
        System.out.println("POST /motociclete/curse");
        Long id = repoCursa.insert(cursa);
        if (id == -1) {
            return new ResponseEntity<>("Cursa could not be added.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Cursa created = repoCursa.getById(id);
        messagingTemplate.convertAndSend("/topic/updates", "Inserted: " + created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cursa> getAll() {
        System.out.println("GET /motociclete/curse");
        return repoCursa.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        System.out.println("GET /motociclete/curse/" + id);
        Cursa cursa = repoCursa.getById(id);
        if (cursa != null) {
            return new ResponseEntity<>(cursa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cursa not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cursa cursa) {
        System.out.println("PUT /motociclete/curse/" + id);
        try {
            repoCursa.updateById(id, cursa);
            messagingTemplate.convertAndSend("/topic/updates", "Updated ID: " + id);
            return new ResponseEntity<>("Cursa updated", HttpStatus.OK);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Update not supported", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        System.out.println("DELETE /motociclete/curse/" + id);
        try {
            repoCursa.deleteById(id);
            messagingTemplate.convertAndSend("/topic/updates", "Deleted ID: " + id);
            return new ResponseEntity<>("Cursa deleted", HttpStatus.OK);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>("Delete not supported", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}