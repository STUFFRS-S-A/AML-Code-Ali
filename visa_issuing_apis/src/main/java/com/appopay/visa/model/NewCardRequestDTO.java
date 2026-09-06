package com.appopay.visa.model;

import lombok.Data;

@Data
public class NewCardRequestDTO {

    private Hdr hdr;
    private Envt envt;

    @Data
    public static class Hdr {
        private String prtcolVrsn;
        private MsgIdentfctn msgIdentfctn;

        @Data
        public static class MsgIdentfctn {
            private String clientId;
            private String correlatnId;
        }
    }

    @Data
    public static class Envt {
        private Card card;

        @Data
        public static class Card {
            private String pan;
            private String seed;
            private String cardTp;
            private String xpryDt;
            private int cardSeqNb;
        }
    }
}

