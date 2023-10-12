package repository;

import entity.Member;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MemberRepository implements ReactivePanacheMongoRepository<Member> {

    public Uni<Member> findByName(String name){
        return find("name",name).firstResult();
    }

    public Uni<Boolean> existByName(String name){
        return count("name",name).map(result -> result > 0);
    }

}
