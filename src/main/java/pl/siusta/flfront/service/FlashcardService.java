package pl.siusta.flfront.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.siusta.flfront.model.FlashcardList;

import java.util.List;

@Service
public class FlashcardService {

    @Value("${get.address}")
    String get;

    @Value("${post.address}")
    String post;

    public List<FlashcardList> getFlashcardsLists() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> json = restTemplate.getForEntity(get, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<FlashcardList> flashcardLists = objectMapper.readValue(json.getBody(), new TypeReference<List<FlashcardList>>() {
        });
        return flashcardLists;
    }

    public Boolean createFlashcardList(FlashcardList f)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FlashcardList> result = restTemplate.postForEntity( post, f, FlashcardList.class);
        if(result.getStatusCodeValue()==200)return true;
        return false;
    }

}
