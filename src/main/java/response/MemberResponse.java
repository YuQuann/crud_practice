package response;

import entity.Member;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MemberResponse extends ResponseBase{

    private Member memberResponse;

}
