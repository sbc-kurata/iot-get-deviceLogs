package iot.get.logs.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * QueryMessageInfoのリクエスト.
 */
@Getter
@Setter
public class QueryMessageInfoRequest {

    /** リージョンID. */
    private String regionId;

    /** メッセージID. */
    private String messageId;
}
