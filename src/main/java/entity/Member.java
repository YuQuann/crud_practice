package entity;

import enums.Status;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member extends BaseEntity{

    private ObjectId id;
    @JsonbProperty
    private Integer memberId;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("level")
    private String level;
    @JsonbProperty("status")
    private Status status;

    public Member(Integer memberId, String name, String level, Status status) {
        this.memberId = memberId;
        this.name = name;
        this.level = level;
        this.status = status;
    }
}
