# HobbyTalk
동아리 가입, 좋아하는 취미에 대한 정보를 공유하는 오픈채팅 서비스입니다.

## 목차

- [개발환경](#개발환경)
- [프로젝트 목표](#프로젝트-목표)
- [어플리케이션 구조도](#어플리케이션-구조도)
- [API doc](#api-doc)
- [ERD](#erd)
- [어플리케이션 아키텍처](#어플리케이션-아키텍처)
- [Git 브랜치 전략](#git-브랜치-전략-github-flow)
- [PR 리뷰 규칙](#PR-리뷰-규칙)

</br>
</br>

## 개발환경

- 언어/프레임워크

    > Java 17 / SpringBoot 3.15 / Gradle 7.6
  
- 데이터베이스
  
    > MySQL 8.* (Source, Replica) / Redis (Cache, Pub/Sub) / Mybatis / Hikari
  

- 인증 / 인가
  
    > JWT / Bcrypt
    
- 통신 프로토콜
  
    > HTTP 1.1 / WebSocket


</br>
</br>


## 프로젝트 목표
- 실시간 통신 및 대용량 트래픽을 받아들일수 있는 튼튼한 어플리케이션을 만드는것
- scale-out 할수있는 분산서버를 고려한 확장성 있는 어플리케이션을 만드는것
- 객체지향 설계원칙에 따른 가독성 및 유지보수성을 고려한 좋은 코드를 짜는것
- 지속적인 리팩토링으로 가독성을 챙기면서 유지보수할것
- 안전한 리팩토링을 위해 테스트코드를 작성하며 추후에는 TDD를 적용해보는것

</br>
</br>


## 어플리케이션 구조도
![hobby drawio](https://github.com/user-attachments/assets/ac5e7352-2925-4ff1-9173-132541b8a280)


</br>
</br>


## API doc

[API 명세서 링크 <<](https://www.notion.so/eefed4fb48cc43298d44ae32218a09f4?pvs=21)

</br>
</br>


## ERD

![hobbyErd](https://github.com/user-attachments/assets/aa45b374-23fb-420b-a6e8-60563237b4a6)


</br>
</br>


## 어플리케이션 아키텍처

![Shared 레어이간 공유되는 설정 및 유틸 소스](https://github.com/f-lab-edu/HobbyTalk/assets/90754590/a101b734-f495-4fcd-8288-e207f836f84e)



</br>
</br>


## Git 브랜치 전략 (Github-Flow)

<img width="1214" alt="github-flow" src="https://github.com/f-lab-edu/HobbyTalk/assets/90754590/55f03a17-dabf-47f0-b50a-d97754221801">

1. 모든 브랜치는 Main 브랜치에서 파생됩니다.
  
2. 모든 브랜치는 PR리뷰 작성 후 Main에 merge 합니다.

</br>
</br>


## PR 리뷰 규칙

1. PR리뷰에는 하나 이상의 코멘트를 달아야 한다.
  
2. PR리뷰에 변경된 소스코드에는 테스트코드를 포함해야 한다.


</br>
</br>

