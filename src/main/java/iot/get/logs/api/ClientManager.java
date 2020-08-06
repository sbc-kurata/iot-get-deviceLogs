package iot.get.logs.api;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import iot.get.logs.api.model.GetCustomerLogRequest;
import iot.get.logs.api.model.GetCustomerLogResponse;
import iot.get.logs.api.model.QueryMessageInfoRequest;
import iot.get.logs.api.model.QueryMessageInfoResponse;

import java.io.IOException;

/**
 * Apiクライアント管理.
 */
public class ClientManager {

    /** Apiクライアント. */
    private final IAcsClient client;

    /**
     * コンストラクタ.
     *
     * @param regionId リージョンID
     * @param accessKey アクセスキー
     * @param secretKey シークレットキー
     */
    public ClientManager(String regionId, String accessKey, String secretKey) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
        client = new DefaultAcsClient(profile);
    }

    /**
     * GetCustomerLogAPIを呼ぶ.
     *
     * @param logRequest GetCustomerLogリクエストオブジェクト
     * @return GetCustomerLogレスポンスオブジェクト
     */
    public GetCustomerLogResponse getCustomerLogRequest(GetCustomerLogRequest logRequest) {

        // リクエスト作成
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("iot." + logRequest.getRegionId() + ".aliyuncs.com");
        request.setSysVersion("2018-01-20");
        request.setSysAction("GetCustomerLog");
        request.putQueryParameter("RegionId", logRequest.getRegionId());
        request.putQueryParameter("PageNo", String.valueOf(logRequest.getPageNo()));
        if (logRequest.getPageSize() == null) {
            request.putQueryParameter("PageSize", "10");
        } else {
            request.putQueryParameter("PageSize", String.valueOf(logRequest.getPageSize()));
        }

        request.putQueryParameter("ProductKey", logRequest.getProductKey());
        request.putQueryParameter("StartTime", String.valueOf(logRequest.getStartTime()));
        request.putQueryParameter("EndTime",  String.valueOf(logRequest.getEndTime()));

        if (logRequest.getBizCode() != null) {
            request.putQueryParameter("BizCode", logRequest.getBizCode());
        }

        if (logRequest.getDeviceName() != null) {
            request.putQueryParameter("DeviceName", logRequest.getDeviceName());
        }

        try {
            // Apiコール
            CommonResponse response = client.getCommonResponse(request);
            ObjectMapper om = new ObjectMapper();

            // Json解決し返却
            return om.readValue(response.getData(), GetCustomerLogResponse.class);
        } catch (ClientException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * QueryMessageInfoAPIを呼ぶ.
     *
     * @param infoRequest QueryMessageInfoリクエスト
     * @return QueryMessageInfoレスポンス
     */
    public QueryMessageInfoResponse queryMessageInfo(QueryMessageInfoRequest infoRequest) {
        // リクエスト作成
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("iot." + infoRequest.getRegionId() + ".aliyuncs.com");
        request.setSysVersion("2018-01-20");
        request.setSysAction("QueryMessageInfo");
        request.putQueryParameter("RegionId", infoRequest.getRegionId());
        request.putQueryParameter("UniMsgId", infoRequest.getMessageId());

        try {
            // Apiコール
            CommonResponse response = client.getCommonResponse(request);
            ObjectMapper om = new ObjectMapper();

            // Json解決し返却
            return om.readValue(response.getData(), QueryMessageInfoResponse.class);
        } catch (ClientException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * クライアントのシャットダウン.
     */
    public void shutDown() {
        client.shutdown();
    }
}
