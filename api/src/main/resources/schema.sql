CREATE TABLE IF NOT EXISTS users (
  id int PRIMARY KEY AUTO_INCREMENT,
  nickname varchar(255) NOT NULL COMMENT '닉네임',
  phone_number varchar(255) NOT NULL COMMENT '핸드폰 번호' UNIQUE,
  password varchar(255) NOT NULL COMMENT '비밀번호',
  username varchar(255) NOT NULL COMMENT '유저 아이디' UNIQUE,
  profile_image varchar(255) COMMENT '프로필 이미지',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜'
);

CREATE TABLE IF NOT EXISTS auth_users (
  id int PRIMARY KEY AUTO_INCREMENT,
  user_id int UNIQUE NOT NULL COMMENT '자기자신의 id',
  refresh_token varchar(255) NOT NULL COMMENT '리프레시 토큰',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜'
);

CREATE TABLE IF NOT EXISTS friends (
  user_id int NOT NULL COMMENT '자기자신의 id',
  friend_id int NOT NULL COMMENT '친구유저 id',
  status int NOT NULL DEFAULT 0 COMMENT '1: 친구, 2: 차단',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜',
  updated_time timestamp NOT NULL DEFAULT NOW() COMMENT '수정 날짜',
  PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS friendship_requests (
  id int PRIMARY KEY AUTO_INCREMENT,
  to_user_id int NOT NULL COMMENT '친구신청 받은 유저 id',
  from_user_id int NOT NULL COMMENT '친구신청 보낸 유저 id',
  status int NOT NULL DEFAULT 0 COMMENT '0: 친구x, 1: 친구o',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜'
);

CREATE TABLE IF NOT EXISTS user_notification_settings (
  user_id int PRIMARY KEY NOT NULL COMMENT '유저 id',
  chat int NOT NULL DEFAULT 1 COMMENT '채팅 알림 설정 여부',
  friendship int NOT NULL DEFAULT 1 COMMENT '친구신청 알림 설정 여부',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  chat_room_membership_request int NOT NULL DEFAULT 1 COMMENT '채팅방 입장 신청 알림 설정 여부'
);

CREATE TABLE IF NOT EXISTS hobby_categories (
  id int PRIMARY KEY AUTO_INCREMENT,
  name varchar(255) NOT NULL COMMENT '카테고리 명' UNIQUE,
  parent_id int COMMENT '부모 카테고리 id',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜'
);

CREATE TABLE IF NOT EXISTS open_chat_rooms (
  id int PRIMARY KEY AUTO_INCREMENT,
  title varchar(255) NOT NULL COMMENT '오픈채팅방 제목',
  notice varchar(255) COMMENT '오픈채팅방 공지',
  manager_id int NOT NULL COMMENT '방장 id',
  category_id int NOT NULL COMMENT '카테고리 id',
  maximum_capacity int NOT NULL COMMENT '오픈채팅방 최대 참여 가능 인원',
  current_attendance int NOT NULL DEFAULT 1 COMMENT '오픈채팅방 현재 참여한 인원',
  password varchar(255) NULL COMMENT '오픈채팅방 비밀번호',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜',
  UNIQUE (manager_id, title)
);

CREATE TABLE IF NOT EXISTS open_chat_room_users (
  user_id int NOT NULL COMMENT '유저 id',
  open_chat_room_id int NOT NULL COMMENT '오픈채팅방 id',
  last_exit_time timestamp NOT NULL DEFAULT NOW() COMMENT '채팅방 나간 시간',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜',
  PRIMARY KEY (user_id, open_chat_room_id)
);

CREATE TABLE IF NOT EXISTS open_chats (
  id int PRIMARY KEY AUTO_INCREMENT,
  sender_id int NOT NULL COMMENT '전송자 id',
  open_chat_room_id int NOT NULL COMMENT '오픈채팅방 id',
  snowflake_id varchar(255) NOT NULL COMMENT '스노우플레이크 아이디',
  message varchar(255) NOT NULL COMMENT '메세지 본문',
  type int NOT NULL COMMENT '채팅 타입',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜',
  UNIQUE KEY uq_snowflake_id (snowflake_id)
);

CREATE TABLE IF NOT EXISTS open_chat_room_membership_requests (
  id int PRIMARY KEY AUTO_INCREMENT,
  requester_id int NOT NULL COMMENT '신청자 id',
  open_chat_room_id int NOT NULL COMMENT '오픈채팅방 id',
  message varchar(255) NOT NULL COMMENT '참가 신청 본문',
  status int NOT NULL DEFAULT 1 COMMENT '1: 참가신청, 2: 승인, 3: 거부',
  is_deleted int NOT NULL DEFAULT 0 COMMENT '삭제 여부',
  deleted_time timestamp NULL COMMENT '삭제 날짜',
  created_time timestamp NOT NULL DEFAULT NOW() COMMENT '생성 날짜'
);

CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME(6) NOT NULL,
	START_TIME DATETIME(6) DEFAULT NULL ,
	END_TIME DATETIME(6) DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME(6),
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	PARAMETER_NAME VARCHAR(100) NOT NULL ,
	PARAMETER_TYPE VARCHAR(100) NOT NULL ,
	PARAMETER_VALUE VARCHAR(2500) ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	CREATE_TIME DATETIME(6) NOT NULL,
	START_TIME DATETIME(6) DEFAULT NULL ,
	END_TIME DATETIME(6) DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED DATETIME(6),
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE IF NOT EXISTS BATCH_JOB_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);