package cl.duoc.backend_adopcion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HuachitosService {

    @Autowired
    private RestTemplate restTemplate;

    public Object getMascotasHuachitos() {
        String url = "https://huachitos.cl/api/animales";

        return restTemplate.getForObject(url, Object.class);
    }
}