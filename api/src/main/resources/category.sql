INSERT INTO hobby_categories (id, name, created_time) VALUES
    (1, '운동', CURRENT_TIMESTAMP),
    (2, '음악', CURRENT_TIMESTAMP),
    (3, '미술', CURRENT_TIMESTAMP),
    (4, '요리', CURRENT_TIMESTAMP),
    (5, '여행', CURRENT_TIMESTAMP),
    (6, '독서', CURRENT_TIMESTAMP),
    (7, '게임', CURRENT_TIMESTAMP),
    (8, '사진 촬영', CURRENT_TIMESTAMP),
    (9, '영화 감상', CURRENT_TIMESTAMP),
    (10, '공예', CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
    created_time = VALUES(created_time);