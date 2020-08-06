package iot.get.logs.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.EnumSet;

/**
 * コマンドライン引数定義.
 */
@AllArgsConstructor
@Getter
public enum CommandLineArgsOption {

    /** 開始時刻. */
    START_TIME("st", "StartTime", true, true, "(必須)ログ取得対象開始時間 フォーマット: \"yyyy-MM-dd HH:mm:ss\" "),

    /** 終了時刻. */
    END_TIME("et", "EndTime", true, true, "(必須)ログ取得対象終了時間 フォーマット: \"yyyy-MM-dd HH:mm:ss\""),

    /** リージョンID. */
    REGION_ID("r", "Region", true, true, "(必須)リージョンID 例: cn-shanghai"),

    /** アクセスキー. */
    ACCESS_KEY("ak", "AccessKey", true, true, "(必須)APIアクセスキー"),

    /** シークレットキー. */
    SECRET_KEY("sk", "SecretKey", true, true, "(必須)APIシークレットキー"),

    /** プロダクトキー. */
    PRODUCT_KEY("p", "ProductKey", true, true, "(必須)IoTPlatform プロダクトキー"),

    /** デバイス名. */
    DEVICE_NAME("d", "DeviceName", false, true, "検索対象のデバイス名"),

    /** ビジネスタイプ. */
    BIZ_CODE("b", "BusinessType", false, true, "検索対象の実行タイプ  例: ruleengine"),

    /** 出力先フォルダ. */
    OUTPUT("o", "OutPut", false, true, "結果出力ディレクトリ"),

    /** ヘルプ. */
    MESSAGE("m", "Message", false, false, "メッセージ出力有無(時間がかかります)"),

    /** ヘルプ. */
    HELP("h", "Help", false, false, "ヘルプ出力");

    /** 引数名. */
    private final String shortName;

    /** 引数名(ロング). */
    private final String longName;

    /** 必須パラメータかどうか. */
    private final boolean required;

    /** 値を持つか. */
    private final boolean hasArgs;

    /** 説明. */
    private final String description;

    /**
     * コマンドライン引数定義作成.
     *
     * @return コマンドライン引数
     */
    public static Options createOption() {
        Options options = new Options();

        EnumSet.allOf(CommandLineArgsOption.class)
                .forEach(args ->
                    options.addOption(Option.builder(args.shortName)
                            .longOpt(args.longName)
                            .required(args.required)
                            .hasArg(args.hasArgs)
                            .desc(args.description)
                            .build())
                );

        return options;
    }
}
