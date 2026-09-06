package com.appopay.visa.model;
import lombok.Data;

@Data
public class CardUpdateRequestDTO {

    private Hdr Hdr;
    private Envt Envt;

    @Data
    public static class Hdr {
        private String PrtcolVrsn;
        private MsgIdentfctn msgIdentfctn;

        @Data
        public static class MsgIdentfctn {
            private String clientId;
            private String correlatnId;
        }
    }

    @Data
    public static class Envt {
        private Card Card;

        @Data
        public static class Card {
            private String seed;
            private String xpryDt;
            private int cardSeqNb;
            private String PANRefIdr;
        }
    }
}