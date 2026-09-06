package com.example.didit.app.model.session;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class CreateSessionRequest {

    @NotBlank(message = "workflow_id is required")
    @JsonProperty("workflow_id")
    String workflowId;

    @JsonProperty("vendor_data")
    String vendorData;

    String callback;

    @JsonProperty("callback_method")
    String callbackMethod;

    String metadata;

    String language;

    @Valid
    @JsonProperty("contact_details")
    ContactDetails contactDetails;

    @Valid
    @JsonProperty("expected_details")
    ExpectedDetails expectedDetails;

    @JsonProperty("portrait_image")
    String portraitImage;


    @Data
    @Builder
    @Jacksonized
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
    public static class ContactDetails {

        String email;

        @JsonProperty("send_notification_emails")
        Boolean sendNotificationEmails;

        @JsonProperty("email_lang")
        String emailLang;

        String phone;
    }


    @Data
    @Builder
    @Jacksonized
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
    public static class ExpectedDetails {

        @JsonProperty("first_name")
        String firstName;

        @JsonProperty("last_name")
        String lastName;

        @JsonProperty("date_of_birth")
        String dateOfBirth;

        String gender;

        String nationality;

        @JsonProperty("id_country")
        String idCountry;

        @JsonProperty("poa_country")
        String poaCountry;

        String address;

        @JsonProperty("identification_number")
        String identificationNumber;

        @JsonProperty("ip_address")
        String ipAddress;

        @JsonProperty("expected_document_types")
        List<String> expectedDocumentTypes;
    }
}