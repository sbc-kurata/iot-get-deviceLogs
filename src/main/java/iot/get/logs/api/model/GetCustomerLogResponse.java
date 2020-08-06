package iot.get.logs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * GetCustomerLogレスポンスオブジェクト.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustomerLogResponse {

    /** リクエストID. */
    @JsonProperty("RequestId")
    private String requestId;

    /** コード. */
    @JsonProperty("Code")
    private String code;

    /** 成功したかどうか. */
    @JsonProperty("Success")
    private boolean success;

    /** ログ情報. */
    @JsonProperty("CustomerLog")
    private CustomerLog customerLog;

    /**
     * ログ情報.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerLog {

        /** Progress. */
        @JsonProperty("Progress")
        private int progress;

        /** 合計ページ数. */
        @JsonProperty("TotalPage")
        private int totalPage;

        /** 1ページ当たりのサイズ. */
        @JsonProperty("PageSize")
        private int pageSize;

        /** 取得したページ. */
        @JsonProperty("CurrentPage")
        private int CurrentPage;

        /** 合計ログ数. */
        @JsonProperty("Count")
        private int count;

        /** ログ. */
        @JsonProperty("Logs")
        private Logs logs;
    }

    /**
     * ログ.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Logs {

        /** ログのリスト. */
        @JsonProperty("CustomerLogItem")
        private List<CustomerLogItem> customerLogItems;
    }

    /**
     * ログレコード.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerLogItem {

        /** ステータス. */
        @JsonProperty("Status")
        private String status;

        /** インスタンスID. */
        @JsonProperty("InstanceId")
        private String instanceId;

        /** 時間. */
        @JsonProperty("Time")
        private String time;

        /** 操作. */
        @JsonProperty("Operation")
        private String operation;

        /** 結果コード. */
        @JsonProperty("Code")
        private String code;

        /** 理由. */
        @JsonProperty("Reason")
        private String reason;

        /** UTC時間. */
        @JsonProperty("UtcTime")
        private String utcTime;

        /** コンテンツ情報. */
        @JsonProperty("Content")
        private String content;

        /** トレースID. */
        @JsonProperty("TraceId")
        private String traceId;

        /** プロダクトキー. */
        @JsonProperty("ProductKey")
        private String productKey;

        /** ビジネスタイプ. */
        @JsonProperty("BizCode")
        private String bizCode;

        /** デバイス名. */
        @JsonProperty("DeviceName")
        private String deviceName;

        /** メッセージID. */
        @JsonProperty("MessageId")
        private String messageId;
    }
}
