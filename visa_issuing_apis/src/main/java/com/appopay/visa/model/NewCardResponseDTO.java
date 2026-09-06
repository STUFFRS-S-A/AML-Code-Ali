package com.appopay.visa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
public class NewCardResponseDTO {

    private Hdr hdr;
    private Envt envt;
    private List<Rslts> rslts;
    private PrcgRslt prcgRslt;

    @Data
    public static class Hdr {
        private String prtcolVrsn;
        private MsgIdentfctn msgIdentfctn;

        @Data
        public static class MsgIdentfctn {
            private String id;
            private String reqstId;
            private String clientId;
            private String correlatnId;
        }
    }

    @Data
    public static class Envt {
        private Card card;

        @Data
        public static class Card {
            private String panRefIdr;
            private String panFourLastDgts;
        }
    }

    @Data
    public static class Rslts {
        private String tp;
        private String rslt;
    }

    @Data
    public static class PrcgRslt {
        private String rsltDtls;
    }
}
