package dev.ifeoluwa.payaza.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.Data;

/**
 * @author on 25/03/2023
 * @project
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositResponse {
    private boolean status;
    private String message;
    private Data data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    @Getter
    @Setter
    public class Data {
        /**
         * this is the redirect url that the user would use to make the payment
         */
        private String authorization_url;
        /**
         * this code identifies the payment url
         */
        private String access_code;
        /**
         * the unique reference used to identify this transaction
         */
        private String reference;

    }


}
