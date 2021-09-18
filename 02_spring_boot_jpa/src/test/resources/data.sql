-- 값 증가 구문을 넣지 않는다면 나중에 Jpa Test 중에 오류 발생
CALL NEXT VALUE FOR hibernate_sequence; -- 값 증가
INSERT INTO USER (`ID`, `NAME`, `EMAIL`, `CREATED_AT`, `UPDATED_AT`) VALUES (1, 'minseo', 'minseo@gmail.com', now(), now());

CALL NEXT VALUE FOR hibernate_sequence; -- 값 증가
INSERT INTO USER (`ID`, `NAME`, `EMAIL`, `CREATED_AT`, `UPDATED_AT`) VALUES (2, 'Minsoo', 'minsoo@gmail.com', now(), now());

CALL NEXT VALUE FOR hibernate_sequence; -- 값 증가
INSERT INTO USER (`ID`, `NAME`, `EMAIL`, `CREATED_AT`, `UPDATED_AT`) VALUES (3, 'John', 'john@gmail.com', now(), now());

CALL NEXT VALUE FOR hibernate_sequence; -- 값 증가
INSERT INTO USER (`ID`, `NAME`, `EMAIL`, `CREATED_AT`, `UPDATED_AT`) VALUES (4, 'James', 'james@gmail.com', now(), now());

CALL NEXT VALUE FOR hibernate_sequence; -- 값 증가
INSERT INTO USER (`ID`, `NAME`, `EMAIL`, `CREATED_AT`, `UPDATED_AT`) VALUES (5, 'John', 'john@naver.com', now(), now());
