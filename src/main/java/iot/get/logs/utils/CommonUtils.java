package iot.get.logs.utils;

import iot.get.logs.cli.CommandLineArgsOption;
import iot.get.logs.cli.model.CommandLineArgs;
import org.apache.commons.cli.CommandLine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * 独自Utils.
 */
public class CommonUtils {

    /**
     * 時間の文字列を、LocalDateTimeに変換.
     *
     * @param timeString 時間 (yyyy-MM-dd HH:mm:ss)
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(timeString, formatter);
    }

    /**
     * LocalDateTimeをEpoch時間に変換.
     *
     * @param localDateTime 時間
     * @return Epoch時間
     */
    public static long localDateTimeToUnixTime(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneOffset.ofHours(+9));

        return zdt.toEpochSecond();
    }

    /**
     * コマンドライン引数の解決をする.
     *
     * @param commandLine コマンドライン引数
     * @return コマンドライン引数解決オブジェクト
     */
    public static CommandLineArgs resolveArgs(CommandLine commandLine) {
        CommandLineArgs args = new CommandLineArgs();

        args.setStartTime(commandLine.getOptionValue(CommandLineArgsOption.START_TIME.getShortName()));
        args.setEndTime(commandLine.getOptionValue(CommandLineArgsOption.END_TIME.getShortName()));
        args.setRegionId(commandLine.getOptionValue(CommandLineArgsOption.REGION_ID.getShortName()));
        args.setAccessKey(commandLine.getOptionValue(CommandLineArgsOption.ACCESS_KEY.getShortName()));
        args.setSecretKey(commandLine.getOptionValue(CommandLineArgsOption.SECRET_KEY.getShortName()));
        args.setProductKey(commandLine.getOptionValue(CommandLineArgsOption.PRODUCT_KEY.getShortName()));
        if (commandLine.hasOption(CommandLineArgsOption.DEVICE_NAME.getShortName())) {
            args.setDeviceName(commandLine.getOptionValue(CommandLineArgsOption.DEVICE_NAME.getShortName()));
        }
        if (commandLine.hasOption(CommandLineArgsOption.BIZ_CODE.getShortName())) {
            args.setBizCode(commandLine.getOptionValue(CommandLineArgsOption.BIZ_CODE.getShortName()));
        }

        // 出力先は、デフォルト ./
        if (commandLine.hasOption(CommandLineArgsOption.OUTPUT.getShortName())) {
            args.setOutput(commandLine.getOptionValue(CommandLineArgsOption.OUTPUT.getShortName()));
        } else {
            args.setOutput("./");
        }

        args.setAddMessageFlag(commandLine.hasOption(CommandLineArgsOption.MESSAGE.getShortName()));

        return args;
    }

    /**
     * Base64デコードを実施する.
     *
     * @param str Base64形式の文字列
     * @return デコードした文字列
     */
    public static String base64Decode(String str) {
        if (str == null) {
            return null;
        }

        Charset charset = StandardCharsets.UTF_8;

        //デコード処理
        byte[] bytes = Base64.getDecoder().decode(str.getBytes());
        return new String(bytes, charset);
    }

    public static String getTimeString(String time) {
        return time.substring(0,4) + time.substring(5, 7) + time.substring(8, 10) + time.substring(11, 13) + time.substring(14, 16) + time.substring(17, 19);
    }
}
