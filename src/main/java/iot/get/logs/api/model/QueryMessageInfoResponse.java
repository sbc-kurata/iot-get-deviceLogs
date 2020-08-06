package iot.get.logs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * QueryMessageInfoのレスポンス.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryMessageInfoResponse {

    /** リクエストが成功したか否か. */
    @JsonProperty("Success")
    private Boolean success;

    /** メッセージ. */
    @JsonProperty("Message")
    private Message message;

    /**
     * メッセージ.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        /** 本文(Base64形式). */
        @JsonProperty("MessageContent")
        private String messageContent;

        /** 生成時間. */
        @JsonProperty("GenerateTime")
        private Long generateTime;

        /** メッセージID. */
        @JsonProperty("UniMessageId")
        private String uniMessageId;

        /** トピック名. */
        @JsonProperty("TopicFullName")
        private String topicFullName;
    }
}
