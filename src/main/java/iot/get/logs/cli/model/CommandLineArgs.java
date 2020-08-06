package iot.get.logs.cli.model;

import lombok.Getter;
import lombok.Setter;

/**
 * コマンドライン引数.
 */
@Getter
@Setter
public class CommandLineArgs {

    /** 開始時間. */
    private String startTime;

    /** 終了時間. */
    private String endTime;

    /** リージョンID. */
    private String regionId;

    /** アクセスキー. */
    private String accessKey;

    /** シークレットキー. */
    private String secretKey;

    /** プロダクトキー. */
    private String productKey;

    /** デバイス名. */
    private String deviceName;

    /** ビジネスタイプ. */
    private String bizCode;

    /** 出力先フォルダ. */
    private String output;

    /** メッセージ追加フラグ. */
    private boolean addMessageFlag;
}
