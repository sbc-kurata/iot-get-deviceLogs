package iot.get.logs;

import com.aliyuncs.utils.StringUtils;
import com.gh.mygreen.xlsmapper.XlsMapper;
import iot.get.logs.api.ClientManager;
import iot.get.logs.api.model.GetCustomerLogRequest;
import iot.get.logs.api.model.GetCustomerLogResponse;
import iot.get.logs.api.model.QueryMessageInfoRequest;
import iot.get.logs.api.model.QueryMessageInfoResponse;
import iot.get.logs.cli.CommandLineArgsOption;
import iot.get.logs.cli.model.CommandLineArgs;
import iot.get.logs.utils.CommonUtils;
import iot.get.logs.xls.LogItem;
import iot.get.logs.xls.LogSheet;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IoT Platformのデバイスログを取得するツール.
 */
public class GetLogMain {

    public static void main(String[] args) throws IOException {

        // コマンドライン引数定義取得
        Options options = CommandLineArgsOption.createOption();

        // 第一引数が -h か --Help ならHELP出力して終了
        if (args.length >= 1 && (args[0].equals("-h") || args[0].equals("--Help"))) {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("OptionsTip", options);
            System.exit(0);
        }

        // コマンドライン引数の解決
        CommandLineParser parser = new DefaultParser();
        CommandLineArgs commandLineArgs;
        try {
            CommandLine commandLine = parser.parse(options, args);
            commandLineArgs = CommonUtils.resolveArgs(commandLine);
        } catch (ParseException e) {
            System.err.println(e);
            HelpFormatter f = new HelpFormatter();
            f.printHelp("OptionsTip", options);
            return;
        }

        // Apiクライアント作成
        ClientManager clientManager = new ClientManager(commandLineArgs.getRegionId(), commandLineArgs.getAccessKey(), commandLineArgs.getSecretKey());

        try {
            execution(clientManager, commandLineArgs);
        } finally {
            clientManager.shutDown();
        }
    }

    public static void execution(ClientManager clientManager, CommandLineArgs commandLineArgs) throws IOException {
        // 開始・終了時間をUnix時刻に変換
        String startTime = commandLineArgs.getStartTime();
        String endTime = commandLineArgs.getEndTime();

        long unixStart = CommonUtils.localDateTimeToUnixTime(CommonUtils.stringToLocalDateTime(startTime));
        long unixEnd = CommonUtils.localDateTimeToUnixTime(CommonUtils.stringToLocalDateTime(endTime));

        int page = 1;
        List<LogItem> logItemList = new ArrayList<>();

        System.out.println("デバイスログの取得を開始します。");

        while (true) {
            // GetCustomerLog リクエスト作成
            GetCustomerLogRequest request = new GetCustomerLogRequest();
            request.setRegionId(commandLineArgs.getRegionId());
            request.setPageNo(page);
            request.setPageSize(50);
            request.setProductKey(commandLineArgs.getProductKey());
            request.setStartTime(unixStart);
            request.setEndTime(unixEnd);
            request.setDeviceName(commandLineArgs.getDeviceName());
            request.setBizCode(commandLineArgs.getBizCode());

            // Api実行
            GetCustomerLogResponse response = clientManager.getCustomerLogRequest(request);

            if (response != null && response.getCustomerLog() != null && response.getCustomerLog().getLogs() != null) {
                // ログデータがある場合、リストに追加する
                response.getCustomerLog().getLogs().getCustomerLogItems().forEach(item -> {
                    LogItem logItem = new LogItem();

                    logItem.setTime(item.getTime());
                    logItem.setTraceId(item.getTraceId());
                    logItem.setDeviceName(item.getDeviceName());
                    logItem.setMessageId(item.getMessageId());
                    logItem.setBizCode(item.getBizCode());
                    logItem.setOperation(item.getOperation());
                    logItem.setContent(item.getContent());
                    logItem.setCode(item.getCode());

                    logItemList.add(logItem);
                });
                System.out.println(response.getCustomerLog().getCurrentPage() + "ページ/ " +
                        response.getCustomerLog().getTotalPage() + "ページの取得が完了しました。");

                // 次ページが存在しない場合、ループを抜ける
                if (response.getCustomerLog().getCurrentPage() >= response.getCustomerLog().getTotalPage()) {
                    break;
                }
            } else {
                break;
            }
            page++;
        }

        if (!logItemList.isEmpty()) {
            // ログデータ並び替え
            List<LogItem> reversedList = logItemList.stream().collect(ArrayList::new, (l, e) -> l.add(0, e), (l, subl) -> l.addAll(0, subl));

            String templateName;

            if (commandLineArgs.isAddMessageFlag()) {
                System.out.println("メッセージ情報の取得を開始します。");

                reversedList.forEach(item -> {
                    if (!StringUtils.isEmpty(item.getMessageId())) {
                        QueryMessageInfoRequest request = new QueryMessageInfoRequest();
                        request.setRegionId(commandLineArgs.getRegionId());
                        request.setMessageId(item.getMessageId());

                        QueryMessageInfoResponse response = clientManager.queryMessageInfo(request);

                        //デコード後に文字列に置き換える際のCharset
                        if (response != null && response.getMessage() != null) {
                            item.setRawMessage(CommonUtils.base64Decode(response.getMessage().getMessageContent()));
                        }
                    }

                    // 進捗ログの出力
                    int index = reversedList.indexOf(item) + 1;
                    if (index % 100 == 0) {
                        System.out.println(index + "件/ " + reversedList.size() + "件の取得が完了しました");
                    }
                });

                System.out.println("メッセージ情報の取得がすべて完了しました。");
                templateName = "/DeviceLogAddMessageTemplate.xlsx";
            } else {
                templateName = "/DeviceLogTemplate.xlsx";
            }

            int index = 1;

            while (true) {
                List<LogItem> tmpList;
                boolean flag = false;

                if (reversedList.size() > 10000 * index) {
                    tmpList = reversedList.subList(10000 * (index - 1), 10000 * index);
                } else {
                    tmpList = reversedList.subList(10000 * (index - 1), reversedList.size());
                    flag = true;
                }

                String st = tmpList.get(0).getTime();
                String et = tmpList.get(tmpList.size() - 1).getTime();

                File file = new File(commandLineArgs.getOutput(), "DeviceLog_" + CommonUtils.getTimeString(st) + "-" + CommonUtils.getTimeString(et) + ".xlsx");
                XlsMapper xlsMapper = new XlsMapper();

                LogSheet sheet = new LogSheet();
                sheet.setStartTime(st);
                sheet.setEndTime(et);
                sheet.setLogItems(tmpList);

                System.out.println("Excelへの書き込みを開始します。");

                xlsMapper.save(ClassLoader.class.getResourceAsStream(templateName), new FileOutputStream(file), sheet);

                System.out.println("Excelへの書き込みが完了しました。");

                if (flag) {
                    break;
                }
                index++;
            }
            System.out.println("すべての出力が完了しました。");
        } else {
            System.out.println("デバイスログが存在しませんでした。");
        }
    }
}
