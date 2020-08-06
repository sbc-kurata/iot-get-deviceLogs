package iot.get.logs.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * GetCustomerLogリクエストオブジェクト.
 */
@Getter
@Setter
public class GetCustomerLogRequest {

    /** リージョンID. */
    private String regionId;

    /** 取得対象ページ数. */
    private Integer pageNo;

    /** ページサイズ. */
    private Integer pageSize;

    /** プロダクトキー. */
    private String productKey;

    /** クエリ条件(現在無効). */
    private String queryCondition;

    /** 開始時刻. */
    private Long startTime;

    /** 終了時刻. */
    private Long endTime;

    /** ステータス(現在無効). */
    private String status;

    /** デバイス名. */
    private String deviceName;

    /** ビズネスタイプ. */
    private String bizCode;
}
