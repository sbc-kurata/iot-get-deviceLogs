package iot.get.logs.xls;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * ログ情報.
 */
@Getter
@Setter
public class LogItem {

    /** 時間. */
    @XlsColumn(columnName="Time")
    private String time;

    /** 操作. */
    @XlsColumn(columnName="Operation")
    private String operation;

    /** 結果コード. */
    @XlsColumn(columnName="Code")
    private String code;

    /** コンテンツ情報. */
    @XlsColumn(columnName="Content")
    private String content;

    /** トレースID. */
    @XlsColumn(columnName="TraceId")
    private String traceId;

    /** ビジネスタイプ. */
    @XlsColumn(columnName="Business type")
    private String bizCode;

    /** デバイス名. */
    @XlsColumn(columnName="DeviceName")
    private String deviceName;

    /** メッセージID. */
    @XlsColumn(columnName="MessageId")
    private String messageId;

    /** メッセージID. */
    @XlsColumn(columnName="Message", optional=true)
    private String rawMessage;
}
