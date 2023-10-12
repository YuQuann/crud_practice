package response;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "responseBase", description = "回傳用物件")
public class ResponseBase {

    /**
     * 狀態
     */
    private String status;
    /**
     * 狀態碼
     */
    private Integer code;
    /**
     * 成功或失敗的說明
     */
    private String msg;
    /**
     * 是否執行成功
     */
    private boolean isSuccess;

}
