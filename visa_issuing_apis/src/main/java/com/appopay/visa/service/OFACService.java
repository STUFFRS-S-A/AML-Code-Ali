package com.appopay.visa.service;

import com.appopay.visa.entity.OFACEntity;
import com.appopay.visa.model.ApiResponseDTO;
import com.appopay.visa.model.ChangeOfacRequestDTO;
import com.appopay.visa.model.OfacRequestDTO;
import com.appopay.visa.model.PaginatedOfacDto;
import com.appopay.visa.repository.OFACRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OFACService {

    @Autowired
    private OFACRepository ofacRepository;
    public boolean isApproved(String name) {
        OFACEntity savedOFAC = ofacRepository.findByName(name);
        if (savedOFAC != null) {
            if (savedOFAC.getIsBLocked())
                return false;
            else {
                return true;
            }
        } else {
            OFACEntity ofac = new OFACEntity();
            ofac.setName(name);
            if (getMatches(name) > 0) {
                ofac.setIsBLocked(true);
                ofacRepository.save(ofac);
                return false;
            } else {
                ofac.setIsBLocked(false);
                ofacRepository.save(ofac);
                return true;
            }
        }
    }
    public Integer getMatches(String name) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OfacRequestDTO ofacRequestDTO = new OfacRequestDTO();
            ofacRequestDTO.setApiKey("9724e637-bbac-4446-abdc-85736aa14344");
            ofacRequestDTO.setMinScore(95);
            ofacRequestDTO.setSources(List.of("SDN"));
            ofacRequestDTO.setTypes(List.of("person"));

            OfacRequestDTO.CaseDTO caseDTO = new OfacRequestDTO.CaseDTO();
            caseDTO.setName(name);
            caseDTO.setType("individual");
            ofacRequestDTO.setCases(List.of(caseDTO));
            // Convert OfacRequestDTO instance to JSON string
            String jsonString = objectMapper.writeValueAsString(ofacRequestDTO);

            RequestBody body = RequestBody.create(
                    jsonString,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url("https://api.ofac-api.com/v4/screen")
                    .post(body)
                    .build();


            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP status code: " + response.code());
            }

            // Parse JSON response into ApiResponse object
            ApiResponseDTO apiResponse = objectMapper.readValue(response.body().string(), ApiResponseDTO.class);

            Integer matchCount = apiResponse.getResults().get(0).getMatchCount();
            System.out.println("ofac match count");
            System.out.println(matchCount);
            return matchCount;

        } catch (Exception e) {
            System.out.println("Exception");
        }

        return 0;
    }
    public String getStatus(String name) throws Exception {
        OFACEntity ofacEntity = ofacRepository.findByName(name);
        if(ofacEntity == null)
            return "NOT FOUND";
        if(ofacEntity.getIsBLocked())
            return "BLOCKED";
        else return "UNBLOCKED";
    }
    public String changeStatus(ChangeOfacRequestDTO request) throws Exception {
        OFACEntity ofacEntity = ofacRepository.findByName(request.getName());
        if(ofacEntity == null)
            return "NOT FOUND";
        ofacEntity.setIsBLocked(request.getIsBLocked());
        ofacRepository.save(ofacEntity);
        return "blocked " + ofacEntity.getIsBLocked();
    }

    public PaginatedOfacDto getAll(Pageable pageable){
        PaginatedOfacDto response = new PaginatedOfacDto();
        response.setData(ofacRepository.findAll(pageable).stream().map(OFACEntity::toDTO).collect(Collectors.toList()));
        response.setDocuments(ofacRepository.count());
        return response;

    }

}
