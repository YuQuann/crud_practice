package entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BaseEntity {


    /**
     * 隨機幸運亂數 測試用
     */
    private Integer randomLuckyNumber;
    /**
     * 預設是增加或操作會員資料當下的時間 測試用
     */
    private LocalDateTime createDateTime;
    /**
     * 預設是增加會員的前三天 測試用
     */
    private LocalDateTime modifyDateTime;
    /**
     * 是否是空值
     */
    private boolean isEmpty;

}
