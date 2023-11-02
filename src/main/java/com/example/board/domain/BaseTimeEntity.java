package com.example.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //JPA Entity클래스들이 BaseTimeEntity를 상속할 경우 필드(createdDate,modifiedDate)들도 칼럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) //Entity의 이벤트를 자동 관리 /메인 애플리케이션에서 활성화 시켜줘야 함
public abstract class BaseTimeEntity {

    @CreatedDate //Entity가 셍성되어 저장될 때 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity값을 변경할 때 시간을 자동 저장됨
    private  LocalDateTime modifiedDate;
}
