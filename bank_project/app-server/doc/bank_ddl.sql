-- 고객
DROP TABLE IF EXISTS bank_customer RESTRICT;

-- 계좌
DROP TABLE IF EXISTS bank_account RESTRICT;

-- 거래내역
DROP TABLE IF EXISTS bank_transaction RESTRICT;

-- 게시판
DROP TABLE IF EXISTS bank_board RESTRICT;

-- 게시판파일
DROP TABLE IF EXISTS board_file RESTRICT;

-- 게시판유형
DROP TABLE IF EXISTS board_category RESTRICT;

-- 고객
CREATE TABLE bank_customer
(
    customer_no   INTEGER      NOT NULL COMMENT '고객번호id', -- 고객번호id
    name          VARCHAR(20)  NOT NULL COMMENT '이름',     -- 이름
    gender        CHAR(1)      NOT NULL COMMENT '성별',     -- 성별
    address       VARCHAR(255) NOT NULL COMMENT '주소',     -- 주소
    email         VARCHAR(40)  NOT NULL COMMENT '이메일',    -- 이메일
    password      VARCHAR(100)  NOT NULL COMMENT '비밀번호',   -- 비밀번호
    credit_rating INTEGER      NOT NULL COMMENT '신용등급'    -- 신용등급
) COMMENT '고객';

-- 고객
ALTER TABLE bank_customer
    ADD CONSTRAINT PK_bank_customer -- 고객 기본키
        PRIMARY KEY (
                     customer_no -- 고객번호id
            );

-- 고객 유니크 인덱스
CREATE UNIQUE INDEX UIX_bank_customer
    ON bank_customer ( -- 고객
                      email ASC -- 이메일
        );

-- 고객 인덱스
CREATE INDEX IX_bank_customer
    ON bank_customer ( -- 고객
                      name ASC, -- 이름
                      address ASC, -- 주소
                      email ASC -- 이메일
        );

ALTER TABLE bank_customer
    MODIFY COLUMN customer_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '고객번호id';


-- 계좌
CREATE TABLE bank_account
(
    acc_no      INTEGER     NOT NULL COMMENT '계좌번호id', -- 계좌번호id
    account_num VARCHAR(15) NOT NULL COMMENT '계좌번호',   -- 계좌번호
    balance     INTEGER     NOT NULL COMMENT '잔액',     -- 잔액
    password    VARCHAR(100) NOT NULL COMMENT '비밀번호',   -- 비밀번호
    owner       INTEGER     NOT NULL COMMENT '고객명'     -- 고객명
) COMMENT '계좌';

-- 계좌
ALTER TABLE bank_account
    ADD CONSTRAINT PK_bank_account -- 계좌 기본키
        PRIMARY KEY (
                     acc_no -- 계좌번호id
            );

-- 계좌 유니크 인덱스
CREATE UNIQUE INDEX UIX_bank_account
    ON bank_account ( -- 계좌
                     account_num ASC -- 계좌번호
        );

ALTER TABLE bank_account
    MODIFY COLUMN acc_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '계좌번호id';

-- 거래내역
CREATE TABLE bank_transaction
(
    tsac_no      INTEGER     NOT NULL COMMENT '거래내역id',                       -- 거래내역id
    acc_num      INTEGER     NOT NULL COMMENT '계좌번호',                         -- 계좌번호
    customer     varchar(20) NOT NULL COMMENT '거래자명',                         -- 거래자명
    trade        VARCHAR(20) NOT NULL COMMENT '거래유형',                         -- 거래유형
    amount       INTEGER     NOT NULL COMMENT '거래금액',                         -- 거래금액
    created_date DATE        NOT NULL DEFAULT (current_date()) COMMENT '거래일자' -- 거래일자
) COMMENT '거래내역';

-- 거래내역
ALTER TABLE bank_transaction
    ADD CONSTRAINT PK_bank_transaction -- 거래내역 기본키
        PRIMARY KEY (
                     tsac_no -- 거래내역id
            );

-- 거래내역 유니크 인덱스
CREATE UNIQUE INDEX UIX_bank_transaction
    ON bank_transaction ( -- 거래내역
                         acc_num ASC -- 계좌번호
        );

ALTER TABLE bank_transaction
    MODIFY COLUMN tsac_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '거래내역id';

-- 거래내역
ALTER TABLE bank_transaction
    ADD CONSTRAINT FK_bank_account_TO_bank_transaction -- 계좌 -> 거래내역
        FOREIGN KEY (
                     acc_num -- 계좌번호
            )
            REFERENCES bank_account ( -- 계좌
                                     acc_no -- 계좌번호id
                );

-- 게시판
CREATE TABLE bank_board
(
    board_no     INTEGER      NOT NULL COMMENT '게시판번호',               -- 게시판번호
    title        VARCHAR(255) NOT NULL COMMENT '제목',                  -- 제목
    content      MEDIUMTEXT   NOT NULL COMMENT '내용',                  -- 내용
    view_count   INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
    created_date DATE         NOT NULL DEFAULT (now()) COMMENT '작성일', -- 작성일
    writer       INTEGER      NOT NULL COMMENT '작성자',                 -- 작성자
    category     INTEGER NULL COMMENT '게시판유형번호'                       -- 게시판유형번호
) COMMENT '게시판';

-- 게시판
ALTER TABLE bank_board
    ADD CONSTRAINT PK_bank_board -- 게시판 기본키
        PRIMARY KEY (
                     board_no -- 게시판번호
            );

ALTER TABLE bank_board
    MODIFY COLUMN board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시판번호';

-- 게시판파일
CREATE TABLE board_file
(
    board_file_no INTEGER      NOT NULL COMMENT '게시판파일번호', -- 게시판파일번호
    filepath      VARCHAR(255) NOT NULL COMMENT '파일경로',    -- 파일경로
    board_no      INTEGER      NOT NULL COMMENT '게시판번호'    -- 게시판번호
) COMMENT '게시판파일';

-- 게시판파일
ALTER TABLE board_file
    ADD CONSTRAINT PK_board_file -- 게시판파일 기본키
        PRIMARY KEY (
                     board_file_no -- 게시판파일번호
            );

-- 게시판유형
CREATE TABLE board_category
(
    board_category_no INTEGER     NOT NULL COMMENT '게시판유형번호', -- 게시판유형번호
    name              VARCHAR(50) NOT NULL COMMENT '게시판이름'    -- 게시판이름
) COMMENT '게시판유형';

-- 게시판유형
ALTER TABLE board_category
    ADD CONSTRAINT PK_board_category -- 게시판유형 기본키
        PRIMARY KEY (
                     board_category_no -- 게시판유형번호
            );

-- 게시판유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_category
    ON board_category ( -- 게시판유형
                       name ASC -- 게시판이름
        );

-- 게시판
ALTER TABLE bank_board
    ADD CONSTRAINT FK_bank_customer_TO_bank_board -- 고객 -> 게시판
        FOREIGN KEY (
                     writer -- 작성자
            )
            REFERENCES bank_customer ( -- 고객
                                      customer_no -- 고객번호
                );

-- 게시판
ALTER TABLE bank_board
    ADD CONSTRAINT FK_board_category_TO_bank_board -- 게시판유형 -> 게시판
        FOREIGN KEY (
                     category -- 게시판유형번호
            )
            REFERENCES board_category ( -- 게시판유형
                                       board_category_no -- 게시판유형번호
                );

-- 게시판파일
ALTER TABLE board_file
    ADD CONSTRAINT FK_bank_board_TO_board_file -- 게시판 -> 게시판파일
        FOREIGN KEY (
                     board_no -- 게시판번호
            )
            REFERENCES bank_board ( -- 게시판
                                   board_no -- 게시판번호
                );