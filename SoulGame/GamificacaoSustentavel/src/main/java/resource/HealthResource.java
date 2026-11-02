package resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthResource {

    @GetMapping
    public ResponseEntity<String> verificarStatus() {
        return ResponseEntity.ok("API SoulUp funcionando!");
    }
}