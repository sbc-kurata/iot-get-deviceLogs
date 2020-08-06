package iot.get.logs.xls;

import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ログシート情報.
 */
@XlsSheet(name="DeviceLog")
@Setter
@Getter
public class LogSheet {

    /** 開始時間. */
    @XlsLabelledCell(label="StartTime", type= LabelledCellType.Right)
    private String startTime;

    /** 終了時間. */
    @XlsLabelledCell(label="EndTime", type= LabelledCellType.Right)
    private String endTime;

    /** ログ一覧. */
    @XlsHorizontalRecords(tableLabel="Logs")
    private List<LogItem> logItems;
}
