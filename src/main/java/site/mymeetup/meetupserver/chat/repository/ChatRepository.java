package site.mymeetup.meetupserver.chat.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import site.mymeetup.meetupserver.chat.entity.Chat;

import java.time.LocalDateTime;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    // 모임 단체 채팅 조회
    Flux<Chat> findAllByCrewIdAndCreateDateAfter(Long crewId, LocalDateTime createDate);

    // 모임 1대1 채팅 조회
    @Query("{$and: [ {'crewId': ?0}, {$or: [ {$and: [ {'senderId': ?1}, {'receiverId': ?2} ]}, {$and: [ {'senderId': ?2}, {'receiverId': ?1} ]} ]} ]}")
    Flux<Chat> findAllByCrewIdAndSenderIdAndReceiverId(Long crewId, Long senderId, Long receiverId);

}
